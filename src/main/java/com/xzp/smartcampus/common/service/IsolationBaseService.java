package com.xzp.smartcampus.common.service;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import org.apache.commons.lang3.StringUtils;

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
    protected void initTenant(QueryWrapper<T> wrapper, LoginUserInfo userInfo) {
        this.setTenantWrapper(wrapper, userInfo);
    }

    private void setTenantWrapper(AbstractWrapper wrapper, LoginUserInfo userInfo) {
        MergeSegments segments = wrapper.getExpression();
        String normalSql = segments.getNormal().getSqlSegment();
        segments.getNormal().clear();
        segments.getNormal().add((ISqlSegment) () -> this.getTenantSqlSegment(userInfo));
        segments.getNormal().add((ISqlSegment) () -> "( " + (StringUtils.isBlank(normalSql) ? "1=1" : normalSql) + " )");
    }

    /**
     * 租户隔离sql条件
     *
     * @param userInfo userInfo
     * @return String
     */
    private String getTenantSqlSegment(LoginUserInfo userInfo) {
        if (userInfo == null) {
            return "";
        }
        return "(" +
                "region_id" + ((StringUtils.isBlank(userInfo.getRegionId()) ? " is null" : (" = '" + userInfo.getRegionId()) + "'")) +
                " AND " +
                "school_id" + ((StringUtils.isBlank(userInfo.getSchoolId()) ? " is null" : (" = '" + userInfo.getSchoolId()) + "'")) +
                ")" +
                " AND ";
    }

    /**
     * 设置数据隔离的参数
     *
     * @param wrapper  wrapper
     * @param userInfo userInfo
     */
    @Override
    protected void initTenant(UpdateWrapper<T> wrapper, LoginUserInfo userInfo) {
        this.setTenantWrapper(wrapper, userInfo);
    }

    /**
     * 设置数据隔离的参数
     *
     * @param model    model
     * @param userInfo userInfo
     */
    @Override
    protected void initTenant(T model, LoginUserInfo userInfo) {
        if (StringUtils.isBlank(model.getRegionId())) {
            model.setRegionId(userInfo.getRegionId());
        }
        if (StringUtils.isBlank(model.getSchoolId())) {
            model.setSchoolId(userInfo.getSchoolId());
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
