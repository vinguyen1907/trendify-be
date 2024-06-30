package com.project.uit.trendify.user.service.interfaces;

import com.project.uit.trendify.user.entity.ShippingAddressEntity;

import java.util.List;

public interface IShippingAddressService {
    List<ShippingAddressEntity> getShippingAddresses(Long userId);
    ShippingAddressEntity createShippingAddress(ShippingAddressEntity shippingAddressEntity);

    void updateShippingAddress(ShippingAddressEntity shippingAddressEntity);
}
