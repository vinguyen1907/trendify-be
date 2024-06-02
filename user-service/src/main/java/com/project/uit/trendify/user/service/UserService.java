package com.project.uit.trendify.user.service;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.dto.RestGetRecommendDTO;
import com.project.uit.trendify.common.lib.dto.UserDTO;
import com.project.uit.trendify.common.lib.request.UpdateUserRequest;
import com.project.uit.trendify.common.lib.util.ProcedureCallUtil;
import com.project.uit.trendify.common.lib.repository.UserRepository;
import com.project.uit.trendify.user.repository.ClickHistoryRepository;
import com.project.uit.trendify.user.request.GetRecommendProductsRequest;
import com.project.uit.trendify.user.response.GetRecommendProductsResponse;
import com.project.uit.trendify.user.service.interfaces.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.project.uit.trendify.common.lib.constant.ApiConstant.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ProcedureCallUtil procedureCallUtil;
    private final ClickHistoryRepository clickHistoryRepository;

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
    public List<ProductDTO> getRecommendProducts(Long userId, Integer page, Integer size) {
        LOGGER.debug("Get recommend products for user: " + userId);

        List<String> baseShoeCodes = new ArrayList<>();
        clickHistoryRepository.findTop10ByUserIdOrderByTimestampDesc(userId).forEach(clickHistory -> {
            baseShoeCodes.add(clickHistory.getProductCode());
        });

        GetRecommendProductsRequest request = new GetRecommendProductsRequest(
                baseShoeCodes,
                page + 1,
                size
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
            return procedureCallUtil.getProductsByCodesFromProductService(codes);
//            List<ProductDTO> products = productRepository.findAllByCodeIn(ids).stream().map(ProductDTO::new).toList();
//            System.out.println("Products: " + products);
//            return new PageDTO<>(page, size, 1, products.size(), products);
        } else {
            throw new RuntimeException("Error occurred: " + responseEntity.getStatusCode());
        }
    }


}
