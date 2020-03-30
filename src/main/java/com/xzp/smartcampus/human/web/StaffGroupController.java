package com.xzp.smartcampus.human.web;

import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.service.StaffGroupService;
import com.xzp.smartcampus.human.service.StaffToGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/human/staff-group")
public class StaffGroupController {

    @Resource
    private StaffGroupService groupService;
    @Resource
    private StaffToGroupService groupMenberService;

    /* 组级别的操作 */
    //按照学生号，获取一个组的信息
    @GetMapping("/gets/all")
    public ResponseEntity<StaffGroupModel> queryBygid(@RequestParam(value = "gid", defaultValue = "") String gid){
        return ResponseEntity.ok(groupService.selectById(gid));
    }
    // 新建一个组的信息
    @PostMapping("/posts")
    public ResponseEntity<StaffGroupModel> addStaff(@RequestBody StaffGroupModel group){
        return ResponseEntity.ok(groupService.addStaffGroup(group));
    }
    // 改变一个组的信息
    @PutMapping("/")
    @PatchMapping("/")
    public ResponseEntity<StaffGroupModel> changeBySgid(@RequestBody StaffGroupModel group){
        return ResponseEntity.ok(groupService.changeStaffGroup(group));
    }
    // 删除一个组的信息
    @DeleteMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteBySgid(@RequestParam(value = "gid", defaultValue = "") String gid){
        StaffGroupModel group = groupService.selectById(gid);
        if (group == null){
            return ResponseEntity.ok("该组的信息不存在");
        }
        groupService.deleteById(gid);
        return ResponseEntity.ok("");
    }

    /* 组内成员的操作 */
    // 添加一个成员
    @PostMapping("/{gid}")
    public ResponseEntity<String> addMenber(@PathVariable("gid") String gid, @RequestParam(value = "member", defaultValue = "") String menber){
        boolean result = groupMenberService.putMenberIntogroup(gid, menber);
        if (result){
            return ResponseEntity.ok("");
        }
            return ResponseEntity.ok("添加失败");
    }
    // 删除一个成员
    @DeleteMapping("/{gid}")
    public ResponseEntity<String> delMenber(@PathVariable("gid") String gid, @RequestParam(value = "member", defaultValue = "") String menber){
        boolean result = groupMenberService.removeMenberOutgroup(gid, menber);
        if (result){
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok("添加失败");
    }



}
