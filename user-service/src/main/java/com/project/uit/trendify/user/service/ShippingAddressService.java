package com.project.uit.trendify.user.service;

import com.project.uit.trendify.user.entity.ShippingAddressEntity;
import com.project.uit.trendify.user.repository.ShippingAddressRepository;
import com.project.uit.trendify.user.service.interfaces.IShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingAddressService implements IShippingAddressService {
    private final ShippingAddressRepository shippingAddressRepository;

    @Override
    public List<ShippingAddressEntity> getShippingAddresses(Long userId) {
        return shippingAddressRepository.findAllByUserId(userId);
    }

    @Override
    public ShippingAddressEntity createShippingAddress(ShippingAddressEntity shippingAddressEntity) {
        return shippingAddressRepository.save(shippingAddressEntity);
    }
}
