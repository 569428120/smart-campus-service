package com.xzp.smartcampus.device.agent.hpt.utils;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.JsonUtils;
import com.xzp.smartcampus.device.agent.hpt.dto.FaceCardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestUtils {

    /**
     * post请求
     *
     * @param template 模板
     * @param url      ip:post
     * @param userName 用户名
     * @param password 密码
     * @param body     请求体
     * @return String
     */
    private static String httpPost(RestTemplate template, String url, String userName, String password, String body) {
        log.debug("url {} body {}", url, body);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", RestUtils.getBasicAuthorization(userName, password));
        HttpEntity<String> requestEntity = new HttpEntity<>(body, requestHeaders);
        ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
        // 请求失败
        if (response.getStatusCodeValue() < 200 && response.getStatusCodeValue() > 300) {
            log.error("send request error {}", response.getBody());
            throw new SipException("请求失败, " + response.getBody());
        }
        log.debug("response {}", response.getBody());
        return response.getBody();
    }

    /**
     * post请求
     *
     * @param template 模板
     * @param url      ip:post
     * @param userName 用户名
     * @param password 密码
     * @param dto      请求体
     * @return FaceCardDto
     */
    public static FaceCardDto httpPostDto(RestTemplate template, String url, String userName, String password, FaceCardDto dto) {
        String str = RestUtils.httpPost(template, url, userName, password, dto == null ? "" : JsonUtils.toString(dto));
        FaceCardDto rDto = JsonUtils.toBean(str, FaceCardDto.class);
        if (rDto == null) {
            throw new SipException("转换为Dto对象失败");
        }
        return rDto;
    }

    /**
     * 基础认证头
     *
     * @param userName 用户名
     * @param password 密码
     * @return String
     */
    private static String getBasicAuthorization(String userName, String password) {
        String str = userName + ":" + password;
        try {
            return "Basic " + Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Base64 encodeToString error", e);
            throw new SipException("base64 编码出错 " + e.getMessage());
        }
    }
}
