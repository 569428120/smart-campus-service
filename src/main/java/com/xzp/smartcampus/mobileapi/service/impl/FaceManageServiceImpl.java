package com.xzp.smartcampus.mobileapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.enums.UserType;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.FaceModel;
import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.service.*;
import com.xzp.smartcampus.human.vo.FeatureFaceVo;
import com.xzp.smartcampus.human.vo.IFeatureVo;
import com.xzp.smartcampus.mobileapi.param.FacePostParam;
import com.xzp.smartcampus.mobileapi.param.FaceQueryParam;
import com.xzp.smartcampus.mobileapi.service.IFaceManageService;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class FaceManageServiceImpl implements IFaceManageService {

    @Resource
    private IFeatureFaceService faceService;

    @Resource
    private IStudentContactService contactService;

    /**
     * 根据用户id获取脸部图片
     *
     * @param userId userId
     * @return FaceVo
     */
    @Override
    public IFeatureVo getFaceVoByUserId(@NotNull(message = "userId 不能空") String userId) {
        List<FaceModel> faceModels = faceService.selectList(new QueryWrapper<FaceModel>()
                .eq("user_id", userId)
        );
        List<IFeatureVo> featureVos = faceService.toFaceVoList(faceModels);
        if (CollectionUtils.isEmpty(featureVos)) {
            return null;
        }
        return featureVos.get(0);
    }

    /**
     * 获取当前登录用户的脸部信息，学生和老师查询自己的   家长查询学生的
     *
     * @return List<FaceVo>
     */
    @Override
    public List<IFeatureVo> getMyFaceList() {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        List<String> userIds = this.getUserIds(userInfo);
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        List<FaceModel> faceModels = faceService.selectList(new QueryWrapper<FaceModel>()
                .in("user_id", userIds)
        );
        if (CollectionUtils.isEmpty(faceModels)) {
            return Collections.emptyList();
        }
        return faceService.toFaceVoList(faceModels);
    }

    /**
     * 获取用户id
     *
     * @param userInfo userInfo
     * @return List<String>
     */
    private List<String> getUserIds(LoginUserInfo userInfo) {
        if (UserType.PARENT.getKey().equals(userInfo.getUserType())) {
            List<StudentContactModel> contactModels = contactService.selectList(new QueryWrapper<StudentContactModel>()
                    .eq("contact", userInfo.getContact())
            );
            if (CollectionUtils.isEmpty(contactModels)) {
                return Collections.emptyList();
            }
            return contactModels.stream().map(StudentContactModel::getStudentId).collect(Collectors.toList());
        }
        return Collections.singletonList(userInfo.getUserId());
    }

    /**
     * 分页查询脸部数据
     *
     * @param queryParam 查询条件
     * @return PageResult<FaceVo>
     */
    @Override
    public PageResult<IFeatureVo> getFaceVoPage(@Valid FaceQueryParam queryParam, Integer current, Integer pageSize) {
        FeatureFaceVo searchValue = new FeatureFaceVo();
        searchValue.setName(queryParam.getUserName());
        searchValue.setGroupId(queryParam.getGroupId());
        searchValue.setUserCode(queryParam.getUserNumber());
        searchValue.setUserType(queryParam.getUserType());
        return faceService.getFeatureFaceVoList(searchValue, current, pageSize);
    }

    /**
     * 保存数据
     *
     * @param postParam 参数
     */
    @Override
    public void saveFaceData(@Valid FacePostParam postParam) {
        if (CollectionUtils.isEmpty(postParam.getImageIds())) {
            log.warn("image is null");
            throw new SipException("参数错误，imageIds 不能为空");
        }
        FaceModel faceModel = new FaceModel();
        faceModel.setId(postParam.getId());
        faceModel.setUserId(postParam.getUserId());
        faceModel.setImageIds(StringUtils.join(postParam.getImageIds(), Constant.PARAMS_ID_SEPARATOR));
        faceService.saveFaceModel(faceModel);
    }

    /**
     * 删除数据
     *
     * @param faceIds faceIds
     */
    @Override
    public void deleteFaceByIds(List<String> faceIds) {
        if (CollectionUtils.isEmpty(faceIds)) {
            log.warn("faceIds is null");
            return;
        }
        faceService.deleteFaceByIds(faceIds);
    }
}
