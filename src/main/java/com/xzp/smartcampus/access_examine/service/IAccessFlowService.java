package com.xzp.smartcampus.access_examine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.access_examine.model.AccessFlowModel;
import com.xzp.smartcampus.access_examine.vo.*;
import com.xzp.smartcampus.access_examine.vo.FullExamineFlowInfo;
import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;

import java.util.List;

public interface IAccessFlowService extends IBaseService<AccessFlowModel> {

    PageResult searchTodoAccessFlow(AccessFlowSearchParam searchParam, Integer current, Integer pageSize);

    PageResult searchAlreadyAccessFlow(AccessFlowSearchParam searchParam, Integer current, Integer pageSize);

    PageResult searchMineAccessFlow(AccessFlowSearchParam searchParam, Integer current, Integer pageSize);

    AccessFlowModel createAccessFlow(AccessFlowModel accessFlowModel);

    AccessFlowModel examineAccessFlow(AccessFlowModel accessFlowModel);
}
