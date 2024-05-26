package com.project.uit.trendify.user.consumer;

import com.project.uit.trendify.user.message.RecordClickHistoryMessage;
import com.project.uit.trendify.user.service.interfaces.IClickHistoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.project.uit.trendify.common.lib.constant.TopicConstant.CLICK_HISTORY_SERVICE_RECORD_CLICK_HISTORY;

@Service
@RequiredArgsConstructor
public class ClickHistoryConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickHistoryConsumer.class);
    private final IClickHistoryService clickHistoryService;

    @KafkaListener(topics = CLICK_HISTORY_SERVICE_RECORD_CLICK_HISTORY, groupId = "group-1")
    public void consume(RecordClickHistoryMessage message) throws InterruptedException {
        LOGGER.info(String.format("#### -> Consumed message -> %s", message));
        clickHistoryService.recordClickHistory(message.getUserId(), message.getProductId(), message.getProductCode());
    }
}
