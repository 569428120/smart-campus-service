package com.xzp.smartcampus.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xzp.smartcampus.authority.param.BindStudentParam;
import com.xzp.smartcampus.authority.service.IBindStudentService;
import com.xzp.smartcampus.authority.service.IUserToRoleService;
import com.xzp.smartcampus.authority.vo.BindStudentVo;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.DataUtil;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.human.mapper.CardMapper;
import com.xzp.smartcampus.human.mapper.StudentContactMapper;
import com.xzp.smartcampus.human.mapper.StudentGroupMapper;
import com.xzp.smartcampus.human.mapper.StudentMapper;
import com.xzp.smartcampus.human.model.CardModel;
import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.system.model.SchoolModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BindStudentServiceImpl implements IBindStudentService {

    /**
     * 数据不隔离使用mapper
     */
    @Resource
    private StudentContactMapper studentContactMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private StudentGroupMapper studentGroupMapper;

    @Resource
    private IUserToRoleService userToRoleService;

    @Resource
    private CardMapper cardMapper;

    /**
     * 获取绑定的学生列表
     *
     * @param cellNumber 手机号码
     * @return List<BindStudentVo>
     */
    @Override
    public List<BindStudentVo> getBindStudentList(@NotBlank(message = "手机号码不能为空") String cellNumber) {
        List<StudentContactModel> studentContactModels = studentContactMapper.selectList(new QueryWrapper<StudentContactModel>()
                .eq("contact", cellNumber)
        );
        if (CollectionUtils.isEmpty(studentContactModels)) {
            log.info("not find studentContactModels by cellNumber {}", cellNumber);
            return Collections.emptyList();
        }
        List<String> studentIds = studentContactModels.stream().map(StudentContactModel::getStudentId).collect(Collectors.toList());
        List<StudentModel> studentModels = studentMapper.selectBatchIds(studentIds);
        if (CollectionUtils.isEmpty(studentModels)) {
            log.info("not find studentModels by ids {}", studentIds);
            return Collections.emptyList();
        }
        return this.toBindStudentVos(studentModels);
    }

    /**
     * 转换为vo对象
     *
     * @param studentModels studentModels
     * @return List<BindStudentVo>
     */
    private List<BindStudentVo> toBindStudentVos(List<StudentModel> studentModels) {
        if (CollectionUtils.isEmpty(studentModels)) {
            log.warn("studentModels is null");
            return Collections.emptyList();
        }
        List<String> groupIds = studentModels.stream().map(StudentModel::getGroupId).collect(Collectors.toList());
        List<String> schoolIds = studentModels.stream().map(StudentModel::getSchoolId).collect(Collectors.toList());
        Map<String, StudentGroupModel> groupIdToClassMap = this.getGroupIdToClassMap(groupIds);
        Map<String, SchoolModel> schoolIdToModelMap = userToRoleService.getIdToSchoolModelMap(schoolIds);

        return studentModels.stream().map(item -> {
            BindStudentVo bindStudentVo = new BindStudentVo();
            bindStudentVo.setStudentId(item.getId());
            bindStudentVo.setLongName(item.getName() + "-" + item.getStudentCode());
            if (schoolIdToModelMap.containsKey(item.getSchoolId())) {
                bindStudentVo.setSchoolName(schoolIdToModelMap.get(item.getSchoolId()).getSchoolName());
            }
            if (groupIdToClassMap.containsKey(item.getGroupId())) {
                StudentGroupModel groupModel = groupIdToClassMap.get(item.getGroupId());
                bindStudentVo.setClassName(groupModel.getGradeLevel() + " 年级 " + groupModel.getGroupName());
            }
            return bindStudentVo;
        }).collect(Collectors.toList());
    }

    /**
     * 班级
     *
     * @param groupIds groupIds
     * @return StudentModel
     */
    private Map<String, StudentGroupModel> getGroupIdToClassMap(List<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            log.warn("groupIds is null");
            return Collections.emptyMap();
        }
        List<StudentGroupModel> studentGroupModels = studentGroupMapper.selectBatchIds(groupIds);
        if (CollectionUtils.isEmpty(studentGroupModels)) {
            log.info("not find studentGroupModels by groupIds {}", groupIds);
            return Collections.emptyMap();
        }

        List<String> allIds = new ArrayList<>();
        studentGroupModels.forEach(item -> {
            List<String> treePathList = DataUtil.strToList(item.getTreePath(), Constant.TREE_SEPARATOR);
            allIds.addAll(treePathList);
        });

        List<StudentGroupModel> groupModels = studentGroupMapper.selectList(new QueryWrapper<StudentGroupModel>()
                .in("id", allIds)
                .eq("type", StudentGroupModel.TYPE_CLASS)
        );
        if (CollectionUtils.isEmpty(groupModels)) {
            log.warn("not find groupModels by allIds {}", allIds);
            return Collections.emptyMap();
        }

        Map<String, StudentGroupModel> groupIdToClassMap = new HashMap<>(groupIds.size());
        groupModels.forEach(item -> {
            studentGroupModels.forEach(sItem -> {
                if (sItem.getTreePath().contains(item.getId())) {
                    groupIdToClassMap.put(sItem.getId(), item);
                }
            });
        });
        return groupIdToClassMap;
    }

    /**
     * 绑定学生
     *
     * @param paramVo 参数
     */
    @Override
    public StudentModel bindStudent(@Valid BindStudentParam paramVo) {
        List<CardModel> cardModels = cardMapper.selectList(new QueryWrapper<CardModel>()
                .eq("card_number", paramVo.getCardNumber())
        );
        if (CollectionUtils.isEmpty(cardModels)) {
            log.warn("not find cardModels by card_number {}", paramVo.getCardNumber());
            throw new SipException("学生不存在，请输入正确的学生信息");
        }
        List<String> studentIds = cardModels.stream().map(CardModel::getUserId).collect(Collectors.toList());
        List<StudentModel> studentModels = studentMapper.selectList(new QueryWrapper<StudentModel>()
                .in("id", studentIds)
                .eq("name", paramVo.getStudentName())
        );
        if (CollectionUtils.isEmpty(studentModels)) {
            log.warn("not find studentModels by studentIds {}", studentIds);
            throw new SipException("学生不存在，请输入正确的学生信息");
        }
        // 校验放在重复绑定
        List<StudentContactModel> studentContactModels = studentContactMapper.selectList(new QueryWrapper<StudentContactModel>()
                .in("contact", paramVo.getCardNumber())
                .in("student_id", studentIds)
        );
        if (!CollectionUtils.isEmpty(studentContactModels)) {
            log.warn("studentContactModels exist");
            throw new SipException("该学生已和当前用户绑定，无法重复操作");
        }
        // 添加联系人
        List<StudentContactModel> newContactModels = studentModels.stream().map(item -> {
            StudentContactModel contactModel = new StudentContactModel();
            BeanUtils.copyProperties(item, contactModel);
            contactModel.setId(SqlUtil.getUUId());
            contactModel.setStudentId(item.getId());
            contactModel.setContact(paramVo.getCellNumber());
            contactModel.setName(item.getName() + "家长");
            contactModel.setFamilyType(StudentContactModel.FAMILY_TYPE_PARENT);
            return contactModel;
        }).collect(Collectors.toList());
        // 先循环保存
        newContactModels.forEach(item -> studentContactMapper.insert(item));
        return studentModels.get(0);
    }

    /**
     * 删除家长信息，就相当于解除关联
     *
     * @param cellNumber 家长的手机号码
     * @param studentId  学生id
     */
    @Override
    public void deleteBindStudentByIds(@NotBlank(message = "手机号码不能为空") String cellNumber, @NotBlank(message = "学生id不能为空") String studentId) {
        studentContactMapper.delete(new UpdateWrapper<StudentContactModel>()
                .eq("contact", cellNumber)
                .eq("student_id", studentId)
        );
    }

}
