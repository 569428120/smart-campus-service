package com.xzp.smartcampus.system.web;


import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.system.model.AuthorityGroupModel;
import com.xzp.smartcampus.system.service.IAuthorityGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 权限组
 */
@RestController
@RequestMapping("/authority-group")
public class AuthorityGroupController {

    @Resource
    private IAuthorityGroupService authorityGroupService;

    /**
     * 分页查询教育局数据
     *
     * @param searchValue 搜索条件
     * @param current     当前页
     * @param pageSize    页容量
     * @return ResponseEntity<PageResult>
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getAuthorityGroupPage(AuthorityGroupModel searchValue,
                                                            @RequestParam(value = "current") Integer current,
                                                            @RequestParam(value = "pageSize") Integer pageSize) {

        return ResponseEntity.ok(authorityGroupService.getAuthorityGroupPage(searchValue, current, pageSize));
    }

    /**
     * 保存数据
     *
     * @param groupModel groupModel
     * @return ResponseEntity<AuthorityGroupModel>
     */
    @PostMapping("/posts")
    public ResponseEntity<AuthorityGroupModel> saveAuthorityGroup(@RequestBody AuthorityGroupModel groupModel) {
        return ResponseEntity.ok(authorityGroupService.saveAuthorityGroup(groupModel));
    }

    /**
     * 删除数据
     *
     * @param menuIds menuIds
     * @return ResponseEntity<String>
     */
    @GetMapping("/deletes/deletes-by-ids")
    public ResponseEntity<String> deleteAuthorityGroupByIds(@RequestParam(value = "groupIds", defaultValue = "") String groupIds) {
        authorityGroupService.deleteAuthorityGroupByIds(Arrays.asList(groupIds.split(",")));
        return ResponseEntity.ok("删除成功");
    }
}
