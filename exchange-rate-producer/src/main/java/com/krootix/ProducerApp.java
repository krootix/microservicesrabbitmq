package com.krootix;

import com.krootix.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProducerApp implements CommandLineRunner {

    public final Producer producer;

    @Autowired
    public ProducerApp(Producer producer) {
        this.producer = producer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class, args);
    }

    @Override
    public void run(String... args) {

        producer.produce();
    }
}