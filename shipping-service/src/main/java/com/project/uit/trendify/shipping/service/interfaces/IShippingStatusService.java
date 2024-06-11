package com.project.uit.trendify.shipping.service.interfaces;

import com.project.uit.trendify.shipping.entity.ShippingStatusEntity;
import com.project.uit.trendify.shipping.enums.ShippingStatus;

public interface IShippingStatusService {
    ShippingStatusEntity createShippingStatus(Long orderId, ShippingStatus status, String location, String destination, String handleBy, String note);
}
