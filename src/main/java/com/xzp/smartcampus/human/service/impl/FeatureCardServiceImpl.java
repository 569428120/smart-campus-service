package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.enums.UserType;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.CardMapper;
import com.xzp.smartcampus.human.model.CardModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.IFeatureCardService;
import com.xzp.smartcampus.human.service.IStaffUserService;
import com.xzp.smartcampus.human.service.IStudentService;
import com.xzp.smartcampus.human.vo.FeatureCardVo;
import com.xzp.smartcampus.human.vo.StudentVo;
import com.xzp.smartcampus.human.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class FeatureCardServiceImpl extends IsolationBaseService<CardMapper, CardModel> implements IFeatureCardService {

    @Resource
    private IStaffUserService userService;

    @Resource
    private IStudentService studentService;

    /**
     * 分页查询
     *
     * @param searchValue searchValue
     * @param current     当前页
     * @param pageSize    也数量
     * @return List<FeatureCardVo>
     */
    @Override
    public PageResult<FeatureCardVo> getFeatureCardVoList(FeatureCardVo searchValue, Integer current, Integer pageSize) {
        List<String> userIds = this.getUserIds(searchValue);
        PageResult<CardModel> pageResult = this.getCardModelPage(searchValue, userIds, current, pageSize);
        return new PageResult<>(pageResult.getTotal(), pageResult.getTotalPage(), this.toFeatureCardVoList(pageResult.getData()));
    }

    /**
     * 转换为vo
     *
     * @param data data
     * @return List<FeatureCardVo>
     */
    private List<FeatureCardVo> toFeatureCardVoList(List<CardModel> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        List<String> userIds = data.stream().map(CardModel::getUserId).collect(Collectors.toList());
        Map<String, UserVo> userIdToUserVoMap = userService.getUserVoListByIds(userIds).stream().collect(Collectors.toMap(UserVo::getId, v -> v));
        Map<String, StudentVo> studentIdToStudentVoMap = studentService.getStudentVoListByIds(userIds).stream().collect(Collectors.toMap(StudentVo::getId, v -> v));

        return data.stream().map(item -> {
            FeatureCardVo cardVo = new FeatureCardVo();
            BeanUtils.copyProperties(item, cardVo);
            cardVo.setGroupName("缺失");
            cardVo.setName("缺失");
            cardVo.setUserCode("缺失");
            if (userIdToUserVoMap.containsKey(item.getUserId())) {
                UserVo userVo = userIdToUserVoMap.get(item.getUserId());
                cardVo.setGroupName(userVo.getGroupName());
                cardVo.setUserCode(userVo.getUserIdentity());
                cardVo.setName(userVo.getName());
                cardVo.setUserType(userVo.getUserType());
            }
            if (studentIdToStudentVoMap.containsKey(item.getUserId())) {
                StudentVo studentVo = studentIdToStudentVoMap.get(item.getUserId());
                cardVo.setGroupName(studentVo.getGroupName());
                cardVo.setUserCode(studentVo.getStudentCode());
                cardVo.setName(studentVo.getName());
                cardVo.setUserType(UserType.STUDENT.getKey());
            }
            return cardVo;
        }).collect(Collectors.toList());
    }

    /**
     * 分页查询model
     *
     * @param searchValue searchValue
     * @param userIds     userIds
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult
     */
    private PageResult<CardModel> getCardModelPage(FeatureCardVo searchValue, List<String> userIds, Integer current, Integer pageSize) {
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<CardModel>()
                .in(!CollectionUtils.isEmpty(userIds), "user_id", userIds)
                .like(StringUtils.isNotBlank(searchValue.getCardNumber()), "card_number", searchValue.getCardNumber())
        );
    }

    /**
     * 过滤名称和身份证号(学号) TODO 该处查询速度较慢需要优化
     *
     * @param searchValue searchValue
     * @return List<String>
     */
    private List<String> getUserIds(FeatureCardVo searchValue) {
        if (StringUtils.isBlank(searchValue.getUserCode()) && StringUtils.isBlank(searchValue.getName())) {
            return Collections.emptyList();
        }
        List<String> userIds = new ArrayList<>();
        userIds.add("-1");
        // 用户
        List<StaffModel> staffModels = userService.selectList(new QueryWrapper<StaffModel>()
                .like(StringUtils.isNotBlank(searchValue.getName()), "name", searchValue.getName())
                .like(StringUtils.isNotBlank(searchValue.getUserCode()), "user_identity", searchValue.getUserCode())
        );
        if (!CollectionUtils.isEmpty(staffModels)) {
            userIds.addAll(staffModels.stream().map(StaffModel::getId).collect(Collectors.toList()));
        }
        // 学生
        List<StudentModel> studentModels = studentService.selectList(new QueryWrapper<StudentModel>()
                .like(StringUtils.isNotBlank(searchValue.getName()), "name", searchValue.getName())
                .like(StringUtils.isNotBlank(searchValue.getUserCode()), "student_code", searchValue.getUserCode())
        );
        if (!CollectionUtils.isEmpty(studentModels)) {
            userIds.addAll(studentModels.stream().map(StudentModel::getId).collect(Collectors.toList()));
        }
        return userIds;
    }

    /**
     * 保存卡信息
     *
     * @param cardModel cardModel
     * @return CardModel
     */
    @Override
    public CardModel saveCardModel(CardModel cardModel) {
        if (StringUtils.isBlank(cardModel.getId())) {
            return this.createCardModel(cardModel);
        }
        return this.updateCardModel(cardModel);
    }

    /**
     * 更新
     *
     * @param cardModel cardModel
     * @return CardModel
     */
    private CardModel updateCardModel(CardModel cardModel) {
        if (StringUtils.isBlank(cardModel.getId())) {
            log.warn("user id is null");
            throw new SipException("参数错误，id为空");
        }
        CardModel localModel = this.selectById(cardModel.getId());
        localModel.setCardNumber(cardModel.getCardNumber());
        localModel.setCardType(cardModel.getCardType());
        localModel.setDescription(cardModel.getDescription());
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 创建
     *
     * @param cardModel cardModel
     * @return CardModel
     */
    private CardModel createCardModel(CardModel cardModel) {
        if (StringUtils.isBlank(cardModel.getUserId())) {
            log.warn("user id is null");
            throw new SipException("参数错误，用户id不能为空");
        }
        cardModel.setId(SqlUtil.getUUId());
        this.insert(cardModel);
        return cardModel;
    }


    /**
     * 删除卡信息
     *
     * @param cardIds cardIds
     */
    @Override
    public void deleteCardByIds(List<String> cardIds) {
        if (CollectionUtils.isEmpty(cardIds)) {
            log.warn("cardIds is null");
            return;
        }
        this.deleteByIds(cardIds);
    }
}
