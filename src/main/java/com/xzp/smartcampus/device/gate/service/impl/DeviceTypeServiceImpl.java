package com.xzp.smartcampus.device.gate.service.impl;

import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.device.gate.mapper.DeviceTypeMapper;
import com.xzp.smartcampus.device.gate.model.DeviceTypeModel;
import com.xzp.smartcampus.device.gate.service.IDeviceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class DeviceTypeServiceImpl extends IsolationBaseService<DeviceTypeMapper, DeviceTypeModel> implements IDeviceTypeService {
}
