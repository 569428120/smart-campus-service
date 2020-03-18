package com.xzp.smartcampus.human.web;

import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/human/Staffs")
public class StaffController {

    @Resource
    private StaffService StaffService;

    /* 单个员工的CRUD */
    //获取一个员工的信息
    @GetMapping("/")
    public ResponseEntity<StaffModel> queryByid(@RequestParam(value = "id", defaultValue = "") String id){
        return ResponseEntity.ok(StaffService.selectById(id));
    }
    // 新建一个员工的信息
    @PostMapping("/")
    public ResponseEntity<StaffModel> addStaff(@RequestBody StaffModel StaffModel){
        return ResponseEntity.ok(StaffService.addStaff(StaffModel));
    }
    // 改变一个员工的信息
    @PutMapping("/")
    @PatchMapping("/")
    public ResponseEntity<StaffModel> changeBySid(@RequestBody StaffModel StaffModel){
        return ResponseEntity.ok(StaffService.changeStaff(StaffModel));
    }
    // 删除一个员工的信息
    @DeleteMapping("/")
    public ResponseEntity<String> deleteBySid(@RequestParam(value = "sid", defaultValue = "") String sid){
        StaffModel Staff = StaffService.selectById(sid);
        if (Staff == null){
            return ResponseEntity.ok("该员工信息不存在");
        }
        StaffService.deleteById(sid);
        return ResponseEntity.ok("");
    }

    /* 员工 批量 CRUD 暂时不需要 */

}
