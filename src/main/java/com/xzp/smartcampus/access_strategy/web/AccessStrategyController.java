package com.xzp.smartcampus.access_strategy.web;


import com.xzp.smartcampus.access_strategy.model.AccessStrategyDetailModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import com.xzp.smartcampus.common.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/access-control")
public class AccessStrategyController {
    @Resource
    private IAccessStrategyService strategyService;

    /**
     * 按条件(名称+状态)搜索策略
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/access-strategy/gets/page")
    public ResponseEntity<PageResult> selectStrategyByCondition(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "current",defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize
    ) {

        return ResponseEntity.ok(this.strategyService.findStrategyByCondition(name, status,current,pageSize));
    }

    /**
     * 新增策略
     * @param strategyDetailModel
     * @return
     */
    @PostMapping("/access-strategy/posts")
    public ResponseEntity<String> addAccessStrategy(@RequestBody AccessStrategyDetailModel strategyDetailModel){
        this.strategyService.createAccessStrategy(strategyDetailModel);
        return ResponseEntity.ok("New access strategy added successful!");
    }

    /**
     * 删除策略(根据id)
     * @param strategyId
     * @return
     */
    @DeleteMapping("/deletes/strategy")
    public ResponseEntity<String> deleteAccessStrategy(@RequestParam(value = "strategyId",required = true) String strategyId){
        this.strategyService.deleteStrategyById(strategyId);
        return ResponseEntity.ok("Strategy deleted successfully");
    }

    /**
     * 更新策略
     * @param strategyModel
     * @return
     */
    @PutMapping("/puts/strategy")
    public ResponseEntity<String> modifyAccessStrategy(@RequestBody AccessStrategyModel strategyModel){
        this.strategyService.updateById(strategyModel);
        return ResponseEntity.ok("Strategy modified successfully");
    }






}















