package com.project.uit.trendify.user.controller;

import com.project.uit.trendify.user.message.RecordClickHistoryMessage;
import com.project.uit.trendify.user.producer.ClickHistoryProducer;
import com.project.uit.trendify.user.request.RecordClickHistoryRequest;
import com.project.uit.trendify.common.lib.util.ExtractTokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/history/click")
@RequiredArgsConstructor
public class ClickHistoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickHistoryController.class);
    private final ClickHistoryProducer clickHistoryProducer;
    private final ExtractTokenUtil extractTokenUtil;

    @PostMapping("")
    public ResponseEntity<Boolean> updateClickHistory(@RequestBody RecordClickHistoryRequest request) {
        LOGGER.info("Receive click history update request: {}", request);
        Long userId = extractTokenUtil.getUserIdFromToken();
        RecordClickHistoryMessage message = new RecordClickHistoryMessage(userId, request.getProductId(), request.getProductCode());
        clickHistoryProducer.send(message);
        return ResponseEntity.ok(true);
    }
}
