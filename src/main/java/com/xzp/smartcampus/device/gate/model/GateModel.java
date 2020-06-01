package com.xzp.smartcampus.device.gate.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_device_ser_gate")
public class GateModel extends BaseModel {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 厂商id
     */
    private String manufacturerId;

    /**
     * 设备型号id
     */
    private String manufacturerTypeId;

    /**
     * 网络地址
     */
    private String netAddress;

    /**
     * 设备用户名
     */
    private String userName;

    /**
     * 设备密码
     */
    private String password;

    /**
     * 状态
     */
    private String status;

    /**
     * 描述 IGNORED忽略判断 null也可以使用update方法
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;

}
