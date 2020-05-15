package com.xzp.smartcampus.system.vo;

import com.xzp.smartcampus.device.gate.model.GateModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GateVo extends GateModel {

    /**
     * 厂商名称
     */
    private String manufacturerName;

    /**
     * 设备型号名称
     */
    private String manufacturerType;

    /**
     * 型号描述
     */
    private String typeDescription;
}
