package com.xzp.smartcampus.device.gate.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.device.gate.model.ManufacturerModel;
import com.xzp.smartcampus.device.gate.vo.ManufacturerVo;

import java.util.List;

public interface IManufacturerService extends IBaseService<ManufacturerModel> {

    /**
     * 获取门闸厂商
     *
     * @return List<ManufacturerVo>
     */
    List<ManufacturerVo> getGateManufacturerVoList();
}
