package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.service.IExamineUserService;
import com.xzp.smartcampus.human.vo.ExamineUserVo;
import com.xzp.smartcampus.mobileapi.param.MyCardParam;
import com.xzp.smartcampus.mobileapi.param.CrQueryParam;
import com.xzp.smartcampus.mobileapi.param.QuotaParam;
import com.xzp.smartcampus.mobileapi.service.IOneCardPassService;
import com.xzp.smartcampus.mobileapi.vo.AmountVo;
import com.xzp.smartcampus.mobileapi.vo.CardVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi/one-card-pass")
@Slf4j
public class OneCardPassApi {

    @Resource
    private IOneCardPassService oneCardPassService;

    @Resource
    private IExamineUserService examineUserService;

    /**
     * 查询我的卡，家长查询学生的
     *
     * @return List<CardVo>
     */
    @GetMapping("/my-card/gets")
    public ResponseEntity<List<CardVo>> getMyCardList() {
        return ResponseEntity.ok(oneCardPassService.getMyCardList());
    }

    /**
     * 查询消费记录
     *
     * @param queryParam 查询参数
     * @param current    开始也
     * @param pageSize   页数量
     * @return PageResult<AmountVo>
     */
    @GetMapping("/consumption-record/gets/page")
    public ResponseEntity<PageResult<AmountVo>> getConsumptionRecord(@Valid CrQueryParam queryParam, Integer current, Integer pageSize) {
        return ResponseEntity.ok(oneCardPassService.getConsumptionRecord(queryParam, current, pageSize));
    }

    /**
     * 查询充值记录
     *
     * @param queryParam 查询条件
     * @param current    当前页
     * @param pageSize   每页显示的数量
     * @return PageResult<AmountVo>
     */
    @GetMapping("/recharge-record/gets/page")
    public ResponseEntity<PageResult<AmountVo>> getRechargeRecord(CrQueryParam queryParam, Integer current, Integer pageSize) {
        return ResponseEntity.ok(oneCardPassService.getRechargeRecord(queryParam, current, pageSize));
    }

    /**
     * 申请挂失一卡通
     *
     * @param postParam 提交的参数
     * @return String
     */
    @PostMapping("/my-card/posts")
    public ResponseEntity<String> saveMyCard(@RequestBody @Valid MyCardParam postParam) {
        oneCardPassService.saveMyCard(postParam);
        return ResponseEntity.ok("操作成功");
    }

    /**
     * 获取审核人列表
     *
     * @param classId classId
     * @param name    name
     * @param number  number
     * @return List<ExamineUserVo>
     */
    @GetMapping("/examine-user/gets")
    public ResponseEntity<List<ExamineUserVo>> getExamineUserList(String classId, String name, String number) {
        return ResponseEntity.ok(examineUserService.getCardExamineUserList(classId, name, number));
    }

    /**
     * 一卡通限额接口
     *
     * @param quotaParam 参数
     * @return String
     */
    @PostMapping("/my-card/quota/posts")
    public ResponseEntity<String> saveQuota(@RequestBody @Valid QuotaParam quotaParam) {
        oneCardPassService.saveCardQuota(quotaParam);
        return ResponseEntity.ok("操作成功");
    }

    @PostMapping("my-card/recharge/posts")
    public ResponseEntity<String> saveCardRecharge(@RequestBody @Valid QuotaParam quotaParam) {
        return ResponseEntity.ok("暂不支持充值");
    }
}
