package com.xzp.smartcampus;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.xzp.smartcampus.*.mapper,com.xzp.smartcampus.*.*.mapper")
@Slf4j
public class SmartCampusServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCampusServiceApplication.class, args);
        log.info("start SmartCampusServiceApplication");
    }

}
