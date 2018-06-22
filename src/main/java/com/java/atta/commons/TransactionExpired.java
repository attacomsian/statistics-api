package com.java.atta.commons;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by atta on 22/6/18
 */
@ResponseStatus(value = HttpStatus.GONE, reason = "Transaction already expired.")
public class TransactionExpired extends Exception{

}
