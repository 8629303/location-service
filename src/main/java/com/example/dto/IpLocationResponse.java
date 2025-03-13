package com.example.dto;

import lombok.Data;

@Data
public class IpLocationResponse {
    /**
     * 国家
     */
    private String country;
    /**
     * 区域
     */
    private String region;
    /**
     * 省
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 运营商
     */
    private String isp;

    public static IpLocationResponse fromString(String location) {
        IpLocationResponse response = new IpLocationResponse();
        String[] parts = location.split("\\|");
        if (parts.length >= 5) {
            response.setCountry(parts[0]);
            response.setRegion(parts[1]);
            response.setProvince(parts[2]);
            response.setCity(parts[3]);
            response.setIsp(parts[4]);
        }
        return response;
    }
}