package com.xzp.smartcampus.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 数据隔离基础业务类
 * 数据需要按照教育局和学校隔离
 *
 * @author Administrator
 */
public class IsolationBaseService<M extends BaseMapper<T>, T extends BaseModel> extends BaseService<M, T> implements IBaseService<T> {


    /**
     * 设置数据隔离的参数
     *
     * @param wrapper  wrapper
     * @param userInfo userInfo
     */
    @Override
    protected void initTenant(QueryWrapper wrapper, LoginUserInfo userInfo) {
        if (!CollectionUtils.isEmpty(userInfo.getRegionIdToName())) {
            wrapper.in("region_id", userInfo.getRegionIdToName().keySet());
        }
        if (!CollectionUtils.isEmpty(userInfo.getSchoolIdToName())) {
            wrapper.in("school_id", userInfo.getSchoolIdToName().keySet());
        }
    }

    /**
     * 设置数据隔离的参数
     *
     * @param wrapper  wrapper
     * @param userInfo userInfo
     */
    @Override
    protected void initTenant(UpdateWrapper wrapper, LoginUserInfo userInfo) {
        if (!CollectionUtils.isEmpty(userInfo.getRegionIdToName())) {
            wrapper.in("region_id", userInfo.getRegionIdToName().keySet());
        }
        if (!CollectionUtils.isEmpty(userInfo.getSchoolIdToName())) {
            wrapper.in("school_id", userInfo.getSchoolIdToName().keySet());
        }
    }

    /**
     * 设置数据隔离的参数
     *
     * @param model    model
     * @param userInfo userInfo
     */
    @Override
    protected void initTenant(T model, LoginUserInfo userInfo) {
        if (!CollectionUtils.isEmpty(userInfo.getRegionIdToName()) && StringUtils.isBlank(model.getRegionId())) {
            userInfo.getRegionIdToName().keySet().forEach(regionId -> {
                model.setRegionId(regionId);
            });

        }
        if (!CollectionUtils.isEmpty(userInfo.getSchoolIdToName()) && StringUtils.isBlank(model.getSchoolId())) {
            userInfo.getSchoolIdToName().keySet().forEach(schoolId -> {
                model.setSchoolId(schoolId);
            });
        }
    }

    /**
     * 设置数据隔离的参数
     *
     * @param models   models
     * @param userInfo userInfo
     */
    @Override
    protected void initTenant(Collection<T> models, LoginUserInfo userInfo) {
        models.forEach(item -> {
            this.initTenant(item, userInfo);
        });
    }
}
