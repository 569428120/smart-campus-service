package com.xzp.smartcampus.access_strategy.service;

import com.xzp.smartcampus.access_strategy.model.AccessFlowModel;
import com.xzp.smartcampus.access_strategy.vo.AccessExamineVo;
import com.xzp.smartcampus.access_strategy.vo.ExamineFlowParam;
import com.xzp.smartcampus.access_strategy.vo.ExamineSearchParam;
import com.xzp.smartcampus.access_strategy.vo.ExamineSearchResult;
import com.xzp.smartcampus.common.service.IBaseService;

import java.util.List;

public interface IAccessFlowService extends IBaseService<AccessFlowModel> {
    void saveAccessFlow(AccessExamineVo examineVo);

    void deleteAccessFlow(String id);

    List<ExamineSearchResult> searchAccessExamine(ExamineSearchParam searchParam);

    void commitAccessFlow(String id);

    void examineAccessFlow(ExamineFlowParam param);
}
