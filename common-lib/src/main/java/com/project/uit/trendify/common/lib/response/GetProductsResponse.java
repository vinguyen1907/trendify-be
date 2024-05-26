package com.project.uit.trendify.common.lib.response;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductsResponse {
    private List<ProductDTO> products;
}
