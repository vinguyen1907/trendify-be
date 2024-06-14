package com.project.uit.trendify.controller;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.dto.PageDTO;
import com.project.uit.trendify.entity.CategoryEntity;
import com.project.uit.trendify.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    private final ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        LOGGER.info("Receive /GET /api/v1/categories");
        var categories = categoryService.getAllCategories();
        LOGGER.info("Response /GET /api/v1/categories: " + categories);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageDTO<ProductDTO>> getProductsInCategory(
            @PathVariable Long categoryId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok(new PageDTO<>(categoryService.getProductsInCategory(categoryId, page, size)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryEntity>> searchCategories(@RequestParam String keyword) {
        LOGGER.info("Receive /GET /api/v1/categories/search?keyword=" + keyword);
        var categories = categoryService.searchCategories(keyword);
        LOGGER.info("Response /GET /api/v1/categories/search?keyword=" + keyword + ": " + categories);
        return ResponseEntity.ok(categories);
    }
}
