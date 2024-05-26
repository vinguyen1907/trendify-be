package com.project.uit.trendify.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetProductByCodesRequest {
    private List<String> codes;
}
