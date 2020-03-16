package com.xzp.smartcampus.access_strategy.service.impl;

import com.xzp.smartcampus.access_strategy.constconfig.AccessConst;
import com.xzp.smartcampus.access_strategy.mapper.AccessFlowMapper;
import com.xzp.smartcampus.access_strategy.model.AccessFlowModel;
import com.xzp.smartcampus.access_strategy.model.AccessFlowPoolModel;
import com.xzp.smartcampus.access_strategy.model.AccessFlowStepModel;
import com.xzp.smartcampus.access_strategy.service.IAccessFlowPoolService;
import com.xzp.smartcampus.access_strategy.service.IAccessFlowService;
import com.xzp.smartcampus.access_strategy.service.IAccessFlowStepService;
import com.xzp.smartcampus.access_strategy.vo.AccessExamineVo;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = SipException.class)
public class AccessFlowService extends IsolationBaseService<AccessFlowMapper, AccessFlowModel>
        implements IAccessFlowService {

    // TODO 调用用户管理服务
    @Autowired
    IUserService userService;
    @Autowired
    IAccessFlowStepService stepService;
    @Autowired
    IAccessFlowPoolService poolService;

    public void createAccessFlow(AccessExamineVo examineVo){
        //使用BaseModel进行属性拷贝
        BaseModel base=new BaseModel();
        BeanUtils.copyProperties(examineVo,base);

        // 1.创建业务流程
        AccessFlowModel accessFlowModel=new AccessFlowModel();
        //拷贝BaseModel属性
        BeanUtils.copyProperties(base,accessFlowModel);
        accessFlowModel.setStartTime(examineVo.getStartTime());
        accessFlowModel.setEndTime(examineVo.getEndTime());
        accessFlowModel.setIsCar(examineVo.getIsCar());
        if(1==examineVo.getIsCar()){
            accessFlowModel.setCarNumber(accessFlowModel.getCarNumber());
        }

        // 2.创建流程主体
        AccessFlowPoolModel flowPoolModel=new AccessFlowPoolModel();
        //拷贝BaseModel属性
        BeanUtils.copyProperties(base,flowPoolModel);
        flowPoolModel.setFlowType(examineVo.getFlowType());
        // TODO 2.1 根据发起人id,查询发起人信息
        SmartUser originator=this.userService.selectById(examineVo.getOriginatorId());
        flowPoolModel.setOriginatorId(originator.getId());
        flowPoolModel.setOriginatorName(originator.getName());
        flowPoolModel.setOriginatorCode(originator.getCode());
        // TODO 2.2 根据申请人id,查询申请人信息
        SmartUser applicant=this.userService.selectById(examineVo.getApplicantId());
        flowPoolModel.setApplicantId(applicant.getId());
        flowPoolModel.setApplicantType(applicant.getType());
        flowPoolModel.setApplicantName(applicant.getName());
        flowPoolModel.setApplicantCode(applicant.getCode());

        // 3.创建流程步骤(暂时只包含两个步骤)
        // 3.1 步骤1>>>申请
        AccessFlowStepModel flowStepModel_1=new AccessFlowStepModel();
        //拷贝BaseModel属性
        BeanUtils.copyProperties(base,flowStepModel_1);
        flowStepModel_1.setStepName(AccessConst.APPLY);
        flowStepModel_1.setOpinion(examineVo.getReason());
        flowStepModel_1.setHandleId(applicant.getId());
        flowStepModel_1.setHandleType(applicant.getType());
        flowStepModel_1.setHandleName(applicant.getName());
        flowStepModel_1.setHandleCode(applicant.getCode());
        // 暂时设置申请状态为完成
        flowStepModel_1.setHandleStatus(AccessConst.FINISH);
        // TODO 3.2 步骤2>>>审批
        SmartUser examine=this.userService.selectById(examineVo.getExamineId());
        AccessFlowStepModel flowStepModel_2=new AccessFlowStepModel();
        //拷贝BaseModel属性
        BeanUtils.copyProperties(base,flowStepModel_2);
        flowStepModel_2.setStepName(AccessConst.EXAMINE);
        flowStepModel_2.setHandleId(examine.getId());
        flowStepModel_2.setHandleType(examine.getType());
        flowStepModel_2.setHandleName(examine.getName());
        flowStepModel_2.setHandleCode(examine.getCode());
            // 设置审核状态为待审核
        flowStepModel_2.setHandleStatus(AccessConst.PENDING);

        // 4.流程步骤入库
        this.stepService.insertBatch(Arrays.asList(flowStepModel_1,flowStepModel_2));
        // 5.流程主体入库
        String steps=flowStepModel_1.getId()+"##"+flowStepModel_2.getId();
        flowPoolModel.setSteps(steps);
        flowPoolModel.setCurrStep(flowStepModel_2.getId());
            // 设置流程整体状态为处理中
        flowPoolModel.setExamineStatus(AccessConst.PROCESSING);
        this.poolService.insert(flowPoolModel);
        // 6.业务入库
        accessFlowModel.setFlowId(flowPoolModel.getId());
        this.insert(accessFlowModel);
    }

    public void deleteAccessFlow(String id){
        if(StringUtils.isEmpty(id)){
            log.error("Access flow id is null!");
            throw new SipException("Access flow id is null!");
        }
        // 1.查找对应的业务信息(获取流程id)
        AccessFlowModel flowModel=this.selectById(id);
        if (null==flowModel){
            log.warn(String.format("Access flow id:%s doesn't exist",id));
            return;
        }
        // 2.查找业务对应的流程>>>删除
        AccessFlowPoolModel flowPoolModel=this.poolService.selectById(flowModel.getFlowId());
        this.poolService.deleteById(flowPoolModel.getId());
        // 3.删除流程对应的步骤
        String steps=flowPoolModel.getSteps();
        List<String> step_ids=Arrays.asList(steps.split("##"));
        this.stepService.deleteByIds(step_ids);
        // 4. 删除业务
        this.deleteById(id);
    }























}
