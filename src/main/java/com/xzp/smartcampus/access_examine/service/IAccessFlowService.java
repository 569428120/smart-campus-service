package com.xzp.smartcampus.access_examine.service;

import com.xzp.smartcampus.access_examine.model.AccessFlowModel;
import com.xzp.smartcampus.access_examine.vo.*;
import com.xzp.smartcampus.access_examine.vo.FullExamineFlowInfo;
import com.xzp.smartcampus.common.service.IBaseService;

import java.util.List;

public interface IAccessFlowService extends IBaseService<AccessFlowModel> {
    void saveAccessFlow(AccessExamineVo examineVo);

    void deleteAccessFlow(String id);

    List<ExamineSearchResult> searchAccessExamine(ExamineSearchParam searchParam);

    void commitAccessFlow(String id);

    void examineAccessFlow(ExamineFlowParam param);

    FullExamineFlowInfo selectExamineInfoById(String id);
}
