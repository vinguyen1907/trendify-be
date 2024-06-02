package com.project.uit.trendify.dto;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;

    private Long userId;

    private ProductDTO product;

    private int quantity;

    private String size;

    private String color;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
