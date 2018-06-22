package com.java.atta.controllers;

import com.java.atta.commons.TransactionExpired;
import com.java.atta.domains.Statistic;
import com.java.atta.domains.Transaction;
import com.java.atta.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by atta on 22/6/18
 */
@RestController
public class TransactionController {

    private TransactionService ts;

    public TransactionController(TransactionService service) {
        this.ts = service;
    }

    @PostMapping("/transactions")
    public ResponseEntity add(@RequestBody Transaction t) {
        try {
            ts.add(t);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (TransactionExpired e) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/statistics")
    public Statistic get() {
        return ts.getStatistic();
    }
}
