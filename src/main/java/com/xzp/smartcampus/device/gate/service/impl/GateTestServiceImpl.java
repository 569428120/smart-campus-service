package com.xzp.smartcampus.device.gate.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.device.gate.async.GateTestCall;
import com.xzp.smartcampus.device.gate.enums.RunLogType;
import com.xzp.smartcampus.device.gate.enums.TestStatus;
import com.xzp.smartcampus.device.gate.model.GateModel;
import com.xzp.smartcampus.device.gate.model.RunLogModel;
import com.xzp.smartcampus.device.gate.model.TestLogModel;
import com.xzp.smartcampus.device.gate.service.IGateTestService;
import com.xzp.smartcampus.device.gate.service.IRunLogService;
import com.xzp.smartcampus.device.gate.service.ITestLogService;
import com.xzp.smartcampus.device.gate.vo.TestLogVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class GateTestServiceImpl implements IGateTestService {

    @Resource
    private ITestLogService testLogService;

    @Resource
    private IRunLogService runLogService;

    @Resource
    private GateTestCall gateTestCall;


    /**
     * 启动测试
     *
     * @param gateModel gateModel
     * @return TestLogVo
     */
    @Override
    public TestLogVo startTest(GateModel gateModel) {
        if (StringUtils.isBlank(gateModel.getDeviceId())) {
            log.warn("deviceId is null");
            throw new SipException("deviceId不能为空");
        }
        TestLogModel testLogModel = testLogService.getTestLogByDeviceId(gateModel.getDeviceId());
        if (testLogModel != null && TestStatus.NORMAL.getKey().equals(testLogModel.getStatus())) {
            log.warn("test is runing by deviceId {}", gateModel.getDeviceId());
            throw new SipException("正在运行中，请勿重复启动 deviceId " + gateModel.getDeviceId());
        }
        // 创建一个
        if (testLogModel == null) {
            testLogModel = new TestLogModel();
            testLogModel.setId(SqlUtil.getUUId());
            testLogModel.setDeviceId(gateModel.getDeviceId());
            testLogService.insert(testLogModel);
        } else {
            // 删除之前的日志
            runLogService.delete(new UpdateWrapper<RunLogModel>()
                    .eq("flog_type", RunLogType.TEST_LOG.getKey())
                    .eq("device_id", gateModel.getDeviceId())
            );
        }
        // 启动测试
        gateTestCall.testStart(testLogModel, gateModel);
        testLogModel.setStatus(TestStatus.NORMAL.getKey());
        testLogService.updateById(testLogModel);
        return this.getTestLogVo(testLogModel);
    }

    /**
     * 查询测试数据
     *
     * @param deviceId 设备id
     * @return TestLogVo
     */
    @Override
    public TestLogVo getTestLogVoByDeviceId(String deviceId) {
        if (StringUtils.isBlank(deviceId)) {
            log.warn("deviceId is null");
            return null;
        }
        TestLogModel testLogModel = testLogService.getTestLogByDeviceId(deviceId);
        if (testLogModel == null) {
            log.warn("not find testLogModel by deviceId {}", deviceId);
            return null;
        }
        return this.getTestLogVo(testLogModel);
    }

    /**
     * 获取测试vo对象
     *
     * @param testLogModel testLogModel
     * @return TestLogVo
     */
    private TestLogVo getTestLogVo(TestLogModel testLogModel) {
        TestLogVo vo = new TestLogVo();
        BeanUtils.copyProperties(testLogModel, vo);
        vo.setLogList(runLogService.getRunLogByDeviceId(testLogModel.getDeviceId(), RunLogType.TEST_LOG));
        return vo;
    }
}
