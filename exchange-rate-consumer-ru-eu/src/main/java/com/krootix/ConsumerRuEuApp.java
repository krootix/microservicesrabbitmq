package com.krootix;

import com.krootix.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ConsumerRuEuApp {

    public final Consumer consumer;

    @Autowired
    public ConsumerRuEuApp(Consumer consumer) {
        this.consumer = consumer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerRuEuApp.class, args);
    }
}