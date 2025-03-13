package com.example.dto;

import lombok.Data;

@Data
public class AddressRequest {
    private String address;
    private Double latitude;
    private Double longitude;
    private String detail;
} 