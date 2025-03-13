package com.example.dto;

import lombok.Data;

@Data
public class RegeoResponse {
    private String status;
    private String info;
    private RegeocodeData regeocode;
    
    @Data
    public static class RegeocodeData {
        private String formatted_address;
    }
} 