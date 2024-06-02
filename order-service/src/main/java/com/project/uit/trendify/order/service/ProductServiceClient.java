package com.project.uit.trendify.order.service;

import com.project.uit.trendify.common.lib.constant.ApiConstant;
import com.project.uit.trendify.common.lib.request.UpdateStockRequest;
import com.project.uit.trendify.common.lib.request.UpdateStockRequestItem;
import com.project.uit.trendify.order.dto.UpdateProductsStockDTO;
import com.project.uit.trendify.order.service.interfaces.IProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.project.uit.trendify.common.lib.constant.ApiConstant.INTERNAL_TOKEN;
import static com.project.uit.trendify.common.lib.constant.ApiConstant.PRODUCT_SERVICE_API_UPDATE_STOCK;

@Service
@RequiredArgsConstructor
public class ProductServiceClient implements IProductServiceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceClient.class);
    private final RestTemplate restTemplate;

    @Override
    public boolean updateProductsStock(UpdateProductsStockDTO updateProductsStockDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + INTERNAL_TOKEN);

        UpdateStockRequest request = new UpdateStockRequest(
                updateProductsStockDTO.getItems().stream().map(item -> new UpdateStockRequestItem(item.getProductId(), item.getQuantity())).toList()
        );
        HttpEntity<UpdateStockRequest> requestEntity = new HttpEntity<>(request, headers);
        try {
            LOGGER.info("Request to product service: {}", requestEntity);
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    PRODUCT_SERVICE_API_UPDATE_STOCK,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class);
            LOGGER.info("Response from product service: {}", response);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                if (ex.getStatusCode().value() == 400) {
                    LOGGER.error("Error from product service: {}", "Invalid request: " + ex.getResponseBodyAsString());
                } else if (ex.getStatusCode().value() == 404) {
                    LOGGER.error("Error from product service: {}", "Product not found: " + ex.getResponseBodyAsString());
                }
            } else if (ex.getStatusCode().is5xxServerError()) {
                LOGGER.error("Error from product service: {}", "Server error: " + ex.getResponseBodyAsString());
            }
            return false;
        }
    }
}
