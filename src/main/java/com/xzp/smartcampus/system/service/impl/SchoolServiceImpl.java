package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.NonIsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.StaffMapper;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.system.mapper.SchoolMapper;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.model.SchoolModel;
import com.xzp.smartcampus.system.service.IRegionService;
import com.xzp.smartcampus.system.service.ISchoolService;
import com.xzp.smartcampus.system.vo.SchoolVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    private StaffMapper userMapper;

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
        PageResult<SchoolModel> pageResult = this.selectPage(new Page<>(current, pageSize), new QueryWrapper<SchoolModel>()
                .eq(StringUtils.isNotBlank(searchValue.getRegionId()), "region_id", searchValue.getRegionId())
                .like(StringUtils.isNotBlank(searchValue.getSchoolName()), "school_name", searchValue.getSchoolName())
                .orderByDesc("create_time"));
        return new PageResult<>(pageResult.getTotal(), pageResult.getTotalPage(), this.toSchoolVoList(pageResult.getData()));
    }

    /**
     * 转换为vo对象
     *
     * @param schoolModels data
     * @return SchoolVo
     */
    private List<SchoolVo> toSchoolVoList(List<SchoolModel> schoolModels) {
        if (CollectionUtils.isEmpty(schoolModels)) {
            return Collections.emptyList();
        }
        List<StaffModel> staffModels = userMapper.selectBatchIds(schoolModels.stream().map(SchoolModel::getAdminUserId).collect(Collectors.toList()));
        Map<String, StaffModel> userIdToModelMap = CollectionUtils.isEmpty(staffModels) ? Collections.emptyMap() : staffModels.stream().collect(Collectors.toMap(StaffModel::getId, v -> v));
        Set<String> regionIds = schoolModels.stream().map(SchoolModel::getRegionId).collect(Collectors.toSet());
        Map<String, RegionModel> regionIdToModelMap = this.getRegionIdToModelMap(regionIds);
        return schoolModels.stream().map(item -> {
            SchoolVo vo = new SchoolVo();
            BeanUtils.copyProperties(item, vo);
            StaffModel admin = userIdToModelMap.get(item.getAdminUserId());
            if (admin != null) {
                vo.setUserName(admin.getUserName());
                vo.setPassword(admin.getUserPassword());
                vo.setContact(admin.getContact());
            }
            RegionModel regionModel = regionIdToModelMap.get(item.getRegionId());
            if (regionModel != null) {
                vo.setRegionName(regionModel.getRegionName());
            }
            return vo;
        }).collect(Collectors.toList());
    }


    /**
     * 教育局id 映射model
     *
     * @param regionIds regionIds
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
     * @param schoolVo schoolVo
     * @return SchoolModel
     */
    @Override
    public SchoolVo postSchoolModel(SchoolVo schoolVo) {
        // 新增操作
        if (StringUtils.isBlank(schoolVo.getId())) {
            if (StringUtils.isBlank(schoolVo.getRegionId())) {
                log.error("regionId is null");
                throw new SipException("数据错误，regionId不能为空");
            }
            schoolVo.setId(SqlUtil.getUUId());
            schoolVo.setSchoolId(schoolVo.getId());
            StaffModel admin = regionService.createAdminUserGroup(schoolVo.getRegionId(), schoolVo.getId(), schoolVo.getAuthorityTemplateId(), schoolVo.getPassword(), schoolVo.getContact());
            schoolVo.setAdminUserId(admin.getId());
            this.insert(schoolVo);
            return schoolVo;
        }
        StaffModel staffModel = regionService.updateAdminUser(schoolVo.getRegionId(), schoolVo.getId(), schoolVo.getAdminUserId(), schoolVo.getAuthorityTemplateId(), schoolVo.getPassword(), schoolVo.getContact());
        schoolVo.setAdminUserId(staffModel.getId());
        // 更新操作
        this.updateSchool(schoolVo);
        return schoolVo;
    }

    /**
     * 更新学校数据
     *
     * @param schoolVo schoolVo
     */
    private void updateSchool(SchoolVo schoolVo) {
        if (schoolVo == null || StringUtils.isBlank(schoolVo.getId())) {
            log.warn("schoolModel or id is null");
            return;
        }
        SchoolModel localDbModel = this.selectById(schoolVo.getId());
        localDbModel.setRegionId(schoolVo.getRegionId());
        localDbModel.setSchoolName(schoolVo.getSchoolName());
        localDbModel.setSchoolLevel(schoolVo.getSchoolLevel());
        localDbModel.setSchoolType(schoolVo.getSchoolType());
        localDbModel.setAddress(schoolVo.getAddress());
        localDbModel.setContact(schoolVo.getContact());
        localDbModel.setDescription(schoolVo.getDescription());
        localDbModel.setAdminUserId(schoolVo.getAdminUserId());
        this.updateById(localDbModel);
    }
}
