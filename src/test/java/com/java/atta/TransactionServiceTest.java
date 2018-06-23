package com.java.atta;

import com.java.atta.commons.TransactionExpired;
import com.java.atta.domains.Transaction;
import com.java.atta.services.TransactionService;
import com.java.atta.services.TransactionServiceImpl;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/*
* Created by atta on 23/6/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

    @Test
    public void testGetStatistic() throws TransactionExpired {
        TransactionService ts = new TransactionServiceImpl();

        Assert.assertEquals(0, ts.getStatistic().getCount());
        Assert.assertEquals(ts.getStatistic().getSum(), 0, 0);

        double amount = 128.95;
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long timestamp = utc.toEpochSecond() * 1000;
        Transaction tn = new Transaction(amount, timestamp);

        ts.add(tn);
        ts.add(tn);

        Assert.assertEquals(2, ts.getStatistic().getCount());
        Assert.assertEquals(ts.getStatistic().getAvg(), amount, 0);
        Assert.assertEquals(ts.getStatistic().getSum(), amount * 2, 0);
    }

    @Test(expected = TransactionExpired.class)
    public void testAdd() throws TransactionExpired {
        TransactionService ts = new TransactionServiceImpl();

        double amount = 128.95;
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long timestamp = utc.toEpochSecond() * 1000;

        //return 201
        Transaction tn = new Transaction(amount, timestamp);
        ts.add(tn);

        //throws TransactionExpired exception
        Transaction expired = new Transaction(amount, timestamp - 61 * 1000);
        ts.add(expired);
    }

    @Test
    public void testRemoveExpired() throws TransactionExpired {
        TransactionService ts = new TransactionServiceImpl();

        double amount = 128.95;
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long timestamp = utc.toEpochSecond() * 1000;
        for (int i = 0; i < 5; i++) {
            ts.add(new Transaction(amount, timestamp - (55 + i) * 1000));
        }

        //wait 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ts.removeExpired();
        Assert.assertTrue(ts.getStatistic().getCount() < 5);
    }
    
    @Test
    public void testIsExpired() throws TransactionExpired {
        TransactionService ts = new TransactionServiceImpl();

        double amount = 128.95;
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long timestamp = utc.toEpochSecond() * 1000;

        //valid transaction
        Transaction tn = new Transaction(amount, timestamp);
        Assert.assertFalse(ts.isExpired(tn));

        //expired transaction
        Transaction tn2 = new Transaction(amount, timestamp - 61 * 1000);
        Assert.assertTrue(ts.isExpired(tn2));
    }
}
