package com.project.uit.trendify.controller;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.request.GetProductByCodesRequest;
import com.project.uit.trendify.dto.PageDTO;
import com.project.uit.trendify.common.lib.response.GetProductByCodesResponse;
import com.project.uit.trendify.entity.ProductSpecificationEntity;
import com.project.uit.trendify.service.interfaces.IProductService;
import com.project.uit.trendify.service.interfaces.IProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final IProductService productService;
    private final IProductSpecificationService productSpecificationService;

    @GetMapping("/{productId}/related")
    public ResponseEntity<?> getRecommendedProducts(
            @PathVariable String productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws IOException {
        LOGGER.info("Receive get related products request");
        PageDTO<ProductDTO> recommendedProducts = productService.getRelatedProducts(productId, page, size);
        return ResponseEntity.ok(recommendedProducts);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/{productId}/specs")
    public ResponseEntity<List<ProductSpecificationEntity>> getProductSpecifications(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(productSpecificationService.getProductSpecifications(productId));
    }

    @PostMapping(path = "/codes", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GetProductByCodesResponse> getProductsByCodes(
            @RequestBody GetProductByCodesRequest request
    ) {
        LOGGER.info("Get products by codes: " + request.getCodes());
        var products = productService.getProductsByCodes(request.getCodes());
        var response = new GetProductByCodesResponse(products);
        LOGGER.info("Response to get products by codes request: " + response);
        return ResponseEntity.ok(response);
    }
}
