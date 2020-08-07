package com.xzp.smartcampus.access_examine.web;

import com.xzp.smartcampus.access_examine.model.AccessFlowModel;
import com.xzp.smartcampus.access_examine.service.IAccessFlowService;
import com.xzp.smartcampus.access_examine.vo.*;
import com.xzp.smartcampus.common.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Controller
@RequestMapping("/flow/flow-record")
public class AccessFlowController {
    @Resource
    IAccessFlowService flowService;

    /**
     * 查询代办流程
     *
     * @param searchParam
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping("/todo/gets/page")
    public ResponseEntity<PageResult> getTodoAccessFlow(
            @RequestParam(value = "searchValue") AccessFlowSearchParam searchParam,
            @RequestParam(value = "current") Integer current,
            @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(this.flowService.searchTodoAccessFlow(searchParam, current, pageSize));
    }

    /**
     * 查询已办结流程
     *
     * @param searchParam
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping("/already/gets/page")
    public ResponseEntity<PageResult> getAlreadyAccessFlow(
            @RequestParam(value = "searchValue") AccessFlowSearchParam searchParam,
            @RequestParam(value = "current") Integer current,
            @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(this.flowService.searchAlreadyAccessFlow(searchParam, current, pageSize));
    }

    /**
     * 查询我创建的流程
     *
     * @param searchParam
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping("/my-create/gets/page")
    public ResponseEntity<PageResult> getMineAccessFlow(
            @RequestParam(value = "searchValue") AccessFlowSearchParam searchParam,
            @RequestParam(value = "current") Integer current,
            @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(this.flowService.searchMineAccessFlow(searchParam, current, pageSize));
    }

    /**
     * 创建流程
     * @param flowModel
     * @return
     */
    @PostMapping("/create-flow/posts")
    public ResponseEntity<AccessFlowModel> addAccessFlow(@RequestBody AccessFlowModel flowModel){
        return ResponseEntity.ok(this.flowService.createAccessFlow(flowModel));
    }

    @PutMapping("/examine-flow/puts")
    public ResponseEntity<AccessFlowModel> examineAccessFlow(@RequestBody AccessFlowModel flowModel){
        return ResponseEntity.ok(this.flowService.examineAccessFlow(flowModel));
    }


}
