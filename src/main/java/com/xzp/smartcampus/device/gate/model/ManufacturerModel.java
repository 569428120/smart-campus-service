package com.xzp.smartcampus.device.gate.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_device_ser_manufacturer")
public class ManufacturerModel extends BaseModel {

    /**
     * 厂商类型  门禁，等等
     */
    private String mType;

    /**
     * 厂商名称
     */
    private String name;

    /**
     * 联系方式
     */
    private String contact;
}
