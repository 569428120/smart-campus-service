package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.CardModel;
import com.xzp.smartcampus.mobileapi.param.CardPostParam;
import com.xzp.smartcampus.mobileapi.param.CardQueryParam;
import com.xzp.smartcampus.mobileapi.param.ExamineCardPostParam;
import com.xzp.smartcampus.mobileapi.service.ICardManageService;
import com.xzp.smartcampus.mobileapi.vo.CardVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi/card-manage")
@Slf4j
public class CardManageApi {

    @Resource
    private ICardManageService cardManageService;

    /**
     * 获取卡列表
     *
     * @param param    搜索参数
     * @param current  当前页
     * @param pageSize 页数量
     * @return 返回数据
     */
    @GetMapping("/gets/page")
    public ResponseEntity<Map<String, Object>> getCardPage(@Valid CardQueryParam param,
                                                           @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                           @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        return ResponseEntity.ok(cardManageService.getCardPage(param, current, pageSize));
    }

    /**
     * 获取待审卡的列表
     *
     * @param param    搜索参数
     * @param current  当前页
     * @param pageSize 页数量
     * @return 返回数据
     */
    @GetMapping("/examine/gets/page")
    public ResponseEntity<PageResult<CardVo>> getExamineCardPage(@Valid CardQueryParam param,
                                                                 @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                                 @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        return ResponseEntity.ok(cardManageService.getExamineCardPage(param, current, pageSize));
    }

    /**
     * 获取id的类型
     *
     * @return 卡的类型
     */
    @GetMapping("/card-type/gets")
    public ResponseEntity<List<Map<String, Object>>> getCardTypeList() {
        List<Map<String, Object>> cardTypeList = new ArrayList<>();
        Map<String, Object> icMap = new HashMap<>(2);
        icMap.put("name", "IC卡");
        icMap.put("value", CardModel.CARD_TYPE_IC);
        cardTypeList.add(icMap);
        return ResponseEntity.ok(cardTypeList);
    }

    /**
     * 保存卡的数据
     *
     * @param param 参数
     * @return String
     */
    @PostMapping("/posts")
    public ResponseEntity<String> saveCardData(@RequestBody @Valid CardPostParam param) {
        cardManageService.saveCardData(param);
        return ResponseEntity.ok("操作成功");
    }

    /**
     * 删除卡数据
     *
     * @param cardIds id列表
     * @return String
     */
    @DeleteMapping("deletes/deletes-by-ids")
    public ResponseEntity<String> deleteCardByIds(@RequestParam(value = "cardIds") List<String> cardIds) {
        cardManageService.deleteCardByIds(cardIds);
        return ResponseEntity.ok("操作成功");
    }

    /**
     * 审核卡
     *
     * @param param 参数
     * @return String
     */
    @PostMapping("posts/to-examine")
    public ResponseEntity<String> examineCard(@RequestBody @Valid ExamineCardPostParam param) {
        cardManageService.examineCard(param);
        return ResponseEntity.ok("操作成功");
    }

}
