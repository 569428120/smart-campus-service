package com.xzp.smartcampus.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.common.vo.PageResult;

import java.util.List;

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
    void insertBatch(List<T> models);

    /**
     * 根据ID查询
     *
     * @param id id
     * @return T
     */
    T selectById(Long id);

    /**
     * 根据id
     *
     * @param id id
     */
    void deleteById(Long id);

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
    void deleteByIds(List<String> ids);

    /**
     * 根据条件删除
     *
     * @param wrapper wrapper
     */
    void delete(UpdateWrapper<T> wrapper);
}
