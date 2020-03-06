package com.xzp.smartcampus.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.mapper.RegionMapper;
import com.xzp.smartcampus.system.model.RegionModel;
import com.xzp.smartcampus.system.service.IRegionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RegionServiceImpl extends IsolationBaseService<RegionMapper, RegionModel> implements IRegionService {

    /**
     * 分页查询教育局数据
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return PageResult
     */
    @Override
    public PageResult getRegionListPage(RegionModel searchValue, Integer current, Integer pageSize) {
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<RegionModel>()
                .like(StringUtils.isNotBlank(searchValue.getRegionName()), "region_name", searchValue.getRegionName())
                .like(StringUtils.isNotBlank(searchValue.getEducationName()), "education_name", searchValue.getEducationName())
                .orderByDesc("create_time")
        );
    }

    /**
     * 保存教育局数据
     *
     * @param regionModel regionModel
     * @return RegionModel
     */
    @Override
    public RegionModel postRegionModel(RegionModel regionModel) {
        // 新增操作
        if (StringUtils.isBlank(regionModel.getId())) {
            regionModel.setId(SqlUtil.getUUId());
            this.insert(regionModel);
        } else {
            // 更新操作
            RegionModel localDbModel = this.selectById(regionModel.getId());
            if (localDbModel == null) {
                log.error("RegionModel not find id by {}", regionModel.getId());
                throw new SipException("数据异常，RegionModel的数据不存在 id " + regionModel.getId());
            }
            localDbModel.setRegionName(regionModel.getRegionName());
            localDbModel.setEducationName(regionModel.getEducationName());
            localDbModel.setDescription(regionModel.getDescription());
            this.updateById(localDbModel);
            return localDbModel;
        }
        return regionModel;
    }
}
