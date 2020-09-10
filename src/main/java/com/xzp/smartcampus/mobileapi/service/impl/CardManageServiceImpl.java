package com.xzp.smartcampus.mobileapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.api.ykt.YktClient;
import com.xzp.smartcampus.common.enums.UserType;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.CardModel;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.*;
import com.xzp.smartcampus.human.vo.UserVo;
import com.xzp.smartcampus.mobileapi.param.CardPostParam;
import com.xzp.smartcampus.mobileapi.param.CardQueryParam;
import com.xzp.smartcampus.mobileapi.param.ExamineCardPostParam;
import com.xzp.smartcampus.mobileapi.service.ICardManageService;
import com.xzp.smartcampus.mobileapi.vo.CardVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CardManageServiceImpl implements ICardManageService {

    @Resource
    private IStudentService studentService;

    @Resource
    private IStaffUserService staffUserService;

    @Resource
    private IFeatureCardService cardService;

    @Resource
    private YktClient yktClient;

    /**
     * 查询卡的列表
     *
     * @param param    搜索参数
     * @param current  当前页
     * @param pageSize 也数量
     * @return 数据
     */
    @Override
    public Map<String, Object> getCardPage(CardQueryParam param, Integer current, Integer pageSize) {
        List<String> userIds = this.getUserIds(param);
        QueryWrapper<CardModel> wrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(userIds)) {
            wrapper.in("user_id", userIds);
        }
        wrapper.like(StringUtils.isNotBlank(param.getCardNumber()), "card_number", param.getCardNumber());
        wrapper.in("card_status", Arrays.asList(CardModel.STATUS_COMPLETE, CardModel.STATUS_CANCEL, CardModel.STATUS_DISCARD));
        PageResult<CardModel> pageResult = cardService.selectPage(new Page<CardModel>(current, pageSize), wrapper);
        Map<String, Object> map = new HashMap<>(8);
        map.put("total", pageResult.getTotal());
        map.put("totalPage", pageResult.getTotalPage());
        map.put("data", this.toCardVos(pageResult.getData()));
        this.setStatisticData(param, userIds, pageResult.getTotal(), map);
        return map;
    }

    /**
     * 设置统计数据
     *
     * @param param   参数
     * @param userIds 用户id
     * @param total   卡的数量
     * @param map     map
     */
    private void setStatisticData(CardQueryParam param, List<String> userIds, Long total, Map<String, Object> map) {
        map.put("userCount", this.getUserCount(userIds));
        map.put("cardConut", total);
        // TODO 设置假的数据
        map.put("openingConut", total);
        map.put("makeUpConut", 0);
        map.put("monthlyMUAvg", 0);
    }

    /**
     * 总人数
     *
     * @param userIds 用户id
     * @return 数量
     */
    private int getUserCount(List<String> userIds) {
        if (!CollectionUtils.isEmpty(userIds)) {
            return userIds.size();
        }
        List<StudentModel> studentModels = studentService.selectList(new QueryWrapper<>());
        List<StaffModel> staffModels = staffUserService.selectList(new QueryWrapper<>());
        int count = 0;
        count += CollectionUtils.isEmpty(studentModels) ? 0 : studentModels.size();
        count += CollectionUtils.isEmpty(staffModels) ? 0 : staffModels.size();
        return count;
    }

    /**
     * 获取userId
     *
     * @param param 参数
     * @return List<String>
     */
    private List<String> getUserIds(CardQueryParam param) {
        if (StringUtils.isBlank(param.getGroupId()) && StringUtils.isBlank(param.getUserName()) && StringUtils.isBlank(param.getUserNumber()) && StringUtils.isBlank(param.getUserType())) {
            return Collections.emptyList();
        }
        // 查询学生
        List<StudentModel> studentModels = studentService.selectList(new QueryWrapper<StudentModel>()
                .eq(StringUtils.isNotBlank(param.getUserType()) && !UserType.STUDENT.getKey().equals(param.getUserType()), "fid", "-1")
                .eq(StringUtils.isNotBlank(param.getGroupId()), "group_id", param.getGroupId())
                .like(StringUtils.isNotBlank(param.getUserName()), "name", param.getUserName())
                .like(StringUtils.isNotBlank(param.getUserNumber()), "student_code", param.getUserNumber())
        );
        // 查询职员
        List<StaffModel> staffModels = staffUserService.selectList(new QueryWrapper<StaffModel>()
                .eq(StringUtils.isNotBlank(param.getUserType()), "user_type", param.getUserType())
                .eq(StringUtils.isNotBlank(param.getGroupId()), "group_id", param.getGroupId())
                .like(StringUtils.isNotBlank(param.getUserName()), "name", param.getUserName())
                .like(StringUtils.isNotBlank(param.getUserNumber()), "user_job_code", param.getUserNumber())
        );

        List<String> userIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(studentModels)) {
            userIds.addAll(studentModels.stream().map(StudentModel::getId).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(staffModels)) {
            userIds.addAll(staffModels.stream().map(StaffModel::getId).collect(Collectors.toList()));
        }
        return userIds;
    }

    /**
     * 查询待审的列表
     *
     * @param param    搜索参数
     * @param current  当前页
     * @param pageSize 页数量
     * @return 数据
     */
    @Override
    public PageResult<CardVo> getExamineCardPage(CardQueryParam param, Integer current, Integer pageSize) {
        List<String> userIds = this.getUserIds(param);
        QueryWrapper<CardModel> wrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(userIds)) {
            wrapper.in("user_id", userIds);
        }
        wrapper.like(StringUtils.isNotBlank(param.getCardNumber()), "card_number", param.getCardNumber());
        wrapper.in("card_status", Arrays.asList(CardModel.STATUS_MAKE_UP, CardModel.STATUS_OPENING));
        PageResult<CardModel> pageResult = cardService.selectPage(new Page<CardModel>(current, pageSize), wrapper);
        return new PageResult<>(pageResult.getTotal(), pageResult.getTotalPage(), this.toCardVos(pageResult.getData()));
    }

    /**
     * 转换为vo对象
     *
     * @param data 数据
     * @return List<CardVo>
     */
    private List<CardVo> toCardVos(List<CardModel> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        List<String> userIds = data.stream().map(CardModel::getUserId).collect(Collectors.toList());
        List<UserVo> userVos = this.getUserVoListByIds(userIds);
        Map<String, UserVo> userIdToVoMap = userVos.stream().collect(Collectors.toMap(UserVo::getId, k -> k));
        return data.stream().map(item -> {
            CardVo cardVo = new CardVo();
            BeanUtils.copyProperties(item, cardVo);
            UserVo userVo = userIdToVoMap.get(item.getUserId());
            if (userVo != null) {
                cardVo.setUserName(userVo.getUserName());
                cardVo.setUserType(userVo.getUserType());
                cardVo.setUserNumber(userVo.getUserJobCode());
            }
            return cardVo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询用户列表
     *
     * @param userIds userIds
     * @return List<UserVo>
     */
    private List<UserVo> getUserVoListByIds(List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        List<StudentModel> studentModels = studentService.selectByIds(userIds);
        List<StaffModel> staffModels = staffUserService.selectByIds(userIds);
        // 转换为vo
        List<UserVo> userVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(studentModels)) {
            userVos.addAll(studentModels.stream().map(item -> {
                UserVo vo = new UserVo();
                BeanUtils.copyProperties(item, vo);
                vo.setUserType(UserType.STUDENT.getKey());
                vo.setUserJobCode(item.getStudentCode());
                vo.setUserName(item.getName());
                return vo;
            }).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(staffModels)) {
            userVos.addAll(staffModels.stream().map(item -> {
                UserVo vo = new UserVo();
                BeanUtils.copyProperties(item, vo);
                return vo;
            }).collect(Collectors.toList()));
        }
        return userVos;
    }

    /**
     * 保存卡数据
     *
     * @param param 参数
     */
    @Override
    public void saveCardData(CardPostParam param) {
        // 补办
        if (CardModel.SERVICE_TYPE_MAKE_UP.equals(param.getServiceType()) && StringUtils.isBlank(param.getOriginalCardNumber())) {
            log.warn("OriginalCardNumber is null");
            throw new SipException("补办业务 originalCardNumber 不能为空");
        }
        if (CardModel.SERVICE_TYPE_MAKE_UP.equals(param.getServiceType())) {
            yktClient.deleteCardById(param.getOriginalCardNumber());
            cardService.delete(new UpdateWrapper<CardModel>()
                    .eq("card_number", param.getOriginalCardNumber())
            );
        }
        // 新增
        if (StringUtils.isBlank(param.getId())) {
            this.createCardModel(param);
            return;
        }
        // 更新
        this.updateCardModel(param);
    }

    /**
     * 更新数据
     *
     * @param param 参数
     */
    private void updateCardModel(CardPostParam param) {
        if (StringUtils.isBlank(param.getId())) {
            log.warn("id is null");
            throw new SipException("参数错误，id 不能为空");
        }
        CardModel cardModel = cardService.selectById(param.getId());
        if (cardModel == null) {
            log.warn("not find cardModel by id {}", param.getId());
            throw new SipException("参数错误，数据不存在 id " + param.getId());
        }
        yktClient.updateCardnumber(cardModel.getCardNumber(), param.getCardNumber());
        cardModel.setCardNumber(param.getCardNumber());
        cardModel.setCardType(param.getCardType());
        cardService.updateById(cardModel);
    }

    /**
     * 新增数据
     *
     * @param param 参数
     */
    private void createCardModel(CardPostParam param) {
        CardModel cardModel = new CardModel();
        cardModel.setId(SqlUtil.getUUId());
        cardModel.setUserId(param.getUserId());
        cardModel.setCardType(param.getCardType());
        cardModel.setCardNumber(param.getCardNumber());
        cardModel.setCardStatus(CardModel.STATUS_COMPLETE);
        cardModel.setServiceType(param.getServiceType());
        cardService.insert(cardModel);
    }

    /**
     * 删除卡的类型
     *
     * @param cardIds id
     */
    @Override
    public void deleteCardByIds(List<String> cardIds) {
        if (CollectionUtils.isEmpty(cardIds)) {
            log.warn("cardIds is null");
            return;
        }
        List<CardModel> cardModels = cardService.selectByIds(cardIds);
        if (CollectionUtils.isEmpty(cardModels)) {
            return;
        }
        List<String> cardNumbers = cardModels.stream().map(CardModel::getCardNumber).collect(Collectors.toList());
        yktClient.deleteCardByIds(cardNumbers);
        cardService.deleteByIds(cardIds);
    }

    /**
     * 审核卡
     *
     * @param param 参数
     */
    @Override
    public void examineCard(@Valid ExamineCardPostParam param) {
        CardModel cardModel = cardService.selectById(param.getCardId());
        if (cardModel == null) {
            log.warn("not find cardModel by id {}", param.getCardId());
            throw new SipException("参数错误，找不到数据 cardId " + param.getCardId());
        }
        cardModel.setCardType(param.getCardType());
        cardModel.setCardNumber(param.getCardNumber());
        // 新增消费卡数据
        yktClient.addCard(param.getCardNumber());
        cardService.updateById(cardModel);
    }
}
