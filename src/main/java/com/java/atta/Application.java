package com.java.atta;

import com.java.atta.domains.Transaction;
import com.java.atta.services.TransactionService;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(TransactionService ts) {
        return (params) -> {
            for (int i = 0; i < 10; i++) {
                double amount = Math.random() * 100;
                ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
                long timestamp = utc.toEpochSecond() * 1000;
                ts.add(new Transaction(amount, timestamp - 30 * 1000));
            }
            System.out.println(ts.getStatistic());
        };
    };
}
