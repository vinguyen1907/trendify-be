package com.project.uit.trendify.common.lib.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RateLimitConfig {

//    @Bean
//    public Bucket bucket() {
//        Refill refill = Refill.greedy(10, Duration.ofMinutes(1));
//        Bandwidth limit = Bandwidth.classic(10, refill);
//        return Bucket.builder()
//                .addLimit(limit)
//                .build();
//    }

    @Bean
    public Map<String, Bucket> rateLimitBuckets() {
        Map<String, Bucket> buckets = new HashMap<>();
        // UserService: 60 requests per minute for each service

        Refill userRefill = Refill.greedy(60, Duration.ofMinutes(1));
        Bandwidth userLimit = Bandwidth.classic(60, userRefill);
        buckets.put("user-service", Bucket.builder().addLimit(userLimit).build());

        Refill orderRefill = Refill.greedy(60, Duration.ofMinutes(1));
        Bandwidth orderLimit = Bandwidth.classic(60, orderRefill);
        buckets.put("order-service", Bucket.builder().addLimit(orderLimit).build());

        Refill paymentRefill = Refill.greedy(60, Duration.ofMinutes(1));
        Bandwidth paymentLimit = Bandwidth.classic(60, paymentRefill);
        buckets.put("payment-service", Bucket.builder().addLimit(paymentLimit).build());

        return buckets;
    }
}