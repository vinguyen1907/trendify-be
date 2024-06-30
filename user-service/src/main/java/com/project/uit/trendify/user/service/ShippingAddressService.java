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
        return shippingAddressRepository.findAllByUserIdOrderByUpdatedAtDesc(userId);
    }

    @Override
    public ShippingAddressEntity createShippingAddress(ShippingAddressEntity shippingAddressEntity) {
        return shippingAddressRepository.save(shippingAddressEntity);
    }

    @Override
    public void updateShippingAddress(ShippingAddressEntity shippingAddressEntity) {
        ShippingAddressEntity shippingAddress = shippingAddressRepository.findById(shippingAddressEntity.getId())
                .orElseThrow(() -> new RuntimeException("Shipping address not found"));
        shippingAddress.setRecipientName(shippingAddressEntity.getRecipientName());
        shippingAddress.setCountry(shippingAddressEntity.getCountry());
        shippingAddress.setCity(shippingAddressEntity.getCity());
        shippingAddress.setState(shippingAddressEntity.getState());
        shippingAddress.setStreet(shippingAddressEntity.getStreet());
        shippingAddress.setZipCode(shippingAddressEntity.getZipCode());
        shippingAddress.setCountryCallingCode(shippingAddressEntity.getCountryCallingCode());
        shippingAddressRepository.save(shippingAddress);
    }
}
