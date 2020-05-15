package com.xzp.smartcampus.device.gate.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.device.gate.enums.RunLogType;
import com.xzp.smartcampus.device.gate.model.RunLogModel;

import java.util.List;

public interface IRunLogService extends IBaseService<RunLogModel> {

    /**
     * 获取日志对象列表
     *
     * @param deviceId 设备id
     * @param type     类型
     * @return List<RunLogModel>
     */
    List<RunLogModel> getRunLogByDeviceId(String deviceId, RunLogType type);
}
