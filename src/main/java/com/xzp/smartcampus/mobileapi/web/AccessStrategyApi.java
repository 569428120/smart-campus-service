package com.xzp.smartcampus.mobileapi.web;


import com.xzp.smartcampus.access_examine.vo.AccessFlowSearchParam;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.mobileapi.service.IMobileAccessStrategyService;
import com.xzp.smartcampus.mobileapi.vo.AccessStrategyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SGS
 */
@RestController
@RequestMapping("/mobileApi/access-strategy")
@Slf4j
public class AccessStrategyApi {

    @Resource
    private IMobileAccessStrategyService strategyService;

    /**
     * 查询策略列表
     *
     * @param searchParam 搜索条件
     * @param current     当前页
     * @param pageSize    页数量
     * @return PageResult
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getAccessStrategyPage(AccessStrategyVo searchParam,
                                                            @RequestParam(value = "current") Integer current,
                                                            @RequestParam(value = "pageSize") Integer pageSize) {

        return ResponseEntity.ok(strategyService.getAccessStrategyPage(searchParam, current, pageSize));
    }

    @GetMapping("/gets/gets-by-id")
    public ResponseEntity<AccessStrategyVo> getAccessStrategyVoById(String strategyId) {
        return ResponseEntity.ok(strategyService.getAccessStrategyVoById(strategyId));
    }

    /**
     * 保存策略
     *
     * @param strategyVo strategyVo
     * @return String
     */
    @PostMapping("/posts")
    public ResponseEntity<String> saveAccessStrategy(@RequestBody AccessStrategyVo strategyVo) {
        strategyService.saveAccessStrategy(strategyVo);
        return ResponseEntity.ok("ok");
    }

    /**
     * 策略分配
     *
     * @param strategyId 策略id
     * @param groupIds   分组id
     * @return String
     */
    @PostMapping("/strategy-to-group/posts")
    public ResponseEntity<String> strategyToGroups(@RequestParam(value = "strategyId") String strategyId, @RequestParam(value = "groupIds") List<String> groupIds) {
        strategyService.saveStrategyToGroupIds(strategyId, groupIds);
        return ResponseEntity.ok("ok");
    }

    /**
     * 删除策略
     *
     * @param strategyIds 策略id
     * @return String
     */
    @DeleteMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteStrategyByIds(@RequestParam(value = "strategyIds") List<String> strategyIds) {
        strategyService.deleteStrategyByIds(strategyIds);
        return ResponseEntity.ok("ok");
    }
}
