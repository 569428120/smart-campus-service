package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzp.smartcampus.common.enums.UserType;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.vo.PageResult;
import com.xzp.smartcampus.human.mapper.StudentMapper;
import com.xzp.smartcampus.human.model.StudentContactModel;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.IStudentContactService;
import com.xzp.smartcampus.human.service.IStudentGroupService;
import com.xzp.smartcampus.human.service.IStudentService;
import com.xzp.smartcampus.human.vo.StudentVo;
import com.xzp.smartcampus.human.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class StudentServiceImpl extends IsolationBaseService<StudentMapper, StudentModel> implements IStudentService {

    @Resource
    private IStudentGroupService groupService;

    @Resource
    private IStudentContactService contactService;

    /**
     * 分页查询
     *
     * @param searchValue searchValue
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<StudentVo>
     */
    @Override
    public PageResult<StudentVo> getStudentVoListPage(StudentModel searchValue, Integer current, Integer pageSize) {
        PageResult<StudentModel> modelPage = this.getStudentModelPage(searchValue, current, pageSize);
        return new PageResult<>(modelPage.getTotal(), modelPage.getTotalPage(), this.toStudentVoList(modelPage.getData()));
    }

    /**
     * 转换为Vo对象
     *
     * @param models models
     * @return List<StudentVo>
     */
    private List<StudentVo> toStudentVoList(List<StudentModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return Collections.emptyList();
        }
        Set<String> groupIds = models.stream().map(StudentModel::getGroupId).collect(Collectors.toSet());
        List<String> studentIds = models.stream().map(StudentModel::getId).collect(Collectors.toList());
        Map<String, StudentGroupModel> groupIdToModelMap = this.getGroupIdToModelMap(groupIds);
        Map<String, List<StudentContactModel>> studentIdToContactListMap = this.getStudentIdToContactListMap(studentIds);
        return models.stream().map(item -> {
            StudentVo userVo = new StudentVo();
            BeanUtils.copyProperties(item, userVo);
            if (groupIdToModelMap.containsKey(item.getGroupId())) {
                userVo.setGroupName(this.getTreePathNames(groupIdToModelMap.get(item.getGroupId()), groupIdToModelMap));
            }
            userVo.setStudentContactList(studentIdToContactListMap.get(item.getId()));
            return userVo;
        }).collect(Collectors.toList());
    }

    /**
     * 学生id映射联系人
     *
     * @param studentIds 学生id列表
     * @return 学生id映射联系人
     */
    private Map<String, List<StudentContactModel>> getStudentIdToContactListMap(List<String> studentIds) {
        if (CollectionUtils.isEmpty(studentIds)) {
            log.info("studentIds is null");
            return Collections.emptyMap();
        }
        List<StudentContactModel> contactModels = contactService.selectList(new QueryWrapper<StudentContactModel>()
                .in("student_id", studentIds)
        );
        if (CollectionUtils.isEmpty(contactModels)) {
            return Collections.emptyMap();
        }
        Map<String, List<StudentContactModel>> studentIdToContactListMap = new HashMap<>(studentIds.size());
        contactModels.forEach(item -> {
            studentIdToContactListMap.computeIfAbsent(item.getStudentId(), k -> new ArrayList<>()).add(item);
        });
        return studentIdToContactListMap;
    }

    /**
     * treepath转换名称
     *
     * @param groupModel        groupModel
     * @param groupIdToModelMap groupIdToModelMap
     * @return String
     */
    private String getTreePathNames(StudentGroupModel groupModel, Map<String, StudentGroupModel> groupIdToModelMap) {
        if (groupModel == null) {
            return "";
        }
        if (StringUtils.isBlank(groupModel.getTreePath())) {
            return "";
        }
        List<String> names = new ArrayList<>(3);
        Arrays.asList(groupModel.getTreePath().split(Constant.TREE_SEPARATOR)).forEach(id -> {
            if (Constant.ROOT.equals(id)) {
                return;
            }
            StudentGroupModel model = groupIdToModelMap.get(id);
            if (model == null) {
                names.add("已被删除");
                return;
            }
            names.add(model.getGroupName());
        });
        return String.join("/", names);
    }

    /**
     * groupId映射分组
     *
     * @param groupIds groupIds
     * @return id映射分组
     */
    private Map<String, StudentGroupModel> getGroupIdToModelMap(Set<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return Collections.emptyMap();
        }
        List<StudentGroupModel> groupModels = groupService.selectByIds(groupIds);
        if (CollectionUtils.isEmpty(groupModels)) {
            return Collections.emptyMap();
        }
        List<String> treePaths = groupModels.stream().map(StudentGroupModel::getTreePath).collect(Collectors.toList());
        List<String> ids = new ArrayList<>(treePaths.size());
        treePaths.forEach(treePath -> {
            if (StringUtils.isBlank(treePath)) {
                return;
            }
            ids.addAll(Arrays.asList(treePath.split(Constant.TREE_SEPARATOR)));
        });
        List<StudentGroupModel> allModels = groupService.selectByIds(ids);
        if (CollectionUtils.isEmpty(allModels)) {
            return Collections.emptyMap();
        }
        return allModels.stream().collect(Collectors.toMap(StudentGroupModel::getId, v -> v));
    }

    /**
     * 获取分页model
     *
     * @param searchValue searchValue
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<StudentModel>
     */
    private PageResult<StudentModel> getStudentModelPage(StudentModel searchValue, Integer current, Integer pageSize) {
        List<String> groupIds = this.getGroupIds(searchValue.getGroupId());
        return this.selectPage(new Page<>(current, pageSize), new QueryWrapper<StudentModel>()
                .in(!CollectionUtils.isEmpty(groupIds), "group_id", groupIds)
                .like(StringUtils.isNotBlank(searchValue.getName()), "name", searchValue.getName())
                .like(StringUtils.isNotBlank(searchValue.getStudentCode()), "student_code", searchValue.getStudentCode())
                .orderByDesc("create_time")
        );
    }

    /**
     * 获取分组子节点
     *
     * @param groupId groupId
     * @return List<String>
     */
    private List<String> getGroupIds(String groupId) {
        if (StringUtils.isBlank(groupId) || Constant.ROOT.equals(groupId)) {
            return Collections.emptyList();
        }
        StudentGroupModel groupModel = groupService.selectById(groupId);
        if (groupModel == null) {
            return Collections.singletonList("-1");
        }
        List<StudentGroupModel> groupModels = groupService.selectList(new QueryWrapper<StudentGroupModel>()
                .likeRight("tree_path", groupModel.getTreePath())
        );
        if (CollectionUtils.isEmpty(groupModels)) {
            return Collections.singletonList("-1");
        }
        return groupModels.stream().map(StudentGroupModel::getId).collect(Collectors.toList());
    }

    /**
     * 保存数据
     *
     * @param studentModel studentModel
     * @return StudentModel
     */
    @Override
    public StudentModel postStudentModel(StudentModel studentModel) {
        this.validatorStudentModel(studentModel);
        if (StringUtils.isBlank(studentModel.getId())) {
            return this.createStudentModel(studentModel);
        }
        return this.updateStudentModel(studentModel);
    }

    /**
     * 校验数据
     *
     * @param studentModel studentModel
     */
    @Override
    public void validatorStudentModel(StudentModel studentModel) {
        // 学生编号不能为重复
        if (StringUtils.isNotBlank(studentModel.getStudentCode())) {
            List<StudentModel> studentModels = this.selectList(new QueryWrapper<StudentModel>()
                    .eq("student_code", studentModel.getStudentCode())
                    .notIn(StringUtils.isNotBlank(studentModel.getId()), "id", Collections.singleton(studentModel.getId()))
            );
            if (!CollectionUtils.isEmpty(studentModels)) {
                log.warn("student_code {} exist", studentModel.getStudentCode());
                throw new SipException("参数错误，学号不能重复," + studentModel.getStudentCode() + "已存在");
            }
        }
    }

    /**
     * 获取学生vo
     *
     * @param studentIds studentIds
     * @return List<StudentVo>
     */
    @Override
    public List<StudentVo> getStudentVoListByIds(List<String> studentIds) {
        if (CollectionUtils.isEmpty(studentIds)) {
            return Collections.emptyList();
        }
        List<StudentModel> studentModels = this.selectByIds(studentIds);
        if (CollectionUtils.isEmpty(studentModels)) {
            return Collections.emptyList();
        }
        return this.toStudentVoList(studentModels);
    }

    /**
     * 分页查询，转换为UserVo对象
     *
     * @param searchValue searchValue
     * @param groupId     groupId
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<UserVo>
     */
    @Override
    public PageResult<UserVo> getUserVoPage(UserVo searchValue, String groupId, Integer current, Integer pageSize) {
        StudentModel searchModel = new StudentModel();
        BeanUtils.copyProperties(searchValue, searchModel);
        searchModel.setGroupId(groupId);
        PageResult<StudentVo> pageResult = this.getStudentVoListPage(searchModel, current, pageSize);
        return new PageResult<>(pageResult.getTotal(), pageResult.getTotalPage(), this.studentVoListToUserVoList(pageResult.getData()));
    }

    /**
     * 转换为userVo
     *
     * @param studentVos studentVos
     * @return List<UserVo>
     */
    private List<UserVo> studentVoListToUserVoList(List<StudentVo> studentVos) {
        if (CollectionUtils.isEmpty(studentVos)) {
            return Collections.emptyList();
        }
        return studentVos.stream().map(item -> {
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUserJobCode(item.getStudentCode());
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 获得家长列表
     *
     * @param searchValue searchValue
     * @param groupId     groupId
     * @param current     current
     * @param pageSize    pageSize
     * @return PageResult<UserVo>
     */
    @Override
    public PageResult<UserVo> getParentUserVoPage(UserVo searchValue, String groupId, Integer current, Integer pageSize) {
        List<String> groupIds = this.getGroupIds(groupId);
        List<StudentModel> studentModels = this.selectList(new QueryWrapper<StudentModel>()
                .in(!CollectionUtils.isEmpty(groupIds), "group_id", groupIds)
        );
        if (CollectionUtils.isEmpty(studentModels)) {
            return PageResult.emptyPageResult();
        }
        List<String> studentIds = studentModels.stream().map(StudentModel::getId).collect(Collectors.toList());
        PageResult<StudentContactModel> pageResult = contactService.selectPage(new Page<>(current, pageSize), new QueryWrapper<StudentContactModel>()
                .in("student_id", studentIds)
                .like(StringUtils.isNotBlank(searchValue.getName()), "name", searchValue.getName())
        );
        return new PageResult<>(pageResult.getTotal(), pageResult.getTotalPage(), this.contactListToUserVoList(pageResult.getData()));
    }

    /**
     * 根据用户id获得家长列表
     *
     * @param userIds userIds
     * @return List<UserVo>
     */
    @Override
    public List<UserVo> getParentUserVoListByIds(List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            log.info("userIds is null");
            return Collections.emptyList();
        }
        List<StudentContactModel> contactModels = contactService.selectByIds(userIds);
        return this.contactListToUserVoList(contactModels);
    }

    /**
     * 联系人转换为userVo
     *
     * @param contactModels contactModels
     * @return List<UserVo>
     */
    private List<UserVo> contactListToUserVoList(List<StudentContactModel> contactModels) {
        if (CollectionUtils.isEmpty(contactModels)) {
            return Collections.emptyList();
        }
        return contactModels.stream().map(item -> {
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(item, vo);
            vo.setUserType(UserType.PARENT.getKey());
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 更新
     *
     * @param studentModel studentModel
     * @return StudentModel
     */
    private StudentModel updateStudentModel(StudentModel studentModel) {
        if (StringUtils.isBlank(studentModel.getId())) {
            log.warn("id is null");
            throw new SipException("数据错误，id不能为空");
        }
        StudentModel localModel = this.selectById(studentModel.getId());
        if (localModel == null) {
            log.warn("not find localModel by id {}", studentModel.getId());
            throw new SipException("参数错误，找不到需要更新的数据 id " + studentModel.getId());
        }
        localModel.setName(studentModel.getName());
        localModel.setStudentCode(studentModel.getStudentCode());
        localModel.setAddress(studentModel.getAddress());
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 创建
     *
     * @param studentModel studentModel
     * @return StudentModel
     */
    private StudentModel createStudentModel(StudentModel studentModel) {
        if (StringUtils.isBlank(studentModel.getGroupId())) {
            log.warn("groupId is null");
            throw new SipException("参数错误，groupId不能为空");
        }
        studentModel.setId(SqlUtil.getUUId());
        this.insert(studentModel);
        return studentModel;
    }
}
