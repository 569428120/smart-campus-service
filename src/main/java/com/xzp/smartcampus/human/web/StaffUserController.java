package com.xzp.smartcampus.human.web;

import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.model.StaffModel;
import com.xzp.smartcampus.human.service.IStaffUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;


@RestController
@RequestMapping("/human/staff-user")
public class StaffUserController {

    @Resource
    private IStaffUserService staffUserService;

    /**
     * 分页查询
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页数量
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getUserVoListPage(StaffModel searchValue,
                                                           @RequestParam(value = "current") Integer current,
                                                           @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(staffUserService.getUserVoListPage(searchValue, current, pageSize));
    }

    /**
     * 修改或新增方法
     *
     * @param staffModel 数据
     * @return ResponseEntity<StaffModel>
     */
    @PostMapping("/posts")
    public ResponseEntity<StaffModel> postSchoolModel(@RequestBody StaffModel staffModel) {
        return ResponseEntity.ok(staffUserService.postStaffUserModel(staffModel));
    }

    /**
     * @param userIds userIds
     * @return ResponseEntity<String>
     */
    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteBatchByIds(@RequestParam(value = "userIds", defaultValue = "") String userIds) {
        staffUserService.deleteByIds(Arrays.asList(userIds.split(",")));
        return ResponseEntity.ok("删除成功");
    }
}
