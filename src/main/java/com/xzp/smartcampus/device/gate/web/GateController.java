package com.xzp.smartcampus.device.gate.web;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.device.gate.model.GateModel;
import com.xzp.smartcampus.device.gate.service.IGateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@RestController
@RequestMapping("/device-manage/gate")
public class GateController {
    @Resource
    private IGateService gateService;

    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getGateListPage(GateModel searchValue,
                                                      @RequestParam(value = "current") Integer current,
                                                      @RequestParam(value = "pageSize") Integer pageSize) {

        return ResponseEntity.ok(gateService.getGateListPage(searchValue, current, pageSize));
    }

    @PostMapping("/posts")
    public ResponseEntity<GateModel> postGateModel(@RequestBody GateModel gateModel) {
        return ResponseEntity.ok(gateService.saveGateModel(gateModel));
    }

    @PostMapping("/validator")
    public ResponseEntity<String> validatorGate(@RequestBody GateModel gateModel) {
        try {
            gateService.validatorGate(gateModel);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("");
    }

    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteRegionByIds(@RequestParam(value = "gateIds", defaultValue = "") String gateIds) {
        gateService.deleteByIds(Arrays.asList(gateIds.split(",")));
        return ResponseEntity.ok("删除成功");
    }

}
