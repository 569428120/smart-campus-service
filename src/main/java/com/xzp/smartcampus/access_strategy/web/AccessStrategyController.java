package com.xzp.smartcampus.access_strategy.web;


import com.xzp.smartcampus.access_strategy.model.AccessStrategyDetailModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/access-strategy")
public class AccessStrategyController {
    @Resource
    private IAccessStrategyService strategyService;

    /**
     * 按条件(名称+状态)搜索策略
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/gets/search")
    public ResponseEntity<List<AccessStrategyDetailModel>> selectStrategyByCondition(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status
    ) {
        List<AccessStrategyDetailModel> strategyDetailModels = this.strategyService.findStrategyByCondition(name, status);

        return ResponseEntity.ok(strategyDetailModels);
    }

    /**
     * 新增策略
     * @param strategyDetailModel
     * @return
     */
    @PostMapping("/posts/strategy")
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















