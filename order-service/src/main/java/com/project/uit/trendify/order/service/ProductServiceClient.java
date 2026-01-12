package com.project.uit.trendify.order.service;

import com.project.uit.trendify.order.dto.UpdateProductsStockDTO;
import com.project.uit.trendify.order.service.interfaces.IProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceClient implements IProductServiceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceClient.class);

    @net.devh.boot.grpc.client.inject.GrpcClient("product-service")
    private com.project.uit.trendify.grpc.ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    @Override
    public boolean updateProductsStock(UpdateProductsStockDTO updateProductsStockDTO) {
        try {
            var items = updateProductsStockDTO.getItems().stream()
                    .map(item -> com.project.uit.trendify.grpc.UpdateStockRequestItem.newBuilder()
                            .setProductId(item.getProductId())
                            .setQuantity(item.getQuantity())
                            .build())
                    .toList();

            var request = com.project.uit.trendify.grpc.UpdateStockRequest.newBuilder()
                    .addAllItems(items)
                    .build();

            LOGGER.info("Request to product service via gRPC: {}", request);
            var response = productServiceStub.updateStock(request);
            LOGGER.info("Response from product service via gRPC: {}", response);
            return response.getSuccess();
        } catch (Exception ex) {
            LOGGER.error("Error from product service via gRPC", ex);
            return false;
        }
    }
}
