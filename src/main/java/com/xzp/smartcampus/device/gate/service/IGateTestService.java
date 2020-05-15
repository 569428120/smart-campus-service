package com.xzp.smartcampus.device.gate.service;

import com.xzp.smartcampus.device.gate.model.GateModel;
import com.xzp.smartcampus.device.gate.vo.TestLogVo;

public interface IGateTestService {

    /**
     * 启动测试
     *
     * @param gateModel gateModel
     * @return TestLogVo
     */
    TestLogVo startTest(GateModel gateModel);

    /**
     * 查询测试数据
     *
     * @param deviceId 设备id
     * @return TestLogVo
     */
    TestLogVo getTestLogVoByDeviceId(String deviceId);
}
