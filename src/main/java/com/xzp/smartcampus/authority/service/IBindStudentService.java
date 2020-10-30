package com.xzp.smartcampus.authority.service;

import com.xzp.smartcampus.authority.param.BindStudentParam;
import com.xzp.smartcampus.authority.vo.BindStudentVo;
import com.xzp.smartcampus.human.model.StudentModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author SGS
 */
public interface IBindStudentService {

    /**
     * 获取绑定的学生列表
     *
     * @param cellNumber 手机号码
     * @return List<BindStudentVo>
     */
    List<BindStudentVo> getBindStudentList(@NotBlank(message = "手机号码不能为空") String cellNumber);

    /**
     * 绑定学生
     *
     * @param paramVo 参数
     * @return StudentModel
     */
    StudentModel bindStudent(@Valid BindStudentParam paramVo);

    /**
     * 删除家长信息，就相当于解除关联
     *
     * @param cellNumber 家长的手机号码
     * @param studentId  学生id
     */
    void deleteBindStudentByIds(@NotBlank(message = "手机号码不能为空") String cellNumber, @NotBlank(message = "学生id不能为空") String studentId);
}
