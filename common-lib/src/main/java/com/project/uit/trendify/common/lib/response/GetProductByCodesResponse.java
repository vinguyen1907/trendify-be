package com.project.uit.trendify.common.lib.response;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class GetProductByCodesResponse implements Serializable {
    private final List<ProductDTO> products;

    @Override
    public String toString() {
        return "GetProductByCodesResponse{" +
                "products=" + products +
                '}';
    }
}
