package com.xzp.smartcampus.access_strategy.web;


import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import com.xzp.smartcampus.common.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@RestController
@RequestMapping("/access-control/access-strategy")
public class AccessStrategyController {
    @Resource
    private IAccessStrategyService strategyService;

    /**
     * 按条件(名称+状态)搜索策略
     *
     * @param name   名称
     * @param status 状态
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> selectStrategyByCondition(
            @RequestParam(value = "strategyName", required = false) String name,
            @RequestParam(value = "strategyStatus", required = false) String status,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize
    ) {

        return ResponseEntity.ok(this.strategyService.findStrategyByCondition(name, status, current, pageSize));
    }

    /**
     * 保存策略
     *
     * @param strategyModel strategyModel
     * @return ResponseEntity<String>
     */
    @PostMapping("/posts")
    public ResponseEntity<AccessStrategyModel> saveAccessStrategy(@RequestBody AccessStrategyModel strategyModel) {
        return ResponseEntity.ok(strategyService.saveAccessStrategy(strategyModel));
    }

    /**
     * 批量删除策略(根据ids)
     *
     * @param strategyIds strategyIds
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteAccessStrategy(@RequestParam(value = "strategyIds") String strategyIds) {
        this.strategyService.deleteStrategyByIds(Arrays.asList(strategyIds.split(",")));
        return ResponseEntity.ok("Strategys deleted successfully");
    }

    /**
     * 更新状态
     *
     * @param strategyId strategyId
     * @param status     status
     * @return ResponseEntity<String>
     */
    @PostMapping("/status/posts")
    public ResponseEntity<String> updateAccessStrategyStatus(@RequestParam(value = "strategyId") String strategyId,
                                                             @RequestParam(value = "status") String status) {
        strategyService.updateAccessStrategyStatus(strategyId, status);
        return ResponseEntity.ok("Strategy modified successfully");
    }
}















