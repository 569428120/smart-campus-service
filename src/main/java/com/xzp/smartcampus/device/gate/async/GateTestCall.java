package com.xzp.smartcampus.device.gate.async;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.device.agent.hpt.api.FaceCardMachineApi;
import com.xzp.smartcampus.device.agent.hpt.dto.FaceCardSysParamVo;
import com.xzp.smartcampus.device.gate.enums.RunLogType;
import com.xzp.smartcampus.device.gate.enums.TestStatus;
import com.xzp.smartcampus.device.gate.model.GateModel;
import com.xzp.smartcampus.device.gate.model.RunLogModel;
import com.xzp.smartcampus.device.gate.model.TestLogModel;
import com.xzp.smartcampus.device.gate.service.IRunLogService;
import com.xzp.smartcampus.device.gate.service.ITestLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GateTestCall {

    @Resource
    private ITestLogService testLogService;

    @Resource
    private IRunLogService runLogService;

    @Resource
    private FaceCardMachineApi faceCardMachineApi;


    /**
     * 异步启动测试
     *
     * @param testLogModel testLogModel
     */
    @Async
    public void testStart(TestLogModel testLogModel, GateModel gateModel) {
        try {
            // 启动前
            this.updateTestLogModel(testLogModel, 10, TestStatus.NORMAL.getKey(), Collections.singletonList("等待测试"));
            if (StringUtils.isBlank(gateModel.getNetAddress()) || StringUtils.isBlank(gateModel.getUserName()) || StringUtils.isBlank(gateModel.getPassword())) {
                log.error("netAddress {} or userName {} or password {} is null", gateModel.getNetAddress(), gateModel.getNetAddress(), gateModel.getPassword());
                throw new SipException("网络地址，用户名，密码不能为空");
            }

            // 获取设备参数
            Thread.sleep(1000);
            this.updateTestLogModel(testLogModel, 20, TestStatus.NORMAL.getKey(), Collections.singletonList("准备获取设备参数"));
            Thread.sleep(1000);
            FaceCardSysParamVo paramVo = faceCardMachineApi.getSysParam(gateModel.getNetAddress(), gateModel.getUserName(), gateModel.getPassword());
            this.updateTestLogModel(testLogModel, 25, TestStatus.NORMAL.getKey(),
                    Collections.singletonList(MessageFormat.format("获取设备参数成功 name {0} DeviceId {1} Version {2}", paramVo.getName(), paramVo.getDeviceId(), paramVo.getVersion())));
            // 设备id为空
            if (StringUtils.isBlank(paramVo.getDeviceId())) {
                throw new SipException("获取设备信息失败，DeviceId为空");
            }
            // 比对是否与设置的设备id一致
            if (paramVo.getDeviceId().equals(gateModel.getDeviceId())) {
                throw new SipException("输入的DeviceId " + gateModel.getDeviceId() + " 和实际的DeviceId " + paramVo.getDeviceId() + " 不符");
            }

            // 开门测试
            Thread.sleep(1000);
            this.updateTestLogModel(testLogModel, 45, TestStatus.NORMAL.getKey(), Collections.singletonList("开门控制测试...."));
            Thread.sleep(1000);
            faceCardMachineApi.openGate(gateModel.getNetAddress(), gateModel.getUserName(), gateModel.getPassword(), gateModel.getDeviceId(), "开门测试");
            this.updateTestLogModel(testLogModel, 45, TestStatus.NORMAL.getKey(), Collections.singletonList("开门测试完成"));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            this.updateTestLogModel(testLogModel, null, TestStatus.EXCEPTION.getKey(), Collections.singletonList("测试出错：" + e.getMessage()));
        }
        this.updateTestLogModel(testLogModel, 100, TestStatus.SUCCESS.getKey(), Collections.singletonList("测试完成"));
    }

    /**
     * 更新测试数据
     *
     * @param testLogModel 更新对象
     * @param percent      百分比
     * @param status       状态
     * @param descList     描述
     */
    private void updateTestLogModel(TestLogModel testLogModel, Integer percent, String status, List<String> descList) {
        if (StringUtils.isBlank(testLogModel.getId())) {
            throw new SipException("id不能为空");
        }
        if (percent != null) {
            testLogModel.setPercent(percent);
        }
        testLogModel.setStatus(status);
        if (!CollectionUtils.isEmpty(descList)) {
            testLogModel.setDescription(descList.get(0));
            List<RunLogModel> runLogModels = descList.stream().map(item -> {
                RunLogModel logModel = new RunLogModel();
                logModel.setId(SqlUtil.getUUId());
                logModel.setDId(testLogModel.getDId());
                logModel.setDeviceId(testLogModel.getDeviceId());
                logModel.setLogText(item);
                logModel.setLogType(RunLogType.TEST_LOG.getKey());
                return logModel;
            }).collect(Collectors.toList());
            runLogService.insertBatch(runLogModels);
        }

        testLogService.updateById(testLogModel);
    }
}
