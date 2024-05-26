package com.project.uit.trendify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.project.uit.trendify.entity")
public class CartServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceRunner.class, args);
    }
}