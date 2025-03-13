package com.example.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class IpLocationService {
    private Searcher searcher;
    
    @PostConstruct
    public void init() {
        try {
            byte[] bytes = IoUtil.readBytes(ResourceUtil.getStream("ip2region.xdb"));
            this.searcher = Searcher.newWithBuffer(bytes);
        } catch (Exception e) {
            log.error("IP2Region 初始化失败", e);
        }
    }
    
    public String getLocation(String ip) {
        try {
            return searcher.search(ip);
        } catch (Exception e) {
            log.error("IP查询失败: {}", ip, e);
            return "未知位置";
        }
    }
} 