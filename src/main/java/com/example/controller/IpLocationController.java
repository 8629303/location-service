package com.example.controller;

import com.example.dto.IpLocationResponse;
import com.example.service.IpLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ip")
public class IpLocationController {

    @Autowired
    private IpLocationService ipLocationService;

    /**
     * 通过IP地址查询所在地区信息
     * @param ip ip地址
     * @return ip所在地区：国家、区域、省、城市、运营商
     */
    @GetMapping("/location")
    public IpLocationResponse getLocation(@RequestParam String ip) {
        String location = ipLocationService.getLocation(ip);
        return IpLocationResponse.fromString(location);
    }
}