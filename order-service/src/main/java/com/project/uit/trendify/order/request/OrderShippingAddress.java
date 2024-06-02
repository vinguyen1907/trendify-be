package com.project.uit.trendify.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderShippingAddress {
    private String recipientName;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String countryCallingCode;
    private String phoneNumber;
    private Double latitude;
    private Double longitude;
}
