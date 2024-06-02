package com.project.uit.trendify.payment.service.impl;

import com.project.uit.trendify.payment.entity.PaymentInfoEntity;

import java.util.List;

public interface IPaymentService {
    List<PaymentInfoEntity> getPaymentMethods(Long userId);
    PaymentInfoEntity addPaymentMethod(PaymentInfoEntity paymentInfoEntity);
}
