package com.project.uit.trendify.user.producer;

import com.project.uit.trendify.user.message.RecordClickHistoryMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.project.uit.trendify.common.lib.constant.TopicConstant.CLICK_HISTORY_SERVICE_RECORD_CLICK_HISTORY;

@Service
@RequiredArgsConstructor
public class ClickHistoryProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickHistoryProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(RecordClickHistoryMessage message) {
        LOGGER.info(String.format("#### -> Producing message -> %s", message));
        kafkaTemplate.send(CLICK_HISTORY_SERVICE_RECORD_CLICK_HISTORY, message);
    }
}
