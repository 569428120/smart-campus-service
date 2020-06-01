package com.xzp.smartcampus.device.gate.vo;

import com.xzp.smartcampus.device.gate.model.DeviceTypeModel;
import com.xzp.smartcampus.device.gate.model.ManufacturerModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManufacturerVo extends ManufacturerModel {

    private List<DeviceTypeModel> deviceTypeList;
}
