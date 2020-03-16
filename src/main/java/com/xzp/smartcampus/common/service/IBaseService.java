package com.xzp.smartcampus.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.common.vo.PageResult;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IBaseService<T extends BaseModel> {
    /**
     * 查询
     *
     * @param wrapper 条件
     * @return List<T>
     */
    List<T> selectList(QueryWrapper<T> wrapper);

    /**
     * 批量插入
     *
     * @param models 数据
     */
    void insertBatch(Collection<T> models);

    /**
     * 根据ID查询
     *
     * @param id id
     * @return T
     */
    T selectById(String id);

    /**
     * 根据id
     *
     * @param id id
     */
    void deleteById(String id);

    /**
     * 插入数据
     *
     * @param model 数据
     */
    void insert(T model);


    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param wrapper 过滤条件
     * @return PageResult
     */
    PageResult<T> selectPage(Page<T> page, QueryWrapper<T> wrapper);


    /**
     * 批量删除
     *
     * @param ids ids
     */
    void deleteByIds(Collection<String> ids);

    /**
     * 根据条件删除
     *
     * @param wrapper wrapper
     */
    void delete(UpdateWrapper<T> wrapper);

    /**
     * 更新数据
     *
     * @param model model
     */
    boolean updateById(T model);

    /**
     * 批量更新
     *
     * @param models models
     */
    void updateBatch(Collection<T> models);

    /**
     * 根据id批量查询
     *
     * @param ids ids
     */
    List<T> selectByIds(Collection<String> ids);
}
