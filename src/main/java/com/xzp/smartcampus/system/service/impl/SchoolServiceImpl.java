package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.service.NonIsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.mapper.SchoolMapper;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.model.SchoolModel;
import com.xzp.smartcampus.system.service.IRegionService;
import com.xzp.smartcampus.system.service.ISchoolService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SchoolServiceImpl extends NonIsolationBaseService<SchoolMapper, SchoolModel> implements ISchoolService {

    @Resource
    private IRegionService regionService;

    /**
     * 分页查询
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    每页显示的数量
     * @return PageResult
     */
    @Override
    public PageResult getSchoolListPage(SchoolModel searchValue, Integer current, Integer pageSize) {
        PageResult pageResult = this.selectPage(new Page<>(current, pageSize), new QueryWrapper<SchoolModel>()
                .eq(StringUtils.isNotBlank(searchValue.getRegionId()), "region_id", searchValue.getRegionId())
                .like(StringUtils.isNotBlank(searchValue.getSchoolName()), "school_name", searchValue.getSchoolName())
                .orderByDesc("create_time"));
        this.setRegionName(pageResult.getData());
        return pageResult;
    }

    /**
     * 设置教育局名称
     *
     * @param data data
     */
    private void setRegionName(List<SchoolModel> data) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        Set<String> regionIds = data.stream().map(SchoolModel::getRegionId).collect(Collectors.toSet());
        Map<String, RegionModel> regionIdToModelMap = this.getRegionIdToModelMap(regionIds);
        data.forEach(item -> {
            RegionModel regionModel = regionIdToModelMap.get(item.getRegionId());
            if (regionModel != null) {
                item.setRegionName(regionModel.getRegionName());
            }
        });
    }

    /**
     * 教育局id 映射model
     *
     * @param regionIds regionIds
     * @return Map<String, RegionModel>
     */
    private Map<String, RegionModel> getRegionIdToModelMap(Set<String> regionIds) {
        if (CollectionUtils.isEmpty(regionIds)) {
            return Collections.emptyMap();
        }
        List<RegionModel> regionModels = regionService.selectByIds(regionIds);
        if (CollectionUtils.isEmpty(regionModels)) {
            log.info("not find regionModels by regionIds {}", regionIds);
            return Collections.emptyMap();
        }
        return regionModels.stream().collect(Collectors.toMap(RegionModel::getId, v -> v, (v1, v2) -> v2));
    }

    /**
     * 保存或者更新
     *
     * @param schoolModel schoolModel
     * @return SchoolModel
     */
    @Override
    public SchoolModel postSchoolModel(SchoolModel schoolModel) {
        // 新增操作
        if (StringUtils.isBlank(schoolModel.getId())) {
            schoolModel.setId(SqlUtil.getUUId());
            this.insert(schoolModel);
            return schoolModel;
        }
        // 更新操作
        SchoolModel localDbModel = this.selectById(schoolModel.getId());
        localDbModel.setRegionId(schoolModel.getRegionId());
        localDbModel.setSchoolName(schoolModel.getSchoolName());
        localDbModel.setSchoolLevel(schoolModel.getSchoolLevel());
        localDbModel.setSchoolType(schoolModel.getSchoolType());
        localDbModel.setAddress(schoolModel.getAddress());
        localDbModel.setContact(schoolModel.getContact());
        localDbModel.setDescription(schoolModel.getDescription());
        this.updateById(localDbModel);
        return localDbModel;
    }
}
