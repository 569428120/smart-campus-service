package com.xzp.smartcampus.access_strategy.web;


import com.xzp.smartcampus.access_strategy.model.AccessStrategyDetailModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import org.apache.commons.lang3.StringUtils;
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

    @GetMapping("/search")
    public ResponseEntity<List<AccessStrategyDetailModel>> selectStrategyByCondition(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) String status
    ) {
        List<AccessStrategyDetailModel> strategyDetailModels = this.strategyService.findStrategyByCondition(name, status);

        return ResponseEntity.ok(strategyDetailModels);
    }

    @PostMapping("/strategy")
    public ResponseEntity<String> addAccessStrategy(@RequestBody AccessStrategyDetailModel strategyDetailModel){
        this.strategyService.createAccessStrategy(strategyDetailModel);
        return ResponseEntity.ok("New access strategy added successful!");
    }


}















