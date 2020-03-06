package com.xzp.smartcampus.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzp.smartcampus.common.model.BaseModel;

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

    /**
     * 插入数据
     *
     * @param model 数据
     */
    @Override
    public void insert(T model) {

    }
}
