package com.xzp.smartcampus.device.gate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.device.gate.mapper.GateMapper;
import com.xzp.smartcampus.device.gate.model.DeviceTypeModel;
import com.xzp.smartcampus.device.gate.model.GateModel;
import com.xzp.smartcampus.device.gate.model.ManufacturerModel;
import com.xzp.smartcampus.device.gate.service.IDeviceTypeService;
import com.xzp.smartcampus.device.gate.service.IGateService;
import com.xzp.smartcampus.device.gate.service.IManufacturerService;
import com.xzp.smartcampus.system.vo.GateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class GateServiceImpl extends IsolationBaseService<GateMapper, GateModel> implements IGateService {

    @Resource
    private IManufacturerService manufacturerService;

    @Resource
    private IDeviceTypeService deviceTypeService;

    /**
     * 分页查询
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    每页显示的数量
     * @return PageResult<GateModel>
     */
    @Override
    public PageResult<GateVo> getGateListPage(GateModel searchValue, Integer current, Integer pageSize) {
        PageResult<GateModel> gatePage = this.selectPage(new Page<>(current, pageSize), new QueryWrapper<GateModel>()
                .eq(StringUtils.isNotBlank(searchValue.getManufacturerId()), "manufacturer_id", searchValue.getManufacturerId())
                .eq(StringUtils.isNotBlank(searchValue.getManufacturerTypeId()), "manufacturer_type_id", searchValue.getManufacturerTypeId())
                .eq(StringUtils.isNotBlank(searchValue.getStatus()), "status", searchValue.getStatus())
        );
        // 转出vo对象
        return this.toGateVoPage(gatePage);
    }

    /**
     * 转为Vo对象
     *
     * @param gatePage gatePage
     * @return PageResult<GateVo>
     */
    private PageResult<GateVo> toGateVoPage(PageResult<GateModel> gatePage) {
        return new PageResult<>(gatePage.getTotal(), gatePage.getTotalPage(), this.toGateVoList(gatePage.getData()));
    }

    /**
     * 转换为vo对象
     *
     * @param gateModels gateModels
     * @return gateModels
     */
    private List<GateVo> toGateVoList(List<GateModel> gateModels) {
        if (CollectionUtils.isEmpty(gateModels)) {
            return Collections.emptyList();
        }
        Set<String> manufacturerIds = new HashSet<>();
        Set<String> manufacturerTypeIds = new HashSet<>();
        gateModels.forEach(item -> {
            manufacturerIds.add(item.getManufacturerId());
            manufacturerTypeIds.add(item.getManufacturerTypeId());
        });
        Map<String, ManufacturerModel> manufacturerIdToModelMap = this.getManufacturerIdToModelMap(manufacturerIds);
        Map<String, DeviceTypeModel> manufacturerTypeIdToModelMap = this.getManufacturerTypeIdToModelMap(manufacturerTypeIds);
        // 转换为vo
        return gateModels.stream().map(item -> {
            GateVo gateVo = new GateVo();
            BeanUtils.copyProperties(item, gateVo);
            ManufacturerModel manufacturerModel = manufacturerIdToModelMap.get(item.getManufacturerId());
            DeviceTypeModel deviceTypeModel = manufacturerTypeIdToModelMap.get(item.getManufacturerTypeId());
            if (manufacturerModel != null) {
                gateVo.setManufacturerName(manufacturerModel.getName());
            }
            if (deviceTypeModel != null) {
                gateVo.setManufacturerType(deviceTypeModel.getName());
                gateVo.setTypeDescription(deviceTypeModel.getDescription());
            }
            return gateVo;
        }).collect(Collectors.toList());
    }

    /**
     * 型号映射
     *
     * @param manufacturerTypeIds manufacturerTypeIds
     * @return 映射
     */
    private Map<String, DeviceTypeModel> getManufacturerTypeIdToModelMap(Set<String> manufacturerTypeIds) {
        if (CollectionUtils.isEmpty(manufacturerTypeIds)) {
            log.info("manufacturerTypeIds is null");
            return Collections.emptyMap();
        }
        List<DeviceTypeModel> deviceTypeModels = deviceTypeService.selectByIds(manufacturerTypeIds);
        if (CollectionUtils.isEmpty(deviceTypeModels)) {
            log.info("deviceTypeModels is null by manufacturerTypeIds {}", manufacturerTypeIds);
            return Collections.emptyMap();
        }
        return deviceTypeModels.stream().collect(Collectors.toMap(DeviceTypeModel::getId, v -> v));
    }

    /**
     * 映射
     *
     * @param manufacturerIds manufacturerIds
     * @return 映射
     */
    private Map<String, ManufacturerModel> getManufacturerIdToModelMap(Set<String> manufacturerIds) {
        if (CollectionUtils.isEmpty(manufacturerIds)) {
            log.info("manufacturerIds is null");
            return Collections.emptyMap();
        }
        List<ManufacturerModel> manufacturerModels = manufacturerService.selectByIds(manufacturerIds);
        if (CollectionUtils.isEmpty(manufacturerModels)) {
            log.info("manufacturerModels is null by manufacturerIds {}", manufacturerIds);
            return Collections.emptyMap();
        }
        return manufacturerModels.stream().collect(Collectors.toMap(ManufacturerModel::getId, v -> v));
    }

    /**
     * 保存
     *
     * @param gateModel gateModel
     * @return GateModel
     */
    @Override
    public GateModel saveGateModel(GateModel gateModel) {
        // 校验
        this.validatorGate(gateModel);
        // 新增
        if (StringUtils.isBlank(gateModel.getId())) {
            gateModel.setId(SqlUtil.getUUId());
            this.insert(gateModel);
            return gateModel;
        }
        this.updateGateModel(gateModel);
        return gateModel;
    }

    /**
     * 跟新
     *
     * @param gateModel gateModel
     */
    private void updateGateModel(GateModel gateModel) {
        if (StringUtils.isBlank(gateModel.getId())) {
            log.error("id is null");
            throw new SipException("数据错误，更新操作id不能为空");
        }
        this.updateById(gateModel);
    }

    /**
     * 校验数据
     *
     * @param gateModel gateModel
     */
    @Override
    public void validatorGate(GateModel gateModel) {
        if (StringUtils.isBlank(gateModel.getDeviceId())) {
            log.warn("deviceId is null");
            throw new SipException("deviceId 不能为空");
        }
        // deviceId 不能重复
        List<GateModel> gateModels = this.selectList(new QueryWrapper<GateModel>()
                .eq("device_id", gateModel.getDeviceId())
                .notIn(StringUtils.isNotBlank(gateModel.getId()), "id", Collections.singleton(gateModel.getId()))
        );
        if (!CollectionUtils.isEmpty(gateModels)) {
            log.warn("device_id exist");
            throw new SipException("deviceId " + gateModel.getDeviceId() + " 已存在");
        }
    }
}
