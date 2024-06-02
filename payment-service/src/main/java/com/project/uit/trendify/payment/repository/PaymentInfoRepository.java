package com.project.uit.trendify.payment.repository;

import com.project.uit.trendify.payment.entity.PaymentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Long> {
    List<PaymentInfoEntity> findAllByUserId(Long userId);
}
