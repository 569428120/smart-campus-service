package com.xzp.smartcampus.device.agent.hpt.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统参数vo
 */
@Data
@EqualsAndHashCode()
public class FaceCardSysParamVo {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备版本
     */
    private String version;
}
