package com.project.uit.trendify.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.uit.trendify.common.lib.dto.RestGetRecommendDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetRelatedProductsResponse {
    private List<RestGetRecommendDTO> recommendations;
    @JsonProperty("total_pages")
    private Integer totalPages;
}
