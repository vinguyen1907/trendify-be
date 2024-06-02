package com.project.uit.trendify.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.project.uit.trendify.common.lib.entity", "com.project.uit.trendify.payment.entity"})
@ComponentScan({"com.project.uit.trendify.common.lib", "com.project.uit.trendify"})
@EnableJpaRepositories(basePackages = "com.project.uit.trendify.payment.repository")
public class PaymentServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceRunner.class, args);
    }
}