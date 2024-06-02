package com.project.uit.trendify.controller;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.service.interfaces.IProductSearchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/search")
@RequiredArgsConstructor
public class ProductSearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSearchController.class);
    private final IProductSearchService productSearchService;

    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam String keyword,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        LOGGER.info("Receive /GET /api/v1/products/search?keyword=" + keyword + "&page=" + page + "&size=" + size);
        var response = productSearchService.searchProducts(keyword);
        LOGGER.info("Response: /GET /api/v1/products/search?keyword=" + keyword + response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncProductsToElasticsearch() {
        productSearchService.syncProductsToElasticsearch();
        return ResponseEntity.ok().build();
    }
}
