package com.xzp.smartcampus.human.web;

import javax.annotation.Resource;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IStaffUserService;


@RestController
@RequestMapping("/human/staff-user")
public class StaffController {

    @Resource
    private IStaffUserService staffService;

    /* ********************** 单个员工的CRUD ********************** */

    //获取一个员工的信息
    @GetMapping("/gets/id")
    public ResponseEntity<StaffModel> getById(@RequestParam(value = "id", defaultValue = "") String id){
        return ResponseEntity.ok(staffService.selectById(id));
    }
    //获取多个员工的信息，分页
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getStaffListPage(StaffModel searchValue,
                                                       @RequestParam(value = "current") Integer current,
                                                       @RequestParam(value = "pageSize") Integer pageSize){
        return ResponseEntity.ok(staffService.getStaffListPage(searchValue, current, pageSize));
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


    /* 员工 批量 CRUD */
    @DeleteMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteBatchByIds(@RequestParam(value = "staffIds", defaultValue = "") String ids){
        staffService.deleteByIds(Arrays.asList(ids.split(",")));
        return ResponseEntity.ok("删除成功");
    }




}
