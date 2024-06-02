package com.project.uit.trendify.order;

import com.project.uit.trendify.common.lib.config.CommonConfig;
import com.project.uit.trendify.common.lib.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.project.uit.trendify.common.lib.entity", "com.project.uit.trendify.order.entity"})
@ComponentScan({"com.project.uit.trendify.common.lib", "com.project.uit.trendify.order"})
@Import({SecurityConfig.class, CommonConfig.class})
@EnableJpaRepositories(basePackages = "com.project.uit.trendify.order.repository")
public class OrderServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceRunner.class, args);
    }
}