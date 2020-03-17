package com.xzp.smartcampus.system.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.model.AuthorityGroupModel;

/**
 * 权限组
 */
public interface IAuthorityGroupService extends IBaseService<AuthorityGroupModel> {

    /**
     * 分页查询
     *
     * @param searchValue 查询条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return PageResult
     */
    PageResult getAuthorityGroupPage(AuthorityGroupModel searchValue, Integer current, Integer pageSize);
}
