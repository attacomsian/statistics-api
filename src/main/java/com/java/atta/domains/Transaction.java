package com.java.atta.domains;

/**
 * Created by atta on 22/6/18
 */
public class Transaction implements Comparable<Transaction> {

    private double amount;
    private long timestamp;

    public Transaction() {
    }

    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" + "amount=" + amount + ", timestamp=" + timestamp + '}';
    }

    @Override
    public int compareTo(Transaction t) {
        if (t.getTimestamp() == getTimestamp()) {
            return 0;
        } else if (t.getTimestamp() > getTimestamp()) {
            return -1;
        } else {
            return 1;
        }
    }

}
