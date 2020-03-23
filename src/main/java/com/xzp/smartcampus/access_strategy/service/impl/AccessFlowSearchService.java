package com.xzp.smartcampus.access_strategy.service.impl;

import com.xzp.smartcampus.access_strategy.model.AccessFlowModel;
import com.xzp.smartcampus.access_strategy.model.AccessFlowPoolModel;
import com.xzp.smartcampus.access_strategy.model.AccessFlowStepModel;
import com.xzp.smartcampus.access_strategy.vo.ExamineSearchResult;
import com.xzp.smartcampus.system.service.IRegionService;
import com.xzp.smartcampus.system.service.ISchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccessFlowSearchService {
    @Autowired
    IRegionService regionService;
    @Autowired
    ISchoolService schoolService;

    public List<ExamineSearchResult> generateSearchResultsByModels(
            List<AccessFlowModel> flowModels, List<AccessFlowPoolModel> poolModels,
            List<AccessFlowStepModel> step1Models,List<AccessFlowStepModel> step2Models){
        List<ExamineSearchResult> searchResults=new ArrayList<>();

        String regionId=poolModels.get(0).getRegionId();
        String schoolId=poolModels.get(0).getSchoolId();
        String region=this.regionService.selectById(regionId).getRegionName();
        String school=this.schoolService.selectById(schoolId).getSchoolName();
        for(int i=0;i<poolModels.size()-1;i++){
            ExamineSearchResult searchResult=new  ExamineSearchResult();
            searchResult.setRegionId(regionId);
            searchResult.setRegion(region);
            searchResult.setSchoolId(schoolId);
            searchResult.setSchool(school);

            AccessFlowPoolModel poolModel=poolModels.get(i);
            searchResult.setFlowId(poolModel.getId());
            searchResult.setApplicantId(poolModel.getApplicantId());
            searchResult.setApplicantName(poolModel.getApplicantName());
            searchResult.setApplicantType(poolModel.getApplicantType());
            searchResult.setApplicantCode(poolModel.getApplicantCode());
            searchResult.setStatus(poolModel.getExamineStatus());
            searchResult.setCurrentStep(poolModel.getCurrStep());

            AccessFlowStepModel step2Model=step2Models.get(i);
            searchResult.setHandleId(step2Model.getHandleId());
            searchResult.setHandleName(step2Model.getHandleName());
            searchResult.setHandleTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(poolModel.getUpdateTime()));

            AccessFlowStepModel step1Model=step1Models.get(i);
            searchResult.setReason(step1Model.getOpinion());    //原因根据申请步骤中的审核内容确定

            AccessFlowModel flowModel=flowModels.get(i);
            searchResult.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(flowModel.getCreateTime()));
            searchResult.setStartTime(flowModel.getStartTime());
            searchResult.setEndTime(flowModel.getEndTime());

            searchResults.add(searchResult);
        }

        return searchResults;
    }








}
