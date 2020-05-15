package com.xzp.smartcampus.device.gate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.device.gate.mapper.TestLogMapper;
import com.xzp.smartcampus.device.gate.model.TestLogModel;
import com.xzp.smartcampus.device.gate.service.ITestLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class TestLogServiceImpl extends IsolationBaseService<TestLogMapper, TestLogModel> implements ITestLogService {

    /**
     * 查询测试对象
     *
     * @param deviceId deviceId
     * @return TestLogModel
     */
    @Override
    public TestLogModel getTestLogByDeviceId(String deviceId) {
        if (StringUtils.isBlank(deviceId)) {
            log.warn("deviceId is null");
            return null;
        }
        List<TestLogModel> testLogModels = this.selectList(new QueryWrapper<TestLogModel>()
                .eq("device_id", deviceId)
        );
        if (CollectionUtils.isEmpty(testLogModels)) {
            return null;
        }
        return testLogModels.get(0);
    }
}
