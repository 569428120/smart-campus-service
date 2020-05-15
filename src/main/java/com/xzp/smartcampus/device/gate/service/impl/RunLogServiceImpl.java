package com.xzp.smartcampus.device.gate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.device.gate.enums.RunLogType;
import com.xzp.smartcampus.device.gate.mapper.RunLogMapper;
import com.xzp.smartcampus.device.gate.model.RunLogModel;
import com.xzp.smartcampus.device.gate.service.IRunLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RunLogServiceImpl extends IsolationBaseService<RunLogMapper, RunLogModel> implements IRunLogService {
    /**
     * 获取日志对象列表
     *
     * @param deviceId 设备id
     * @param type     类型
     * @return List<RunLogModel>
     */
    @Override
    public List<RunLogModel> getRunLogByDeviceId(String deviceId, RunLogType type) {
        if (StringUtils.isBlank(deviceId) || type == null) {
            log.warn("deviceId ro type is null");
            return Collections.emptyList();
        }
        return this.selectList(new QueryWrapper<RunLogModel>()
                .eq("device_id", deviceId)
                .eq("log_type", type.getKey())
        );
    }
}
