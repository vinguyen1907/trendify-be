package com.project.uit.trendify.payment.controller;

import com.project.uit.trendify.common.lib.util.ExtractTokenUtil;
import com.project.uit.trendify.payment.entity.PaymentInfoEntity;
import com.project.uit.trendify.payment.service.impl.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
    private final ExtractTokenUtil extractTokenUtil;
    private final IPaymentService paymentService;

    @GetMapping("/infos")
    public ResponseEntity<List<PaymentInfoEntity>> getPaymentMethods() {
        LOGGER.info("GET /api/v1/payment/infos");
        Long userId = extractTokenUtil.getUserIdFromToken();
        var response = paymentService.getPaymentMethods(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/infos")
    public ResponseEntity<PaymentInfoEntity> addPaymentMethod(
            @RequestBody PaymentInfoEntity paymentInfoEntity
    ) {
        LOGGER.info("Request to POST /api/v1/payment/infos");
        Long userId = extractTokenUtil.getUserIdFromToken();
        paymentInfoEntity.setUserId(userId);
        var response = paymentService.addPaymentMethod(paymentInfoEntity);
        LOGGER.info("Response POST /api/v1/payment/infos: " + response.toString());
        return ResponseEntity.ok(response);
    }
}
