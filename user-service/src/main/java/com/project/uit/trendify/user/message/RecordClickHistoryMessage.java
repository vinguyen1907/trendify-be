package com.project.uit.trendify.user.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordClickHistoryMessage {
    private Long userId;
    private Long productId;
    private String productCode;
}
