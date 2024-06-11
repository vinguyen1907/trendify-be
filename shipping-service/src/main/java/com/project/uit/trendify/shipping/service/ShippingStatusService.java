package com.project.uit.trendify.shipping.service;

import com.project.uit.trendify.shipping.entity.ShippingStatusEntity;
import com.project.uit.trendify.shipping.enums.ShippingStatus;
import com.project.uit.trendify.shipping.repository.ShippingStatusRepository;
import com.project.uit.trendify.shipping.service.interfaces.IShippingStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingStatusService implements IShippingStatusService {
    private final ShippingStatusRepository shippingStatusRepository;

    @Override
    public ShippingStatusEntity createShippingStatus(Long orderId, ShippingStatus status, String location, String destination, String handleBy, String note) {
        ShippingStatusEntity shippingStatus = ShippingStatusEntity.builder()
                .orderId(orderId)
                .status(status)
                .location(location)
                .destination(destination)
                .handleBy(handleBy)
                .note(note)
                .build();
        return shippingStatusRepository.save(shippingStatus);
    }
}
