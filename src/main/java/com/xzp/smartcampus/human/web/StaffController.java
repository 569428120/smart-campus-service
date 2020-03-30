package com.xzp.smartcampus.human.web;

import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/human/staff-user")
public class StaffController {

    @Resource
    private StaffService staffService;

    /* ********************** 单个员工的CRUD ********************** */

    /** 获取一个员工的信息

     */
    @GetMapping("/gets/page")
    public ResponseEntity<StaffModel> queryById(@RequestParam(value = "id", defaultValue = "") String id){
        return ResponseEntity.ok(staffService.selectById(id));
    }
    // 新建一个员工的信息
    // {"regionId": "test", "schoolId": "test", "groupId": "test", "name": "test", "studentCode": "test", "address": "test"}
    @PostMapping("/posts")
    public ResponseEntity<StaffModel> addStaff(@RequestBody StaffModel StaffModel){
        return ResponseEntity.ok(staffService.addStaff(StaffModel));
    }
    // 改变一个员工的信息
    @PostMapping("/login-user/posts")
    public ResponseEntity<StaffModel> savePWD(@RequestBody StaffModel StaffModel){
        return ResponseEntity.ok(staffService.addStaff(StaffModel));
    }

    @PutMapping("/")
    @PatchMapping("/")
    public ResponseEntity<StaffModel> changeById(@RequestBody StaffModel StaffModel){
        return ResponseEntity.ok(staffService.changeStaff(StaffModel));
    }
    // 删除一个员工的信息
    @DeleteMapping("/")
    public ResponseEntity<String> deleteById(@RequestParam(value = "id", defaultValue = "") String id){
        StaffModel Staff = staffService.selectById(id);
        if (Staff == null){
            return ResponseEntity.ok("该员工信息不存在");
        }
        staffService.deleteById(id);
        return ResponseEntity.ok("");
    }

    /* 员工 批量 CRUD 暂时不需要 */




}
