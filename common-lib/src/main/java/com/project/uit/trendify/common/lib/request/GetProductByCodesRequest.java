package com.project.uit.trendify.common.lib.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductByCodesRequest {
    private List<String> codes;
}
