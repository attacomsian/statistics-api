package com.java.atta.services;

import com.java.atta.commons.TransactionExpired;
import com.java.atta.commons.TransactionOperation;
import com.java.atta.domains.Statistic;
import com.java.atta.domains.Transaction;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Service;

/**
 * Created by atta on 22/6/18
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final PriorityQueue<Transaction> transactions;

    private final Lock lock;

    private final long expiredInMillis = 60 * 1000;

    private volatile Statistic statistic;

    public TransactionServiceImpl() {
        this.transactions = new PriorityQueue<>();
        this.lock = new ReentrantLock();
        this.statistic = new Statistic();
    }

    @Override
    public void add(Transaction t) throws TransactionExpired {
        if (isExpired(t)) {
            throw new TransactionExpired();
        }

        lock.lock();
        try {
            transactions.add(t);
            updateStatistic(t, TransactionOperation.INSERT);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Statistic getStatistic() {
        return this.statistic;
    }

    @Override
    public void removeExpired() {
        Transaction head = transactions.peek();
        while (head != null && isExpired(head)) {
            lock.lock();
            try {
                if (isExpired(transactions.peek())) {
                    updateStatistic(transactions.poll(), TransactionOperation.REMOVE);
                }
            } finally {
                lock.unlock();
            }
            head = transactions.peek();
        }
    }

    @Override
    public boolean isExpired(Transaction t) {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long epochInMillis = utc.toEpochSecond() * 1000;
        return t.getTimestamp() < epochInMillis - expiredInMillis;
    }

    private void updateStatistic(Transaction t, TransactionOperation operation) {
        if (operation.equals(TransactionOperation.INSERT)) {
            this.statistic = new Statistic(
                    statistic.getSum() + t.getAmount(),
                    (statistic.getSum() + t.getAmount()) / (statistic.getCount() + 1),
                    Math.max(statistic.getMax(), t.getAmount()),
                    Math.min(statistic.getMin(), t.getAmount()),
                    statistic.getCount() + 1
            );
        } else if (operation.equals(TransactionOperation.REMOVE)) {
            statistic = new Statistic(
                    statistic.getSum() - t.getAmount(),
                    statistic.getCount() == 1 ? 0 : (statistic.getSum() - t.getAmount()) / (statistic.getCount() - 1),
                    statistic.getCount() == 1 ? 0
                            : (statistic.getMax() > t.getAmount() ? statistic.getMax()
                                    : transactions.stream().mapToDouble(d -> d.getAmount()).max().getAsDouble()),
                    statistic.getCount() == 1 ? 0
                            : (statistic.getMin() < t.getAmount() ? statistic.getMin()
                                    : transactions.stream().mapToDouble(d -> d.getAmount()).min().getAsDouble()),
                    statistic.getCount() - 1
            );
        }
    }
}
