package com.xzp.smartcampus.access_examine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.access_examine.constconfig.FLowConst;
import com.xzp.smartcampus.access_examine.mapper.AccessFlowMapper;
import com.xzp.smartcampus.access_examine.model.AccessFlowModel;
import com.xzp.smartcampus.access_examine.model.AccessFlowPoolModel;
import com.xzp.smartcampus.access_examine.model.AccessFlowStepModel;
import com.xzp.smartcampus.access_examine.service.IAccessFlowPoolService;
import com.xzp.smartcampus.access_examine.service.IAccessFlowService;
import com.xzp.smartcampus.access_examine.service.IAccessFlowStepService;
import com.xzp.smartcampus.access_examine.vo.AccessExamineVo;
import com.xzp.smartcampus.access_examine.vo.ExamineFlowParam;
import com.xzp.smartcampus.access_examine.vo.ExamineSearchParam;
import com.xzp.smartcampus.access_examine.vo.ExamineSearchResult;
import com.xzp.smartcampus.access_examine.vo.FullExamineFlowInfo;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.model.BaseModel;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IStaffUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
@Transactional(rollbackFor = SipException.class)
public class AccessFlowService extends IsolationBaseService<AccessFlowMapper, AccessFlowModel>
        implements IAccessFlowService {

    // TODO 调用用户管理服务
    @Autowired
    IStaffUserService userService;
    @Autowired
    IAccessFlowStepService stepService;
    @Autowired
    IAccessFlowPoolService poolService;
    @Autowired
    AccessFlowSearchService searchService;

    public void saveAccessFlow(AccessExamineVo examineVo) {
        //使用BaseModel进行属性拷贝
        BaseModel base = new BaseModel();
        BeanUtils.copyProperties(examineVo, base);

        // 1.创建业务流程
        AccessFlowModel accessFlowModel = new AccessFlowModel();
        //拷贝BaseModel属性
        BeanUtils.copyProperties(base, accessFlowModel);
        accessFlowModel.setStartTime(examineVo.getStartTime());
        accessFlowModel.setEndTime(examineVo.getEndTime());
        accessFlowModel.setIsCar(examineVo.getIsCar());
        if (1 == examineVo.getIsCar()) {
            accessFlowModel.setCarNumber(accessFlowModel.getCarNumber());
        }

        // 2.创建流程主体
        AccessFlowPoolModel flowPoolModel = new AccessFlowPoolModel();
        //拷贝BaseModel属性
        BeanUtils.copyProperties(base, flowPoolModel);
        flowPoolModel.setFlowType(examineVo.getFlowType());
        // TODO 2.1 根据发起人id,查询发起人信息
        StaffModel originator = this.userService.selectById(examineVo.getOriginatorId());
        flowPoolModel.setOriginatorId(originator.getId());
        flowPoolModel.setOriginatorName(originator.getName());
        flowPoolModel.setOriginatorCode(originator.getUserJobCode());
        // TODO 2.2 根据申请人id,查询申请人信息
        StaffModel applicant = this.userService.selectById(examineVo.getApplicantId());
        flowPoolModel.setApplicantId(applicant.getId());
        flowPoolModel.setApplicantType(applicant.getUserType());
        flowPoolModel.setApplicantName(applicant.getName());
        flowPoolModel.setApplicantCode(applicant.getUserJobCode());

        // 3.创建流程步骤(暂时只包含两个步骤)
        // 3.1 步骤1>>>申请
        AccessFlowStepModel flowStepModel_1 = new AccessFlowStepModel();
        //拷贝BaseModel属性
        BeanUtils.copyProperties(base, flowStepModel_1);
        flowStepModel_1.setStepName(FLowConst.APPLY);     //第一步骤创建完成,默认名称为 申请
        flowStepModel_1.setOpinion(examineVo.getReason());
        flowStepModel_1.setHandleId(applicant.getId());
        flowStepModel_1.setHandleType(applicant.getUserType());
        flowStepModel_1.setHandleName(applicant.getName());
        flowStepModel_1.setHandleCode(applicant.getUserJobCode());
        // 申请步骤创建完成,设置申请状态为完成
        flowStepModel_1.setHandleStatus(FLowConst.PENDING);
        // TODO 3.2 步骤2>>>审批
        StaffModel examine = this.userService.selectById(examineVo.getExamineId());
        AccessFlowStepModel flowStepModel_2 = new AccessFlowStepModel();
        //拷贝BaseModel属性
        BeanUtils.copyProperties(base, flowStepModel_2);
        flowStepModel_2.setStepName(FLowConst.EXAMINE);
        flowStepModel_2.setHandleId(examine.getId());
        flowStepModel_2.setHandleType(examine.getUserType());
        flowStepModel_2.setHandleName(examine.getName());
        flowStepModel_2.setHandleCode(examine.getUserJobCode());
        // TODO 保存操作不更新步骤状态
        // 审核步骤创建完成,设置审核状态为待审核
//        flowStepModel_2.setHandleStatus(AccessConst.PENDING);

        // 4.流程步骤入库
        this.stepService.insertBatch(Arrays.asList(flowStepModel_1, flowStepModel_2));
        // 5.流程主体入库
        String steps = flowStepModel_1.getId() + "##" + flowStepModel_2.getId();
        flowPoolModel.setSteps(steps);
        // TODO 保存操作不指定当前步骤
//        flowPoolModel.setCurrStep(flowStepModel_2.getId());
        // TODO 保存操作不指定当前流程整体状态
        // 流程步骤创建完成,设置流程整体状态为待审批
//        flowPoolModel.setExamineStatus(AccessConst.PENDING);
        this.poolService.insert(flowPoolModel);
        // 6.业务入库
        accessFlowModel.setFlowId(flowPoolModel.getId());
        this.insert(accessFlowModel);
    }

    public void deleteAccessFlow(String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("Access flow id is null!");
            throw new SipException("Access flow id is null!");
        }
        // 1.查找对应的业务信息(获取流程id)
        AccessFlowModel flowModel = this.selectById(id);
        if (null == flowModel) {
            log.warn(String.format("Access flow id:%s doesn't exist", id));
            return;
        }
        // 2.查找业务对应的流程>>>删除
        AccessFlowPoolModel flowPoolModel = this.poolService.selectById(flowModel.getFlowId());
        this.poolService.deleteById(flowPoolModel.getId());
        // 3.删除流程对应的步骤
        String steps = flowPoolModel.getSteps();
        List<String> step_ids = Arrays.asList(steps.split("##"));
        this.stepService.deleteByIds(step_ids);
        // 4. 删除业务
        this.deleteById(id);
    }

    public List<ExamineSearchResult> searchAccessExamine(ExamineSearchParam searchParam) {
        // 1.查询FlowPool主表
        // 风险:对于申请人,申请人编码,状态都为空的条件查询,需要整表查询
        QueryWrapper<AccessFlowPoolModel> poolWrapper = new QueryWrapper<>();
        poolWrapper.like(StringUtils.isNotBlank(searchParam.getApplicantName()), "applicant_name", searchParam.getApplicantName())
                .like(StringUtils.isNotBlank(searchParam.getApplicantCode()), "applicant_code", searchParam.getApplicantCode());
        // 查询时过滤申请中的电子流
        if (StringUtils.isBlank(searchParam.getApplicantCode())) {
            poolWrapper.ne("examine_status", FLowConst.APPLYING);
        } else {
            poolWrapper.eq("examine_status", searchParam.getHandleStatus());
        }
        List<AccessFlowPoolModel> poolModels = this.poolService.selectList(poolWrapper);
        List<String> step1Ids = new ArrayList<>();    //步骤1 id列表
        List<String> step2Ids = new ArrayList<>();    //步骤2 id列表
        List<String> flowIds = new ArrayList<>();     //flow-pool id列表
        Map<String, AccessFlowPoolModel> stepId2PoolMaps = new HashMap<>();
        if (CollectionUtils.isEmpty(poolModels)) {
            return null;
        } else {
            poolModels.forEach(poolModel -> {
                String[] stepIds = poolModel.getSteps().split("##");
                stepId2PoolMaps.put(stepIds[1], poolModel);
                step2Ids.add(stepIds[1]);
            });
        }

        // 2.查询步骤表
        List<AccessFlowStepModel> step2Models = this.stepService.selectList(new QueryWrapper<AccessFlowStepModel>()
                .like(StringUtils.isNotBlank(searchParam.getHandleName()), "handle_name", searchParam.getHandleName())
                .like(StringUtils.isNotBlank(searchParam.getHandleTime()), "update_time", searchParam.getHandleTime())
                .in("id", step2Ids)
        );
        // 3.根据步骤表信息反过来过滤flow-pool主表
        List<AccessFlowPoolModel> destPoolModels = new ArrayList<>();     //新建dest**Model,过滤后,使flow-pool和step顺序对应
        List<AccessFlowStepModel> destStep2Models = new ArrayList<>();
        step2Models.forEach(step2Model -> {
            Set<String> step2IdSet = stepId2PoolMaps.keySet();
            if (step2IdSet.contains(step2Model.getId())) {
                AccessFlowPoolModel destPoolModel = stepId2PoolMaps.get(step2Model.getId());
                destPoolModels.add(destPoolModel);
                step1Ids.add(destPoolModel.getSteps().split("##")[0]);
                flowIds.add(destPoolModel.getId());
                destStep2Models.add(step2Model);
            }
        });
        // 4.查询业务主表和step1步骤1表
        List<AccessFlowStepModel> destStep1Models = this.stepService.selectByIds(step1Ids);
        List<AccessFlowModel> destFlowModels = this.selectList(new QueryWrapper<AccessFlowModel>()
                .in("flow_id", flowIds));
        // 5. 组装result信息
        return this.searchService.generateSearchResultsByModels(destFlowModels, destPoolModels, destStep1Models, destStep2Models);
    }

    /**
     * 出入电子流提交操作
     * 提交时更新申请状态
     *
     * @param id 流程业务id
     */
    public void commitAccessFlow(String id) {
        AccessFlowModel flowModel = this.selectById(id);
        if (null != flowModel) {
            AccessFlowPoolModel poolModel = this.poolService.selectById(flowModel.getFlowId());
            String[] steps = poolModel.getSteps().split("##");
            AccessFlowStepModel step1Model = this.stepService.selectById(steps[0]);
            AccessFlowStepModel step2Model = this.stepService.selectById(steps[1]);
            // 更新步骤状态,flow-pool状态
            step1Model.setHandleStatus(FLowConst.FINISH);
            step2Model.setHandleStatus(FLowConst.PENDING);
            this.stepService.updateBatch(Arrays.asList(step1Model, step2Model));

            poolModel.setExamineStatus(FLowConst.PROCESSING);
            this.poolService.updateById(poolModel);
        }

    }

    public void examineAccessFlow(ExamineFlowParam param) {
        String serviceId=param.getServiceId();
        String flowId=param.getFlowId();
        String stepId=param.getStepId();

        if (StringUtils.isEmpty(serviceId) || StringUtils.isEmpty(flowId) || StringUtils.isEmpty(stepId)) {
            log.error(String.format("Service id:%s or flow id:%s or flow-step id:%s is empty!", serviceId,flowId, stepId));
            throw new SipException(String.format("Service id:%s or flow id:%s or flow-step id:%s is empty!", serviceId,flowId, stepId));
        }
        // 1.更新步骤表本身状态
        AccessFlowStepModel stepModel = new AccessFlowStepModel();
        stepModel.setId(stepId);
        stepModel.setHandleStatus(param.getStatus());
        stepModel.setOpinion(param.getOpinion());

        // 2.更新流程主表状态
        AccessFlowPoolModel poolModel = new AccessFlowPoolModel();
        poolModel.setId(flowId);
        switch (param.getStatus()) {
            case FLowConst.CANCEL:
                poolModel.setExamineStatus(FLowConst.CANCEL); // 取消(打回)
                break;
            case FLowConst.FINISH:
                poolModel.setExamineStatus(FLowConst.FINISH); // 通过
                break;
            default:
                poolModel.setExamineStatus(param.getStatus());
        }

        // 3.更新业务表时间(更新时间)
        AccessFlowModel flowModel=new AccessFlowModel();
        flowModel.setId(serviceId);
        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        flowModel.setUpdateTime(sqlDate);

        this.stepService.updateById(stepModel);
        this.poolService.updateById(poolModel);
        this.updateById(flowModel);
    }

    public FullExamineFlowInfo selectExamineInfoById(String id){
        if (StringUtils.isEmpty(id)) {
            log.error("Examine service id is null!");
            throw new SipException("Examine service id is null!");
        }
        // 1.根据业务id查询对应的业务信息
        FullExamineFlowInfo fullExamineInfo;
        AccessFlowModel flowModel=this.selectById(id);
        if(null==flowModel){
            return null;
        }else {
            fullExamineInfo=new FullExamineFlowInfo();
            BeanUtils.copyProperties(flowModel,fullExamineInfo);
            // 2.查询当前业务对应的流程对象
            AccessFlowPoolModel poolModel=this.poolService.selectById(fullExamineInfo.getFlowId());
            // 3.查询流程对应的审批步骤信息
            fullExamineInfo.setPoolModel(poolModel);
            if(null!=poolModel){
                List<String> stepIds=Arrays.asList(poolModel.getSteps().split("##"));
                List<AccessFlowStepModel> stepModels=this.stepService.selectByIds(stepIds);
                fullExamineInfo.setSteps(stepModels);
            }
        }

        return fullExamineInfo;
    }











}
