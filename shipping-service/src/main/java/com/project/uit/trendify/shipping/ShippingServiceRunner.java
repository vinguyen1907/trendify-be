package com.project.uit.trendify.shipping;

import com.project.uit.trendify.common.lib.config.CommonConfig;
import com.project.uit.trendify.common.lib.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.project.uit.trendify.shipping.repository")
@EntityScan(basePackages = {"com.project.uit.trendify.common.lib.entity", "com.project.uit.trendify.shipping.entity"})
@Import({SecurityConfig.class, CommonConfig.class})
@ComponentScan({"com.project.uit.trendify.common.lib", "com.project.uit.trendify.shipping"})
public class ShippingServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(ShippingServiceRunner.class, args);
    }
}