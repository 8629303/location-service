package com.example.service;

import com.example.dto.AddressRequest;
import com.example.dto.GeoResponse;
import com.example.dto.RegeoResponse;
import com.example.entity.Address;
import com.example.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class AddressService {
    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.geocode-url}")
    private String geocodeUrl;

    @Value("${amap.regeocode-url}")
    private String regeoCodeUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AddressRepository addressRepository;

    public GeoResponse geocode(String address) {
        String url = String.format("%s?address=%s&key=%s",
                geocodeUrl,
                address,
                amapKey);
        return restTemplate.getForObject(URI.create(url), GeoResponse.class);
    }

    public RegeoResponse reGeocode(double latitude, double longitude) {
        String location = longitude + "," + latitude;
        String url = String.format("%s?location=%s&key=%s",
                regeoCodeUrl,
                location,
                amapKey);
        return restTemplate.getForObject(url, RegeoResponse.class);
    }

    @Transactional
    public Address saveAddress(AddressRequest request) {
        Address address = new Address();
        address.setAddress(request.getAddress());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());
        address.setDetail(request.getDetail());
        return addressRepository.save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

} 