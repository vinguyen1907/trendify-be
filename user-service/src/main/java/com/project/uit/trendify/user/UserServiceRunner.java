package com.project.uit.trendify.user;

import com.project.uit.trendify.common.lib.config.CommonConfig;
import com.project.uit.trendify.common.lib.config.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.project.uit.trendify.common.lib.entity", "com.project.uit.trendify.user.entity"})
@ComponentScan({"com.project.uit.trendify.common.lib", "com.project.uit.trendify"})
@Import({SecurityConfig.class, CommonConfig.class})
@EnableJpaRepositories(basePackages = "com.project.uit.trendify.user.repository")
public class UserServiceRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceRunner.class);
    public static void main(String[] args) {
        LOGGER.info("Start Trendify Spring app");
        SpringApplication.run(UserServiceRunner.class, args);
        LOGGER.info("Stop Trendify Spring app");
    }
}