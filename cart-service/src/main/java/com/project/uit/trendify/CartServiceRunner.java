package com.project.uit.trendify;

import com.project.uit.trendify.common.lib.config.CommonConfig;
import com.project.uit.trendify.common.lib.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EntityScan(basePackages = "com.project.uit.trendify.entity")
@Import({SecurityConfig.class, CommonConfig.class})
public class CartServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceRunner.class, args);
    }
}