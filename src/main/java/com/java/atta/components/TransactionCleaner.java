package com.java.atta.components;

import com.java.atta.services.TransactionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by atta on 22/6/18
 */
@Component
public class TransactionCleaner {

    private TransactionService ts;

    public TransactionCleaner(TransactionService service) {
        this.ts = service;
    }

    @Scheduled(fixedRate = 500)
    public void cleanExpiredTransactions() {
        ts.removeExpired();
    }
}
