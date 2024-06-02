package com.project.uit.trendify.payment.service;

import com.project.uit.trendify.payment.entity.PaymentInfoEntity;
import com.project.uit.trendify.payment.repository.PaymentInfoRepository;
import com.project.uit.trendify.payment.service.impl.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentInfoRepository paymentInfoRepository;

    @Override
    public List<PaymentInfoEntity> getPaymentMethods(Long userId) {
        return paymentInfoRepository.findAllByUserId(userId);
    }

    @Override
    public PaymentInfoEntity addPaymentMethod(PaymentInfoEntity paymentInfoEntity) {
        return paymentInfoRepository.save(paymentInfoEntity);
    }
}
