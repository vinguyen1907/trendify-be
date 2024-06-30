package com.project.uit.trendify.user.controller;

import com.project.uit.trendify.user.entity.ShippingAddressEntity;
import com.project.uit.trendify.user.service.interfaces.IShippingAddressService;
import com.project.uit.trendify.common.lib.util.ExtractTokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipping-address")
@RequiredArgsConstructor
public class ShippingAddressController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingAddressController.class);
    private final ExtractTokenUtil extractTokenUtil;
    private final IShippingAddressService shippingAddressService;

    @GetMapping("")
    public ResponseEntity<List<ShippingAddressEntity>> getShippingAddresses() {
        LOGGER.info("Receive get shipping addresses request");
        Long userId = extractTokenUtil.getUserIdFromToken();
        var response = shippingAddressService.getShippingAddresses(userId);
        LOGGER.info("Response get shipping addresses request: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ShippingAddressEntity> createShippingAddresses(
            @RequestBody ShippingAddressEntity shippingAddressEntity
    ) {
        LOGGER.info("Receive create shipping address request: {}", shippingAddressEntity);
        Long userId = extractTokenUtil.getUserIdFromToken();
        shippingAddressEntity.setUserId(userId);
        var response = shippingAddressService.createShippingAddress(shippingAddressEntity);
        LOGGER.info("Response create shipping address request: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("")
    public ResponseEntity<?> updateShippingAddresses(
            @RequestBody ShippingAddressEntity shippingAddressEntity
    ) {
        LOGGER.info("Receive Update shipping address request: {}", shippingAddressEntity);
        Long userId = extractTokenUtil.getUserIdFromToken();
        shippingAddressEntity.setUserId(userId);
        shippingAddressService.updateShippingAddress(shippingAddressEntity);
        LOGGER.info("Response /PUT /api/v1/shipping-address: Success");
        return ResponseEntity.ok().build();
    }
}
