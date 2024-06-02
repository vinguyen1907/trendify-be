package com.project.uit.trendify.service;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.dto.RestGetRecommendDTO;
import com.project.uit.trendify.common.lib.request.UpdateStockRequest;
import com.project.uit.trendify.dto.PageDTO;
import com.project.uit.trendify.entity.ProductSpecificationEntity;
import com.project.uit.trendify.repository.ProductRepository;
import com.project.uit.trendify.request.GetRecommendedProductsRequest;
import com.project.uit.trendify.response.GetRelatedProductsResponse;
import com.project.uit.trendify.service.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static com.project.uit.trendify.common.lib.constant.ApiConstant.RECOMMENDATION_SYS_API_RECOMMEND;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public ProductDTO getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductDTO::new)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PageDTO<ProductDTO> getRelatedProducts(String productCode, int page, int size) throws IOException {
        GetRecommendedProductsRequest request = new GetRecommendedProductsRequest(productCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GetRecommendedProductsRequest> requestEntity = new HttpEntity<>(request, headers);
        LOGGER.info("Request to recommendation sys: {}", requestEntity);
        try {
            ResponseEntity<GetRelatedProductsResponse> responseEntity = restTemplate.postForEntity(
                    RECOMMENDATION_SYS_API_RECOMMEND,
                    requestEntity,
                    GetRelatedProductsResponse.class
            );
            LOGGER.info("Response from recommendation sys: {}", responseEntity);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                GetRelatedProductsResponse recommendDTOS = responseEntity.getBody();
                List<String> ids = recommendDTOS.getRecommendations().stream().map(RestGetRecommendDTO::getProductCode).toList();
                List<ProductDTO> products = productRepository.findAllByCodeIn(ids).stream().map(ProductDTO::new).toList();
                System.out.println("Products: " + products);
                return new PageDTO<>(page, size, 1, products.size(), products);
            } else {
                System.err.println("Error occurred: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            LOGGER.error("Request to recommendation sys error: {}", e.toString());
            throw e;
        }
        return null;
    }

    @Override
    public List<ProductDTO> getProductsByCodes(List<String> codes) {
        return productRepository.findAllByCodeIn(codes).stream().map(ProductDTO::new).toList();
    }

    @Override
    public List<ProductDTO> getProductsByIds(List<Long> ids) {
        return productRepository.findAllByIdIn(ids).stream().map(ProductDTO::new).toList();
    }

    @Override
    public List<ProductSpecificationEntity> getProductSpecifications(Long productId) {
        return null;
    }

    @Override
    @Transactional
    public void updateStock(UpdateStockRequest request) {
        request.getItems().forEach(item -> {
            productRepository.findById(item.getProductId())
                    .ifPresent(product -> {
                        if (product.getStockCount() < item.getQuantity()) {
                            throw new IllegalArgumentException("Not enough stock for product: " + product.getId());
                        }
                        product.setStockCount(product.getStockCount() - item.getQuantity());
                        product.setSoldCount(product.getSoldCount() + item.getQuantity());
                        productRepository.save(product);
                    });
        });
    }
}
