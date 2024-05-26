package com.project.uit.trendify.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "product_click_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductClickHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private Long userId;
    @NotNull
    @Column(nullable = false)
    private Long productId;
    private String productCode;
    private LocalDateTime timestamp;

    @PrePersist
    private void prePersist() {
        timestamp = LocalDateTime.now();
    }
}
