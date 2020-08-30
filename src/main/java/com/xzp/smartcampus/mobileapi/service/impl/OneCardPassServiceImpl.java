package com.xzp.smartcampus.mobileapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xzp.smartcampus.common.api.ykt.YktClient;
import com.xzp.smartcampus.common.api.ykt.param.YktRecordQueryParam;
import com.xzp.smartcampus.common.api.ykt.vo.TktRecordVo;
import com.xzp.smartcampus.common.enums.UserType;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.utils.UserContext;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.CardModel;
import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.service.IFeatureCardService;
import com.xzp.smartcampus.human.service.IStudentContactService;
import com.xzp.smartcampus.mobileapi.param.CrQueryParam;
import com.xzp.smartcampus.mobileapi.param.MyCardParam;
import com.xzp.smartcampus.mobileapi.param.QuotaParam;
import com.xzp.smartcampus.mobileapi.service.IOneCardPassService;
import com.xzp.smartcampus.mobileapi.vo.AmountVo;
import com.xzp.smartcampus.mobileapi.vo.CardVo;
import com.xzp.smartcampus.portal.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OneCardPassServiceImpl implements IOneCardPassService {

    @Resource
    private IFeatureCardService cardService;

    @Resource
    private IStudentContactService contactService;

    @Resource
    private YktClient yktClient;

    /**
     * 或者我的卡列表，如果是家长就把学生的卡都展示出来
     *
     * @return List<CardVo>
     */
    @Override
    public List<CardVo> getMyCardList() {
        LoginUserInfo userInfo = UserContext.getLoginUser();
        // 老师查询自己的数据
        if (UserType.PARENT.getKey().equals(userInfo.getUserType())) {
            return this.getChildrenCardModels(userInfo.getContact(), userInfo);
        }
        List<CardModel> cardModels = cardService.selectList(new QueryWrapper<CardModel>()
                .eq("user_id", userInfo.getUserId())
        );
        if (CollectionUtils.isEmpty(cardModels)) {
            log.info("cardModels is  null by userId {}", userInfo.getUserId());
            return Collections.emptyList();
        }
        return this.toCardVos(cardModels, userInfo);
    }

    /**
     * 查询孩子的卡
     *
     * @param contact 手机号码
     * @return List<CardModel>
     */
    private List<CardVo> getChildrenCardModels(String contact, LoginUserInfo userInfo) {
        if (StringUtils.isEmpty(contact)) {
            log.warn("contact is null");
            return Collections.emptyList();
        }
        List<StudentContactModel> contactModels = contactService.selectList(new QueryWrapper<StudentContactModel>()
                .eq("contact", contact)
        );
        if (CollectionUtils.isEmpty(contactModels)) {
            log.info("not find contactModels by contact {}", contact);
            return Collections.emptyList();
        }
        List<String> studentIds = contactModels.stream().map(StudentContactModel::getStudentId).collect(Collectors.toList());
        List<CardModel> cardModels = cardService.selectList(new QueryWrapper<CardModel>()
                .in("user_id", studentIds)
        );
        if (CollectionUtils.isEmpty(cardModels)) {
            log.info("not find cardModels by studentIds {}", studentIds);
            return Collections.emptyList();
        }
        return this.toCardVos(cardModels, userInfo);
    }

    /**
     * 转换为vo对象
     *
     * @param cardModels 数据
     * @param userInfo   userInfo
     * @return List<CardVo>
     */
    private List<CardVo> toCardVos(List<CardModel> cardModels, LoginUserInfo userInfo) {
        if (CollectionUtils.isEmpty(cardModels)) {
            log.warn("cardModels is null");
            return Collections.emptyList();
        }
        return cardModels.stream().map(item -> {
            CardVo cardVo = new CardVo();
            BeanUtils.copyProperties(item, cardVo);
            cardVo.setUserName(userInfo.getName());
            cardVo.setUserType(userInfo.getUserType());
            cardVo.setUserNumber(userInfo.getUserNumber());
            cardVo.setBalance(BigDecimal.valueOf(0));
            return cardVo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询消费记录
     *
     * @param queryParam 查询条件
     * @param current    当前页
     * @param pageSize   每页大小
     * @return PageResult<AmountVo>
     */
    @Override
    public PageResult<AmountVo> getConsumptionRecord(CrQueryParam queryParam, Integer current, Integer pageSize) {
        return this.getAmountVoPage(queryParam, current, pageSize, YktRecordQueryParam.XFZL_XF);
    }

    /**
     * 查询消费充值记录
     *
     * @param queryParam 查询条件
     * @param current    当前页
     * @param pageSize   也数量
     * @param xfzl       类型
     * @return PageResult<AmountVo>
     */
    private PageResult<AmountVo> getAmountVoPage(CrQueryParam queryParam, Integer current, Integer pageSize, String xfzl) {
        YktRecordQueryParam param = new YktRecordQueryParam();
        param.setRfkh(queryParam.getCardNumber());
        param.setRybh(queryParam.getCardNumber());
        param.setXfzl(xfzl);
        if (queryParam.getStartTime() != null) {
            param.setBegintime(queryParam.getStartTime());
        }
        if (queryParam.getEndTime() != null) {
            param.setEndtime(queryParam.getEndTime());
        }
        PageResult<TktRecordVo> pageResult = yktClient.getTurnoverRecordList(param);
        return new PageResult<>(pageResult.getTotal(), pageResult.getTotalPage(), this.toAmountVos(pageResult.getData()));
    }

    /**
     * 转换为消费记录vo
     *
     * @param data 数据
     * @return List<AmountVo>
     */
    private List<AmountVo> toAmountVos(List<TktRecordVo> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        return data.stream().map(item -> {
            AmountVo amountVo = new AmountVo();
            amountVo.setAmount(item.getXfje());
            amountVo.setScene(item.getXffs());
            amountVo.setOperationTime(item.getXfsj());
            return amountVo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询充值记录
     *
     * @param queryParam 查询条件
     * @param current    当前页
     * @param pageSize   每页大小
     * @return PageResult<AmountVo>
     */
    @Override
    public PageResult<AmountVo> getRechargeRecord(CrQueryParam queryParam, Integer current, Integer pageSize) {
        return this.getAmountVoPage(queryParam, current, pageSize, YktRecordQueryParam.XFZL_ZK);
    }

    /**
     * 创建我的卡申请
     *
     * @param cardParam 参数
     */
    @Override
    public void saveMyCard(MyCardParam cardParam) {
        if (StringUtils.isEmpty(cardParam.getUserId())) {
            log.warn("userId is null");
            throw new SipException("userId 不能为空");
        }
        // 如果是补办的话需要传入老的卡号
        if (CardModel.SERVICE_TYPE_MAKE_UP.equals(cardParam.getServiceType()) && org.apache.commons.lang3.StringUtils.isBlank(cardParam.getOldCardNumber())) {
            log.warn("old card number is null");
            throw new SipException("补办业务，oldCardNumber 字段不能为空");
        }
        // 删除老的卡号
        if (StringUtils.isEmpty(cardParam.getOldCardNumber()) && CardModel.SERVICE_TYPE_MAKE_UP.equals(cardParam.getServiceType())) {
            // 删除卡
            yktClient.deleteCardById(cardParam.getOldCardNumber());
            cardService.delete(new UpdateWrapper<CardModel>()
                    .eq("user_id", cardParam.getUserId())
                    .eq("card_number", cardParam.getOldCardNumber())
            );
        }
        CardModel cardModel = new CardModel();
        cardModel.setId(SqlUtil.getUUId());
        cardModel.setUserId(cardParam.getUserId());
        cardModel.setServiceType(cardParam.getServiceType());
        cardModel.setReason(cardParam.getReason());
        cardModel.setCardStatus(CardModel.SERVICE_TYPE_MAKE_UP.equals(cardParam.getServiceType()) ? CardModel.STATUS_MAKE_UP : CardModel.STATUS_OPENING);
        cardService.insert(cardModel);
    }

    /**
     * 限额
     *
     * @param quotaParam 参数
     */
    @Override
    public void saveCardQuota(@Valid QuotaParam quotaParam) {
        if (StringUtils.isEmpty(quotaParam.getCardNumber()) || StringUtils.isEmpty(quotaParam.getQuota())) {
            log.warn("cardNumber or quota is null");
            throw new SipException("参数错误，cardNumber和quota不能为空");
        }
        List<CardModel> cardModels = cardService.selectList(new QueryWrapper<CardModel>()
                .eq("user_id", quotaParam.getUserId())
                .eq("card_number", quotaParam.getCardNumber())
        );
        if (CollectionUtils.isEmpty(cardModels)) {
            log.info("cardModels not find by card_number {}", quotaParam.getCardNumber());
            return;
        }
        // 调用优卡特限制额度
        yktClient.setCardQuota(quotaParam.getCardNumber(), quotaParam.getQuota());
        cardModels.forEach(item -> {
            item.setQuota(quotaParam.getQuota());
        });
        cardService.updateBatch(cardModels);
    }
}
