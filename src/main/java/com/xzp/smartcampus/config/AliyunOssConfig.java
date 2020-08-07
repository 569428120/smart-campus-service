package com.xzp.smartcampus.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class AliyunOssConfig {
    private String endpoint;
    private String bucketUrl;
    private String keyid;
    private String keysecret;
    private String bucketname;
    private String filehost;
}
