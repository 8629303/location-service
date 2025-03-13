package com.example.controller;

import com.example.dto.AddressRequest;
import com.example.dto.GeoResponse;
import com.example.dto.RegeoResponse;
import com.example.entity.Address;
import com.example.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 根据地址获取经纬度
     *
     * @param address 地址
     * @return 经纬度字符串，格式为"经度,纬度"
     */
    @GetMapping("/geocode")
    public GeoResponse geocode(@RequestParam String address) {
        return addressService.geocode(address);
    }

    /**
     * 根据经纬度获取详细地址信息
     *
     * @param longitude 经度
     * @param latitude 纬度
     * @return 地址信息
     */
    @GetMapping("/regeocode")
    public RegeoResponse regeocode(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        return addressService.reGeocode(latitude, longitude);
    }

    /**
     * 保存以及修改地址信息
     * @param request AddressRequest 地址详细信息
     * @return 返回保存成功的对象
     */
    @PostMapping
    public Address saveAddress(@RequestBody AddressRequest request) {
        return addressService.saveAddress(request);
    }

    /**
     * 查询地址列表
     * @return 地址列表
     */
    @GetMapping("/list")
    public List<Address> getAddresses() {
        return addressService.findAll();
    }
} 