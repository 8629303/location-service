package com.example.controller;

import com.example.service.AddressParser;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/address/parse")
public class AddressParserController {

    /**
     * 地址识别功能，地址管理系统需要实现一个“粘贴并识别”功能，自动从文本中提取姓名、电话和地址。【POST请求】
     * @param text 地址信息
     * @return 拆分的地址信息
     */
    @PostMapping
    public Map<String, String> parseAddressPost(@RequestBody String text) {
        return AddressParser.parseAddress(text);
    }

    /**
     * 地址识别功能，地址管理系统需要实现一个“粘贴并识别”功能，自动从文本中提取姓名、电话和地址。【GET请求】
     * @param text 地址信息
     * @return 拆分的地址信息
     */
    @GetMapping
    public Map<String, String> parseAddressGet(@RequestParam String text) {
        return AddressParser.parseAddress(text);
    }
}