package com.xzp.smartcampus.device.gate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.device.gate.enums.ManufacturerType;
import com.xzp.smartcampus.device.gate.mapper.ManufacturerMapper;
import com.xzp.smartcampus.device.gate.model.DeviceTypeModel;
import com.xzp.smartcampus.device.gate.model.ManufacturerModel;
import com.xzp.smartcampus.device.gate.service.IDeviceTypeService;
import com.xzp.smartcampus.device.gate.service.IManufacturerService;
import com.xzp.smartcampus.device.gate.vo.ManufacturerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ManufacturerServiceImpl extends IsolationBaseService<ManufacturerMapper, ManufacturerModel> implements IManufacturerService {

    @Resource
    private IDeviceTypeService deviceTypeService;

    /**
     * 获取门闸厂商
     *
     * @return List<ManufacturerVo>
     */
    @Override
    public List<ManufacturerVo> getGateManufacturerVoList() {
        List<ManufacturerModel> manufacturerModels = this.selectList(new QueryWrapper<ManufacturerModel>()
                .eq("m_type", ManufacturerType.GATE.getKey())
        );
        if (CollectionUtils.isEmpty(manufacturerModels)) {
            return Collections.emptyList();
        }
        List<String> manufacturerIds = manufacturerModels.stream().map(ManufacturerModel::getId).collect(Collectors.toList());
        Map<String, List<DeviceTypeModel>> manufacturerIdToDeviceListMap = this.getManufacturerIdToDeviceListMap(manufacturerIds);
        return this.toManufacturerVo(manufacturerModels, manufacturerIdToDeviceListMap);
    }

    /**
     * vo列表
     *
     * @param manufacturerModels            model
     * @param manufacturerIdToDeviceListMap 设备数据
     * @return List<ManufacturerVo>
     */
    private List<ManufacturerVo> toManufacturerVo(List<ManufacturerModel> manufacturerModels, Map<String, List<DeviceTypeModel>> manufacturerIdToDeviceListMap) {
        return manufacturerModels.stream().map(item -> {
            ManufacturerVo vo = new ManufacturerVo();
            BeanUtils.copyProperties(item, vo);
            vo.setDeviceTypeList(manufacturerIdToDeviceListMap.get(item.getId()));
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 厂商和设备的关系
     *
     * @param manufacturerIds manufacturerIds
     * @return 映射
     */
    private Map<String, List<DeviceTypeModel>> getManufacturerIdToDeviceListMap(List<String> manufacturerIds) {
        if (CollectionUtils.isEmpty(manufacturerIds)) {
            return Collections.emptyMap();
        }
        List<DeviceTypeModel> deviceTypeModels = deviceTypeService.selectList(new QueryWrapper<DeviceTypeModel>()
                .in("manufacturer_id", manufacturerIds)
        );
        if (CollectionUtils.isEmpty(deviceTypeModels)) {
            log.info("deviceTypeModels is null by manufacturerIds {}", manufacturerIds);
            return Collections.emptyMap();
        }
        Map<String, List<DeviceTypeModel>> manufacturerIdToDeviceListMap = new HashMap<>(manufacturerIds.size());
        deviceTypeModels.forEach(item -> {
            List<DeviceTypeModel> models = manufacturerIdToDeviceListMap.computeIfAbsent(item.getManufacturerId(), k -> new ArrayList<>());
            models.add(item);
        });
        return manufacturerIdToDeviceListMap;
    }
}
