package com.xzp.smartcampus.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.common.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 租户隔离的基础业务类
 *
 * @param <M>
 * @param <T>
 */
@Slf4j
public abstract class BaseService<M extends BaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> implements IBaseService<T> {

    /**
     * 设置数据隔离的参数
     *
     * @param wrapper wrapper
     */
    protected abstract void initTenant(QueryWrapper wrapper);

    /**
     * 设置数据隔离的参数
     *
     * @param wrapper wrapper
     */
    protected abstract void initTenant(UpdateWrapper wrapper);


    /**
     * 设置数据隔离的参数
     *
     * @param model model
     */
    protected abstract void initTenant(T model);

    /**
     * 设置数据隔离的参数
     *
     * @param models models
     */
    protected abstract void initTenant(List<T> models);


    @Override
    public List<T> selectList(QueryWrapper<T> wrapper) {
        this.initTenant(wrapper);
        return this.list(wrapper);
    }

    @Override
    public void insertBatch(List<T> models) {
        if (models == null || models.isEmpty()) {
            return;
        }
        this.initTenant(models);
        this.saveBatch(models);

    }

    /**
     * 根据ID查询
     *
     * @param id id
     * @return T
     */
    @Override
    public T selectById(Long id) {
        return this.getById(id);
    }

    /**
     * 根据id
     *
     * @param id id
     */
    @Override
    public void deleteById(Long id) {
        this.deleteById(id);
    }

    /**
     * 插入数据
     *
     * @param model 数据
     */
    @Override
    public void insert(T model) {
        this.initTenant(model);
        this.save(model);
    }

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param wrapper 过滤条件
     * @return PageResult
     */
    @Override
    public PageResult<T> selectPage(Page<T> page, QueryWrapper<T> wrapper) {
        this.initTenant(wrapper);
        IPage<T> iPage = this.baseMapper.selectPage(page, wrapper);
        if (iPage == null) {
            throw new SipException("selectPage error");
        }
        return new PageResult<T>(iPage.getTotal(), (int) iPage.getPages(), iPage.getRecords());
    }

    /**
     * 批量删除
     *
     * @param ids ids
     */
    @Override
    public void deleteByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            log.info("ids is null");
            return;
        }
        this.removeByIds(ids);
    }

    @Override
    public void delete(UpdateWrapper<T> wrapper) {
        this.initTenant(wrapper);
        this.remove(wrapper);
    }


}
