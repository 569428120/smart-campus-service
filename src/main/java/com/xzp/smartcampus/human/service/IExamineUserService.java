package com.xzp.smartcampus.human.service;

import com.xzp.smartcampus.common.service.IBaseService;
import com.xzp.smartcampus.human.model.ExamineUserModel;
import com.xzp.smartcampus.human.vo.ExamineUserVo;

import java.util.List;

/**
 * @author SGS
 */
public interface IExamineUserService extends IBaseService<ExamineUserModel> {

    /**
     * 获取门禁审核人列表
     *
     * @param classId 班级id
     * @param name    名称
     * @param number  编号
     * @return List<ExamineUserVo>
     */
    List<ExamineUserVo> getAccessExamineUserList(String classId, String name, String number);
}
