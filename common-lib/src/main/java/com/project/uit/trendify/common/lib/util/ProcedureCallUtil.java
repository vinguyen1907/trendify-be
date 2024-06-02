package com.project.uit.trendify.common.lib.util;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.request.GetProductByCodesRequest;
import com.project.uit.trendify.common.lib.request.GetProductsByIdsRequest;
import com.project.uit.trendify.common.lib.response.GetProductsResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.project.uit.trendify.common.lib.constant.ApiConstant.INTERNAL_TOKEN;
import static com.project.uit.trendify.common.lib.constant.ApiConstant.PRODUCT_SERVICE_BASE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class ProcedureCallUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcedureCallUtil.class);
    private final RestTemplate restTemplate;

    public List<ProductDTO> getProductsByCodesFromProductService(List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            LOGGER.info("List of codes is empty");
            return List.of();
        }
        String url = PRODUCT_SERVICE_BASE_URL + "/codes";
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("codes", codes)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + INTERNAL_TOKEN);
        LOGGER.info("Request getting products by codes to product service - URI: " + uri);
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

    public List<ProductDTO> getProductsByIdsFromProductService(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            LOGGER.info("List of ids is empty");
            return List.of();
        }
        String url = PRODUCT_SERVICE_BASE_URL + "/ids";
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("ids", ids)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + INTERNAL_TOKEN);
        headers.setContentType(APPLICATION_JSON);
        try {
            GetProductsByIdsRequest request = new GetProductsByIdsRequest(ids);
            HttpEntity<GetProductsByIdsRequest> requestEntity = new HttpEntity<>(request, headers);
            LOGGER.info("Request getting products by ids to product service - URI: " + uri + " Request: " + requestEntity);
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
        } catch (Exception e) {
            LOGGER.error("Unknown Error: " + e.getMessage());
            throw e;
        }
    }
}
