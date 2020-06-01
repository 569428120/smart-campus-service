package com.xzp.smartcampus.device.gate.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_device_ser_test_log")
public class TestLogModel extends BaseModel {

    /**
     * 设备的主键
     */
    private String dId;

    /**
     * 设备deviceId
     */
    private String deviceId;

    /**
     * 百分比
     */
    private Integer percent;

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
