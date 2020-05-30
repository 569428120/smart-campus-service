package com.xzp.smartcampus.device.agent.hpt.dto;

import java.util.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode()
public class FaceCardDto {

    /**
     * 操作类型
     */
    private String operator;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 数据信息
     */
    private Map<String, Object> info;
}
