package com.project.uit.trendify.common.lib.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateStockRequest {
    List<UpdateStockRequestItem> items;
}
