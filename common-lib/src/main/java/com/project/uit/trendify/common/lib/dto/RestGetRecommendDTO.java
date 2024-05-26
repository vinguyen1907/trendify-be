package com.project.uit.trendify.common.lib.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RestGetRecommendDTO {
    @JsonProperty("asin")
    private String productCode;
    private Double score;
}
