package com.project.uit.trendify.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRecommendProductsRequest {
    @JsonProperty("shoes_ids")
    private List<String> shoesIds;
    private int page;
    @JsonProperty("page_size")
    private int pageSize;
}
