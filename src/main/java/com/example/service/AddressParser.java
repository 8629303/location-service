package com.example.service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressParser {

    public static Map<String, String> parseAddress(String text) {
        Map<String, String> result = new HashMap<>();

        // 1. HanLP 分词
        List<Term> termList = HanLP.segment(text);

        // 2. 先提取电话号码
        String phone = extractPhone(text);
        if (phone != null) {
            result.put("phone", phone);
        }

        // 3. 再提取姓名
        String name = extractName(termList, text, phone);
        if (name != null) {
            result.put("name", name);
        }

        // 4. 最后提取地址，确保完整
        String address = extractAddress(termList, text, name, phone);
        if (address != null) {
            result.put("address", address);
        }

        return result;
    }

    // 提取电话号码
    private static String extractPhone(String text) {
        String phonePattern = "(\\d{11})"; // 11位手机号
        Pattern pattern = Pattern.compile(phonePattern);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // 提取姓名
    private static String extractName(List<Term> termList, String text, String phone) {
        StringBuilder name = new StringBuilder();

        // 1. 优先使用 HanLP 词性识别（nr: 人名）
        for (Term term : termList) {
            if ("nr".equals(term.nature.toString())) {
                name.append(term.word);
            }
        }
        if (name.length() > 0) {
            return name.toString();
        }

        // 2. 若 HanLP 识别失败，尝试从手机号前后匹配2-5个汉字
        if (phone != null) {
            int phoneIndex = text.indexOf(phone);
            if (phoneIndex != -1) {
                // (1) 电话号码后面2-5个汉字
                String afterPhone = text.substring(phoneIndex + phone.length()).trim();
                Matcher matcher = Pattern.compile("^[\\u4e00-\\u9fa5]{2,5}").matcher(afterPhone);
                if (matcher.find()) {
                    return matcher.group(0);
                }

                // (2) 电话号码前面2-5个汉字
                String beforePhone = text.substring(0, phoneIndex).trim();
                matcher = Pattern.compile("[\\u4e00-\\u9fa5]{2,5}$").matcher(beforePhone);
                if (matcher.find()) {
                    return matcher.group(0);
                }
            }
        }

        return null;
    }

    // 提取地址信息，确保完整性
    private static String extractAddress(List<Term> termList, String text, String name, String phone) {
        StringBuilder address = new StringBuilder();
        Set<String> extractedTerms = new LinkedHashSet<>();

        // 1. 先从分词结果提取地名（词性 ns）
        for (Term term : termList) {
            if ("ns".equals(term.nature.toString())) {
                extractedTerms.add(term.word);
            }
        }

        // 2. 将地名拼接成完整地址
        for (String addrPart : extractedTerms) {
            address.append(addrPart).append(" ");
        }

        // 3. 兜底方案：如果地址长度过短，则直接从文本中扣除姓名和手机号，获取剩余部分作为地址
        String remainingText = text;
        if (name != null) {
            remainingText = remainingText.replace(name, "").trim();
        }
        if (phone != null) {
            remainingText = remainingText.replace(phone, "").trim();
        }

        // 如果地址部分明显过短，则直接使用剩余文本作为地址
        if (address.length() < 5 || remainingText.length() > address.length()) {
            return remainingText;
        }

        return address.toString().trim();
    }
}