package com.project.uit.trendify.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordClickHistoryRequest {
    private Long productId;
    private String productCode;
}
