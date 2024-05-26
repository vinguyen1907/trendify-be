package com.project.uit.trendify.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "cart")
@Data
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItemEntity> items;
}
