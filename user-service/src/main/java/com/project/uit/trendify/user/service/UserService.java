package com.project.uit.trendify.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.dto.RestGetRecommendDTO;
import com.project.uit.trendify.common.lib.dto.UserDTO;
import com.project.uit.trendify.common.lib.request.UpdateUserRequest;
import com.project.uit.trendify.service.interfaces.IUserService;
import com.project.uit.trendify.common.lib.repository.UserRepository;
import com.project.uit.trendify.user.request.GetProductByCodesRequest;
import com.project.uit.trendify.user.request.GetRecommendProductsRequest;
import com.project.uit.trendify.common.lib.response.GetProductsResponse;
import com.project.uit.trendify.user.response.GetRecommendProductsResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.project.uit.trendify.common.lib.constant.ApiConstant.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserDTO getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.toDTO();
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.toDTO();
    }

    @Override
    public UserDTO updateUser(Long userId, UpdateUserRequest request) {
        var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getAge() != null) {
            user.setAge(request.getAge());
        }
        if (request.getFcmToken() != null) {
            user.setFcmToken(request.getFcmToken());
        }

        return userRepository.save(user).toDTO();
    }

    @Override
    public List<ProductDTO> getRecommendProducts(Long userId) {
        LOGGER.debug("Get recommend products for user: " + userId);

        List<String> baseShoeCodes = new ArrayList<>();
        baseShoeCodes.add("B08BLP231K");
        baseShoeCodes.add("B012DS5U2Y");
        baseShoeCodes.add("B01GRUZWAO");
        GetRecommendProductsRequest request = new GetRecommendProductsRequest(
                baseShoeCodes,
                1,
                10
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GetRecommendProductsRequest> requestEntity = new HttpEntity<>(request, headers);

        LOGGER.info("Request to recommendation sys: " + request);
        ResponseEntity<GetRecommendProductsResponse> responseEntity = restTemplate.postForEntity(
                RECOMMENDATION_SYS_API_RECOMMEND_FROM_USER_LAST_VIEWED_ITEMS,
                requestEntity,
                GetRecommendProductsResponse.class);
        LOGGER.info("Response from recommendation sys: " + responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            GetRecommendProductsResponse response = responseEntity.getBody();
            List<String> codes = response.getRecommendations().stream().map(RestGetRecommendDTO::getProductCode).toList();
            return getProductsByCodesFromProductService(codes);
//            List<ProductDTO> products = productRepository.findAllByCodeIn(ids).stream().map(ProductDTO::new).toList();
//            System.out.println("Products: " + products);
//            return new PageDTO<>(page, size, 1, products.size(), products);
        } else {
            throw new RuntimeException("Error occurred: " + responseEntity.getStatusCode());
        }
    }

    private List<ProductDTO> getProductsByCodesFromProductService(List<String> codes) {
        String url = PRODUCT_SERVICE_BASE_URL + "/api/v1/products/codes";
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("codes", codes)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + INTERNAL_TOKEN);
        LOGGER.info("Request to product service - URI: " + uri);
        try {
            GetProductByCodesRequest request = new GetProductByCodesRequest(codes);
            HttpEntity<GetProductByCodesRequest> requestEntity = new HttpEntity<>(request, headers);
            ResponseEntity<GetProductsResponse> responseEntity = restTemplate.postForEntity(
                    uri,
                    requestEntity,
                    GetProductsResponse.class
            );
            LOGGER.info("Response from product service: " + responseEntity.getBody());
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody().getProducts();
            } else {
                throw new RuntimeException("Error occurred: " + responseEntity.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            LOGGER.error("HTTP Error: " + e.getStatusCode());
            throw e;
        } catch (RestClientException e) {
            LOGGER.error("Client Error: " + e.getMessage());
            throw e;
        }
    }
}
