package com.xzp.smartcampus.mobileapi.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.mobileapi.model.LSRecordModel;
import com.xzp.smartcampus.mobileapi.vo.LSRecordSearchParam;

import java.util.List;

public interface ILeaveSchoolService extends IBaseService<LSRecordModel> {
    PageResult selectLsRecord(LSRecordSearchParam param,Integer current,Integer pageSize);

    PageResult selectLsApprovalRecord(LSRecordSearchParam param,Integer current,Integer pageSize);

    List selectLsApprovalClasses();

    List selectLsExamineUsers();

    void saveLsRecord(LSRecordModel recordModel);

    void confirmLsRecord(String id);

    void denyLsRecord(String id);

}
