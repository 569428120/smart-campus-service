package com.xzp.smartcampus.device.gate.model;


import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_device_ser_device_type")
public class DeviceTypeModel extends BaseModel {

    /**
     * 厂商id
     */
    private String manufacturerId;

    /**
     * 型号名称
     */
    private String name;

    /**
     * 型号版本
     */
    private String version;

    /**
     * 描述 IGNORED忽略判断 null也可以使用update方法
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;
}
