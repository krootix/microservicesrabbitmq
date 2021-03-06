package com.krootix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CommonApp {

    public static void main(String[] args) {
        SpringApplication.run(CommonApp.class, args);
    }

}