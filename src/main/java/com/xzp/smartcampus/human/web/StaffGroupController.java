package com.xzp.smartcampus.human.web;

import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.service.IStaffGroupService;
import com.xzp.smartcampus.human.service.StaffToGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/human/staff-group")
public class StaffGroupController {

    @Resource
    private IStaffGroupService groupService;
    @Resource
    private StaffToGroupService groupMenberService;

    /* 组级别的操作 */
    //按照学生号，获取一个组的信息
    @GetMapping("/")
    public ResponseEntity<StaffGroupModel> getBystaffGid(@RequestParam(value = "staffstaffGid", defaultValue = "") String staffGid){
        return ResponseEntity.ok(groupService.selectById(staffGid));
    }
    //按照学生号，获取多个组的信息
    @GetMapping("/gets/all")
    public ResponseEntity<List<StaffGroupModel>> getAllBystaffGid(@RequestParam(value = "staffstaffGids", defaultValue = "") String staffGids){
        return ResponseEntity.ok(groupService.selectByIds(Arrays.asList(staffGids.split(","))));
    }
    // 新建一个组的信息
    @PostMapping("/posts")
    public ResponseEntity<StaffGroupModel> addStaff(@RequestBody StaffGroupModel group){
        return ResponseEntity.ok(groupService.addStaffGroup(group));
    }
    // 改变一个组的信息
    @PutMapping("/")
    @PatchMapping("/")
    public ResponseEntity<StaffGroupModel> changeBySstaffGid(@RequestBody StaffGroupModel group){
        return ResponseEntity.ok(groupService.changeStaffGroup(group));
    }
    // 删除一个组的信息
    @DeleteMapping("/")
    public ResponseEntity<String> deleteBySstaffGid(@RequestParam(value = "staffGid", defaultValue = "") String staffGid){
        StaffGroupModel group = groupService.selectById(staffGid);
        if (group == null){
            return ResponseEntity.ok("该组的信息不存在");
        }
        groupService.deleteById(staffGid);
        return ResponseEntity.ok("");
    }
    // 删除一个组的信息
    @DeleteMapping("/deletes-by-ids")
    public ResponseEntity<String> deleteBatchBySstaffGids(@RequestParam(value = "staffGid", defaultValue = "") String staffGids){
        groupService.deleteByIds(Arrays.asList(staffGids.split(",")));
        return ResponseEntity.ok("");
    }

    /* 组内成员的操作 */
    // 添加一个成员
    @PostMapping("/{staffGid}")
    public ResponseEntity<String> addMenber(@PathVariable("staffGid") String staffGid, @RequestParam(value = "member", defaultValue = "") String menber){
        boolean result = groupMenberService.putMenberIntogroup(staffGid, menber);
        if (result){
            return ResponseEntity.ok("");
        }
            return ResponseEntity.ok("添加失败");
    }
    // 删除一个成员
    @DeleteMapping("/{staffGid}")
    public ResponseEntity<String> delMenber(@PathVariable("staffGid") String staffGid, @RequestParam(value = "member", defaultValue = "") String menber){
        boolean result = groupMenberService.removeMenberOutgroup(staffGid, menber);
        if (result){
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok("添加失败");
    }



}
