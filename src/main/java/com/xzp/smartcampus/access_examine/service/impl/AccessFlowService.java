package com.xzp.smartcampus.access_examine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.access_examine.constconfig.FLowConst;
import com.xzp.smartcampus.access_examine.mapper.AccessFlowMapper;
import com.xzp.smartcampus.access_examine.model.AccessFlowModel;
import com.xzp.smartcampus.access_examine.service.IAccessFlowService;
import com.xzp.smartcampus.access_examine.vo.AccessFlowSearchParam;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service
@Slf4j
@Transactional(rollbackFor = SipException.class)
public class AccessFlowService extends IsolationBaseService<AccessFlowMapper, AccessFlowModel>
        implements IAccessFlowService {


    public PageResult<AccessFlowModel> searchTodoAccessFlow(AccessFlowSearchParam searchParam,
                                                            Integer current, Integer pageSize) {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        return this.selectPage(new Page<>(current, pageSize),
                new QueryWrapper<AccessFlowModel>().eq("examine_id", userInfo.getUserId())
                        .eq("examine_status", FLowConst.PENDING)
                        .like(StringUtils.isNotBlank(searchParam.getFlowName()), "flow_name", searchParam.getFlowName())
                        .lt(StringUtils.isNotBlank(searchParam.getEndTime()), "end_time", searchParam.getEndTime())
                        .gt(StringUtils.isNotBlank(searchParam.getStartTime()), "start_time", searchParam.getStartTime())
        );
    }

    public PageResult<AccessFlowModel> searchAlreadyAccessFlow(AccessFlowSearchParam searchParam,
                                                               Integer current, Integer pageSize) {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        return this.selectPage(new Page<>(current, pageSize),
                new QueryWrapper<AccessFlowModel>().eq("examine_id", userInfo.getUserId())
                        .eq("examine_status", FLowConst.FINISH)
                        .like(StringUtils.isNotBlank(searchParam.getFlowName()), "flow_name", searchParam.getFlowName())
                        .lt(StringUtils.isNotBlank(searchParam.getEndTime()), "end_time", searchParam.getEndTime())
                        .gt(StringUtils.isNotBlank(searchParam.getStartTime()), "start_time", searchParam.getStartTime())
        );
    }

    public PageResult<AccessFlowModel> searchMineAccessFlow(AccessFlowSearchParam searchParam,
                                                            Integer current, Integer pageSize) {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        return this.selectPage(new Page<>(current, pageSize),
                new QueryWrapper<AccessFlowModel>().eq("originator_id", userInfo.getUserId())
                        .like(StringUtils.isNotBlank(searchParam.getFlowName()), "flow_name", searchParam.getFlowName())
                        .lt(StringUtils.isNotBlank(searchParam.getEndTime()), "end_time", searchParam.getEndTime())
                        .gt(StringUtils.isNotBlank(searchParam.getStartTime()), "start_time", searchParam.getStartTime())
        );
    }

    public AccessFlowModel createAccessFlow(AccessFlowModel accessFlowModel){
        accessFlowModel.setId(SqlUtil.getUUId());
        accessFlowModel.setExamineStatus(FLowConst.PENDING);
        if(StringUtils.isNotEmpty(accessFlowModel.getCarNumber())){
            accessFlowModel.setIsCar(1);
        }else {
            accessFlowModel.setIsCar(0);
        }
        this.insert(accessFlowModel);

        return accessFlowModel;
    }

    public AccessFlowModel examineAccessFlow(AccessFlowModel accessFlowModel){
        AccessFlowModel localModel=this.selectById(accessFlowModel.getId());
        if(localModel==null){
            log.error("LocalModel is null by id {}", accessFlowModel.getId());
            throw new SipException("数据错误，找不到AccessFlowModel id " + accessFlowModel.getId());
        }
        localModel.setExamineStatus(accessFlowModel.getExamineStatus());
        localModel.setExamineDesc(accessFlowModel.getExamineDesc());
        this.updateById(localModel);

        return localModel;
    }

}
