package com.xzp.smartcampus.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzp.smartcampus.common.model.BaseModel;

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
     * @param wrapper wrapper
     */
    @Override
    protected void initTenant(QueryWrapper wrapper) {

    }

    /**
     * 设置数据隔离的参数
     *
     * @param wrapper wrapper
     */
    @Override
    protected void initTenant(UpdateWrapper wrapper) {

    }

    /**
     * 设置数据隔离的参数
     *
     * @param model model
     */
    @Override
    protected void initTenant(BaseModel model) {

    }

    /**
     * 设置数据隔离的参数
     *
     * @param models models
     */
    @Override
    protected void initTenant(Collection models) {

    }
}
