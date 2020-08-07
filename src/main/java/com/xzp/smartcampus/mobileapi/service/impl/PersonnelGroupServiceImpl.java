package com.xzp.smartcampus.mobileapi.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.human.model.*;
import com.xzp.smartcampus.human.service.*;
import com.xzp.smartcampus.mobileapi.service.IPersonnelGroupService;
import com.xzp.smartcampus.mobileapi.vo.PersonnelGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SGS
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PersonnelGroupServiceImpl implements IPersonnelGroupService {

    @Resource
    private IStaffGroupService staffGroupService;

    @Resource
    private IStudentGroupService studentGroupService;

    @Resource
    private IStaffUserService staffUserService;

    @Resource
    private IStudentService studentService;

    @Resource
    private IStudentContactService contactService;

    /**
     * 获取用户分组
     *
     * @param nodeId   节点id
     * @param withUser 是否查询用户
     * @return List<PersonnelGroupVo>
     */
    @Override
    public List<PersonnelGroupVo> getPersonnelGroupByNodeId(String nodeId, Boolean withUser) {
        if (StringUtils.isBlank(nodeId)) {
            nodeId = Constant.ROOT;
        }
        // root
        if (Constant.ROOT.equals(nodeId)) {
            return Stream.of(PersonnelGroupVo.GROUP_ROOT_STAFF, PersonnelGroupVo.GROUP_ROOT_STUDENT).map(name -> {
                PersonnelGroupVo groupVo = new PersonnelGroupVo();
                groupVo.setId(name);
                groupVo.setType(PersonnelGroupVo.GROUP_TYPE_GROUP);
                groupVo.setName(name);
                groupVo.setNumber(name);
                return groupVo;
            }).collect(Collectors.toList());
        }
        // 职工组 root
        if (PersonnelGroupVo.GROUP_ROOT_STAFF.equals(nodeId)) {
            List<StaffGroupModel> groupModels = staffGroupService.selectList(new QueryWrapper<StaffGroupModel>()
                    .eq("pid", Constant.ROOT)
            );
            if (CollectionUtils.isEmpty(groupModels)) {
                return Collections.emptyList();
            }
            return groupModels.stream().map(PersonnelGroupVo::newInstance).collect(Collectors.toList());
        }

        // 学生组
        if (PersonnelGroupVo.GROUP_ROOT_STUDENT.equals(nodeId)) {
            List<StudentGroupModel> studentGroupModels = studentGroupService.selectList(new QueryWrapper<StudentGroupModel>()
                    .eq("pid", Constant.ROOT)
            );
            if (CollectionUtils.isEmpty(studentGroupModels)) {
                return Collections.emptyList();
            }
            return studentGroupModels.stream().map(PersonnelGroupVo::newInstance).collect(Collectors.toList());
        }

        // 其他情况都查
        List<PersonnelGroupVo> groupVos = new ArrayList<>();
        // 职工组
        List<StaffGroupModel> groupModels = staffGroupService.selectList(new QueryWrapper<StaffGroupModel>()
                .eq("pid", nodeId)
        );
        if (!CollectionUtils.isEmpty(groupModels)) {
            groupVos.addAll(groupModels.stream().map(PersonnelGroupVo::newInstance).collect(Collectors.toList()));
        }
        // 学生组
        List<StudentGroupModel> studentGroupModels = studentGroupService.selectList(new QueryWrapper<StudentGroupModel>()
                .eq("pid", nodeId)
        );
        if (!CollectionUtils.isEmpty(studentGroupModels)) {
            groupVos.addAll(studentGroupModels.stream().map(PersonnelGroupVo::newInstance).collect(Collectors.toList()));
        }

        // 查询人员
        if (withUser == null || withUser) {
            List<PersonnelGroupVo> personnelGroupVos = this.getPersonnelListByGroupId(nodeId);
            if (!CollectionUtils.isEmpty(personnelGroupVos)) {
                groupVos.addAll(personnelGroupVos);
            }
        }
        return groupVos;
    }

    /**
     * 查询人员列表
     *
     * @param nodeId 节点id
     * @return List<PersonnelGroupVo>
     */
    private List<PersonnelGroupVo> getPersonnelListByGroupId(String nodeId) {
        if (StringUtils.isBlank(nodeId)) {
            log.warn("nodeId is null");
            return Collections.emptyList();
        }
        // 职工
        List<PersonnelGroupVo> personnelGroupVos = new ArrayList<>();
        List<StaffModel> staffModels = staffUserService.selectList(new QueryWrapper<StaffModel>()
                .eq("group_id", nodeId)
        );
        if (!CollectionUtils.isEmpty(staffModels)) {
            personnelGroupVos.addAll(staffModels.stream().map(PersonnelGroupVo::newInstance).collect(Collectors.toList()));
        }
        // 学生
        List<StudentModel> studentModels = studentService.selectList(new QueryWrapper<StudentModel>()
                .eq("group_id", nodeId)
        );
        if (!CollectionUtils.isEmpty(studentModels)) {
            List<String> studentIds = studentModels.stream().map(StudentModel::getId).collect(Collectors.toList());
            Map<String, List<PersonnelGroupVo>> studentIdToParentListMap = this.getStudentIdToParentListMap(studentIds);
            personnelGroupVos.addAll(studentModels.stream().map(item -> PersonnelGroupVo.newInstance(item, studentIdToParentListMap.get(item.getId()))).collect(Collectors.toList()));
        }
        return personnelGroupVos;
    }

    /**
     * 查询联系人
     *
     * @param studentIds studentIds
     * @return 学生id映射联系人
     */
    private Map<String, List<PersonnelGroupVo>> getStudentIdToParentListMap(List<String> studentIds) {
        if (CollectionUtils.isEmpty(studentIds)) {
            log.warn("studentIds is null");
            return Collections.emptyMap();
        }
        List<StudentContactModel> contactModels = contactService.selectList(new QueryWrapper<StudentContactModel>()
                .in("student_id", studentIds)
        );
        if (CollectionUtils.isEmpty(contactModels)) {
            return Collections.emptyMap();
        }
        Map<String, List<PersonnelGroupVo>> studentIdToParentListMap = new HashMap<>(contactModels.size());
        contactModels.forEach(item -> {
            studentIdToParentListMap.computeIfAbsent(item.getStudentId(), k -> new ArrayList<>())
                    .add(PersonnelGroupVo.newInstance(item));
        });
        return studentIdToParentListMap;
    }

}
