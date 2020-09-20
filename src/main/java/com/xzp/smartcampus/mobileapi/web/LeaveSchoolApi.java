package com.xzp.smartcampus.mobileapi.web;

import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.mobileapi.model.LSRecordModel;
import com.xzp.smartcampus.mobileapi.service.ILeaveSchoolService;
import com.xzp.smartcampus.mobileapi.vo.LSRecordSearchParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author SGS
 */
@RestController
@RequestMapping("mobileApi/leave-school")
public class LeaveSchoolApi {

    @Resource
    private ILeaveSchoolService leaveSchoolService;

    /**
     * 一键放学历史记录查询
     *
     * @param searchParam 查询参数
     * @param current     当前页
     * @param pageSize    每页条数
     * @return PageResult
     */
    @GetMapping("/gets/page")
    public ResponseEntity<PageResult> getLeaveSchoolRecord(
            LSRecordSearchParam searchParam,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return ResponseEntity.ok(this.leaveSchoolService.selectLsRecord(searchParam, current, pageSize));
    }

    /**
     * 一键放学审核记录列表
     *
     * @param searchParam
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping("/approval/gets/page")
    public ResponseEntity<PageResult> getLeaveSchoolApprovalRecord(
            LSRecordSearchParam searchParam,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return ResponseEntity.ok(this.leaveSchoolService.selectLsApprovalRecord(searchParam, current, pageSize));
    }

    /**
     * 一键放学班级审核列表
     *
     * @return
     */
    @GetMapping("/class-list/gets")
    public ResponseEntity<List> getLeaveSchoolApprovalClasses() {
        return ResponseEntity.ok(this.leaveSchoolService.selectLsApprovalClasses());
    }

    /**
     * 查询一键放学审核人列表
     *
     * @return
     */
    @GetMapping("/examine-user/gets")
    public ResponseEntity<List> getLeaveSchoolExamineUsers() {
        return ResponseEntity.ok(this.leaveSchoolService.selectLsExamineUsers());
    }

    /**
     * 新增一键放学
     *
     * @param recordModel
     * @return
     */
    @PostMapping("/posts")
    public ResponseEntity<String> saveLsRecord(@RequestBody @Valid LSRecordModel recordModel) {
        this.leaveSchoolService.saveLsRecord(recordModel);
        return ResponseEntity.ok("OK");
    }

    /**
     * 一键放学同意
     *
     * @param leaveId
     * @return
     */
    @PostMapping("/posts/confirm")
    public ResponseEntity<String> confirmLsRecord(String leaveId) {
        if (StringUtils.isBlank(leaveId)) {
            throw new SipException("leaveId 不能为空");
        }
        this.leaveSchoolService.confirmLsRecord(leaveId);
        return ResponseEntity.ok("OK");
    }

    /**
     * 拒绝一键放学
     *
     * @param leaveId
     * @return
     */
    @PostMapping("/posts/cancel")
    public ResponseEntity<String> denyLsRecord(@NotBlank(message = "leaveId 不能为空") String leaveId) {
        if (StringUtils.isBlank(leaveId)) {
            throw new SipException("leaveId 不能为空");
        }
        this.leaveSchoolService.denyLsRecord(leaveId);
        return ResponseEntity.ok("OK");
    }

}
