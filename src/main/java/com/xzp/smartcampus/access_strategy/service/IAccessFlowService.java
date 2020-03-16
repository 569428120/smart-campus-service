package com.xzp.smartcampus.access_strategy.service;

import com.xzp.smartcampus.access_strategy.model.AccessFlowModel;
import com.xzp.smartcampus.access_strategy.vo.AccessExamineVo;
import com.xzp.smartcampus.common.service.IBaseService;

public interface IAccessFlowService extends IBaseService<AccessFlowModel> {
    void createAccessFlow(AccessExamineVo examineVo);

    void deleteAccessFlow(String id);
}
