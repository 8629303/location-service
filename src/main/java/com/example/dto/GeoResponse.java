package com.example.dto;

import lombok.Data;
import java.util.List;

@Data
public class GeoResponse {
    private String status;
    private String info;
    private List<GeoCode> geocodes;
    
    @Data
    public static class GeoCode {
        private String location;
        private String formatted_address;
    }
} 