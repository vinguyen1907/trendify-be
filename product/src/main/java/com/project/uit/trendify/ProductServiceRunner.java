package com.project.uit.trendify;

import com.project.uit.trendify.common.lib.config.CommonConfig;
import com.project.uit.trendify.common.lib.config.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.project.uit.trendify.common.lib.entity", "com.project.uit.trendify.entity"})
@ComponentScan({"com.project.uit.trendify.common.lib", "com.project.uit.trendify"})
@Import({SecurityConfig.class, CommonConfig.class})
@EnableJpaRepositories(basePackages = "com.project.uit.trendify.repository")
public class ProductServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceRunner.class, args);
    }
}