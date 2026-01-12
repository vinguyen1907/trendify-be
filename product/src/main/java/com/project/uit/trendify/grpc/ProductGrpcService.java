package com.project.uit.trendify.grpc;

import com.project.uit.trendify.grpc.ProductServiceGrpc;
import com.project.uit.trendify.grpc.UpdateStockRequest;
import com.project.uit.trendify.grpc.UpdateStockResponse;
import com.project.uit.trendify.service.interfaces.IProductService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
@RequiredArgsConstructor
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductGrpcService.class);
    private final IProductService productService;

    @Override
    public void updateStock(UpdateStockRequest request, StreamObserver<UpdateStockResponse> responseObserver) {
        LOGGER.info("gRPC updateStock received with {} items", request.getItemsCount());
        try {
            var commonRequestItems = request.getItemsList().stream()
                    .map(item -> new com.project.uit.trendify.common.lib.request.UpdateStockRequestItem(item.getProductId(), item.getQuantity()))
                    .toList();
            var commonRequest = new com.project.uit.trendify.common.lib.request.UpdateStockRequest(commonRequestItems);

            productService.updateStock(commonRequest);

            UpdateStockResponse response = UpdateStockResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Stock updated successfully")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            LOGGER.error("Error processing gRPC updateStock", e);
            responseObserver.onError(e);
        }
    }
}
