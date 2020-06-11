package com.xzp.smartcampus.access_strategy.web;


import com.xzp.smartcampus.access_strategy.model.AccessStrategyModel;
import com.xzp.smartcampus.access_strategy.model.AccessStrategyTimeModel;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyService;
import com.xzp.smartcampus.access_strategy.service.IAccessStrategyTimeService;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/access-control")
public class AccessStrategyController {
    @Resource
    private IAccessStrategyService strategyService;
    @Resource
    private IAccessStrategyTimeService timeService;

    /**
     * 按条件(名称+状态)搜索策略
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/access-strategy/gets/page")
    public ResponseEntity<PageResult> selectStrategyByCondition(
            @RequestParam(value = "strategyName", required = false) String name,
            @RequestParam(value = "strategyStatus", required = false) String status,
            @RequestParam(value = "current",defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize
    ) {

        return ResponseEntity.ok(this.strategyService.findStrategyByCondition(name, status,current,pageSize));
    }

    /**
     * 新增策略
     * @param strategyModel
     * @return
     */
    @PostMapping("/access-strategy/posts")
    public ResponseEntity<String> addAccessStrategy(@RequestBody AccessStrategyModel strategyModel){
        this.strategyService.insert(strategyModel);
        return ResponseEntity.ok("New access strategy added successful!");
    }

    /**
     * 新增策略对应的时间段(批量)
     * @param timeModels
     * @return
     */
    @PostMapping("/strategy-time-quantum/posts")
    public ResponseEntity<String> addAccessStrategy(@RequestBody List<AccessStrategyTimeModel> timeModels){
        this.timeService.insertBatch(timeModels);
        return ResponseEntity.ok("New strategy-time added successful!");
    }

    /**
     * 批量删除策略(根据ids)
     * @param strategyIds
     * @return
     */
    @DeleteMapping("/access-strategy/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteAccessStrategy(@RequestParam(value = "strategyIds",required = true) String strategyIds){
        this.strategyService.deleteStrategyByIds(Arrays.asList(strategyIds.split(",")));
        return ResponseEntity.ok("Strategys deleted successfully");
    }

    /**
     * 更新策略
     * @param strategyModel
     * @return
     */
    @PostMapping("/access-strategy/status/posts")
    public ResponseEntity<String> modifyAccessStrategy(@RequestBody AccessStrategyModel strategyModel){
        this.strategyService.updateById(strategyModel);
        return ResponseEntity.ok("Strategy modified successfully");
    }

    /**
     * 更新策略下的 策略-时间段 数据,合并增改
     * @param strategyTimeModels 时间段 Model
     * @return
     */
    @PostMapping("/strategy-time-quantum/posts")
    public ResponseEntity<String> modifyAccessStrategyTime(@RequestBody List<AccessStrategyTimeModel> strategyTimeModels){
        if(CollectionUtils.isEmpty(strategyTimeModels)){
            throw new SipException("StrategyTimeModels is null!");
        }
        this.timeService.modifyAccessStrategyTime(strategyTimeModels);

        return ResponseEntity.ok("Strategy-Time modified successfully");
    }

    /**
     * 查询时间段
     * @param strategyId 策略id
     * @return
     */
    @GetMapping("/strategy-time-quantum/gets/gets-by-strategyid")
    public ResponseEntity<List> selectStrategyPeriod(@RequestParam(value = "strategyId",required = true) String strategyId){

        return ResponseEntity.ok(this.timeService.findStrategyPeriod(strategyId));
    }

    /**
     * 删除时间段(批量)
     * @param ids   时间段id列表
     * @return
     */
    @GetMapping("/strategy-time-quantum/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteStrategyPeriod(@RequestParam(value ="ids",required = true) String ids){
        this.timeService.deleteByIds(Arrays.asList(ids.split(",")));
        return ResponseEntity.ok(MessageFormat.format("Strategy time-models :{0} have been deleted",ids));
    }

}















