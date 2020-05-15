package com.xzp.smartcampus.device.gate.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_device_ser_run_log")
public class RunLogModel extends BaseModel {

    /**
     * 设备的主键
     */
    private String dId;

    /**
     * 设备deviceId
     */
    private String deviceId;

    /**
     * log类型  测试日志 运行日志
     */
    private String logType;

    /**
     * 日志内容
     */
    private String logText;
}
