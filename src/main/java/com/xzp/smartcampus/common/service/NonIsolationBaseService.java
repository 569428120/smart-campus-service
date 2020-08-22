package com.xzp.smartcampus.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import com.xzp.smartcampus.portal.vo.RegionInfo;
import com.xzp.smartcampus.portal.vo.SchoolInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 数据不隔离
 *
 * @author Administrator
 */
public class NonIsolationBaseService<M extends BaseMapper<T>, T extends BaseModel> extends BaseService<M, T> implements IBaseService<T> {

    /**
     * 设置数据隔离的参数
     *
     * @param wrapper  wrapper
     * @param userInfo userInfo
     */
    @Override
    protected void initTenant(QueryWrapper wrapper, LoginUserInfo userInfo) {

    }

    /**
     * 设置数据隔离的参数
     *
     * @param wrapper  wrapper
     * @param userInfo userInfo
     */
    @Override
    protected void initTenant(UpdateWrapper wrapper, LoginUserInfo userInfo) {

    }

    /**
     * 设置数据隔离的参数
     *
     * @param model    model
     * @param userInfo userInfo
     */
    @Override
    protected void initTenant(T model, LoginUserInfo userInfo) {
        RegionInfo regionInfo = userInfo.getCurrRegionInfo();
        SchoolInfo schoolInfo = userInfo.getCurrSchoolInfo();
        if (regionInfo != null && StringUtils.isBlank(model.getRegionId())) {
            model.setRegionId(regionInfo.getRegionId());
        }
        if (schoolInfo != null && StringUtils.isBlank(model.getSchoolId())) {
            model.setSchoolId(schoolInfo.getSchoolId());
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
