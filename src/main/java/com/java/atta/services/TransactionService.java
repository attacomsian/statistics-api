package com.java.atta.services;

import com.java.atta.commons.TransactionExpired;
import com.java.atta.domains.Statistic;
import com.java.atta.domains.Transaction;

/**
 * Created by atta on 22/6/18
 */
public interface TransactionService {

    void add(Transaction t) throws TransactionExpired;
    
    Statistic getStatistic();
    
    void removeExpired();
    
    boolean isExpired(Transaction t);
}
