package com.xzp.smartcampus.device.gate.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.device.gate.model.TestLogModel;

public interface ITestLogService extends IBaseService<TestLogModel> {

    /**
     * 查询测试对象
     *
     * @param deviceId deviceId
     * @return TestLogModel
     */
    TestLogModel getTestLogByDeviceId(String deviceId);
}
