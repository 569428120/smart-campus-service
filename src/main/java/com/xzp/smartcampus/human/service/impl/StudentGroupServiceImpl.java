package com.xzp.smartcampus.human.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xzp.smartcampus.common.exception.SipException;
import com.xzp.smartcampus.common.service.IsolationBaseService;
import com.xzp.smartcampus.common.utils.Constant;
import com.xzp.smartcampus.common.utils.DataUtil;
import com.xzp.smartcampus.common.utils.SqlUtil;
import com.xzp.smartcampus.common.utils.TreeUtil;
import com.xzp.smartcampus.human.mapper.StudentGroupMapper;
import com.xzp.smartcampus.human.model.StaffGroupModel;
import com.xzp.smartcampus.human.model.StudentGroupModel;
import com.xzp.smartcampus.human.model.StudentModel;
import com.xzp.smartcampus.human.service.IStudentGroupService;
import com.xzp.smartcampus.human.service.IStudentService;
import com.xzp.smartcampus.human.vo.StudentGroupTreeVo;
import com.xzp.smartcampus.human.vo.UserGroupTreeVo;
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
public class StudentGroupServiceImpl extends IsolationBaseService<StudentGroupMapper, StudentGroupModel> implements IStudentGroupService {

    @Resource
    private IStudentService studentService;

    /**
     * 查询分组树
     *
     * @param searchValue 搜索条件
     * @return List<StudentGroupTreeVo>
     */
    @Override
    public List<StudentGroupTreeVo> getStudentGroupVoTreeList(StudentGroupModel searchValue) {
        List<StudentGroupModel> groupModels = this.getStudentGroupModelList(searchValue);
        if (CollectionUtils.isEmpty(groupModels)) {
            return Collections.emptyList();
        }
        // 转为树结构
        return TreeUtil.modelToTreeVo(groupModels, new StudentGroupTreeVo());
    }

    /**
     * 查询model
     *
     * @param searchValue searchValue
     * @return List<StudentGroupModel>
     */
    private List<StudentGroupModel> getStudentGroupModelList(StudentGroupModel searchValue) {
        List<StudentGroupModel> groupModels = this.selectList(new QueryWrapper<StudentGroupModel>()
                .like(StringUtils.isNotBlank(searchValue.getGroupName()), "group_name", searchValue.getGroupName())
                .orderByAsc("create_time")
        );
        if (CollectionUtils.isEmpty(groupModels)) {
            return Collections.emptyList();
        }
        // 查询需要展示的但是未搜索到的数据
        List<String> pids = this.getNotFindPids(groupModels);
        if (!CollectionUtils.isEmpty(pids)) {
            List<StudentGroupModel> pModels = this.selectList(new QueryWrapper<StudentGroupModel>()
                    .in("id", pids)
                    .orderByAsc("grade_level")
            );
            if (!CollectionUtils.isEmpty(pModels)) {
                groupModels.addAll(pModels);
            }
        }
        return groupModels;
    }


    /**
     * 删除学生分组
     *
     * @param groupIds 分组id
     */
    @Override
    public void deleteGroupByIds(List<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            log.warn("groupIds is null");
            return;
        }
        List<StudentGroupModel> groupModels = this.selectByIds(groupIds);
        if (CollectionUtils.isEmpty(groupModels)) {
            log.warn("not find groupModels by groupIds {}", groupIds);
            return;
        }
        List<String> ids = groupModels.stream().map(StudentGroupModel::getId).collect(Collectors.toList());
        List<StudentGroupModel> childrenList = this.getChildrenList(groupModels);
        if (!CollectionUtils.isEmpty(childrenList)) {
            childrenList.forEach(item -> {
                ids.add(item.getId());
            });
        }
        // 删除关联的用户
        studentService.delete(new UpdateWrapper<StudentModel>()
                .in("group_id", ids)
        );
        this.deleteByIds(ids);
    }

    /**
     * 保存数据
     *
     * @param groupModel groupModel
     * @return StudentGroupModel
     */
    @Override
    public StudentGroupModel postGroupModel(StudentGroupModel groupModel) {
        if (StringUtils.isBlank(groupModel.getId())) {
            return this.createGroupModel(groupModel);
        }
        return this.updateGroupModel(groupModel);
    }

    /**
     * 更新分组
     *
     * @param groupModel groupModel
     * @return StudentGroupModel
     */
    private StudentGroupModel updateGroupModel(StudentGroupModel groupModel) {
        if (StringUtils.isBlank(groupModel.getId())) {
            log.warn("id is null");
            throw new SipException("参数错误，更新操作id不能为空");
        }
        StudentGroupModel localModel = this.selectById(groupModel.getId());
        if (localModel == null) {
            log.warn("not find localModel by id {}", groupModel.getId());
            throw new SipException("参数错误，找不到更新对象 id " + groupModel.getId());
        }
        localModel.setAuthorityId(groupModel.getAuthorityId());
        localModel.setAccessStrategyId(groupModel.getAccessStrategyId());
        localModel.setGroupName(groupModel.getGroupName());
        localModel.setDescription(groupModel.getDescription());
        // 更新年级
        if ("grade".equals(groupModel.getType()) && groupModel.getGradeLevel() != null) {
            localModel.setGradeLevel(groupModel.getGradeLevel());
            this.updateChildrenGradeLevel(localModel, groupModel.getGradeLevel());
        }
        this.updateById(localModel);
        return localModel;
    }

    /**
     * 更新子节点的年级数据
     *
     * @param pModel     父节点
     * @param gradeLevel 年级
     */
    private void updateChildrenGradeLevel(StudentGroupModel pModel, Integer gradeLevel) {
        if (pModel == null || gradeLevel == null) {
            log.warn("gradeLevel or pModel is null");
            return;
        }
        List<StudentGroupModel> children = this.getChildrenList(Collections.singletonList(pModel));
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        children.forEach(item -> {
            item.setGradeLevel(gradeLevel);
        });
        this.updateBatch(children);
    }

    /**
     * 新建分组
     *
     * @param groupModel groupModel
     * @return StudentGroupModel
     */
    private StudentGroupModel createGroupModel(StudentGroupModel groupModel) {
        groupModel.setId(SqlUtil.getUUId());
        groupModel.setGroupCode(groupModel.getId());
        if (StringUtils.isBlank(groupModel.getPid()) || Constant.ROOT.equals(groupModel.getPid())) {
            groupModel.setPid(Constant.ROOT);
            groupModel.setTreePath(Constant.ROOT + Constant.TREE_SEPARATOR + groupModel.getId());
        } else {
            StudentGroupModel pModel = this.selectById(groupModel.getPid());
            if (pModel == null) {
                log.warn("not find pModel by id " + groupModel.getPid());
                throw new SipException("参数错误，找不到父节点数据 id " + groupModel.getPid());
            }
            groupModel.setPid(pModel.getId());
            groupModel.setTreePath(pModel.getTreePath() + Constant.TREE_SEPARATOR + groupModel.getId());
        }
        this.insert(groupModel);
        return groupModel;
    }

    /**
     * 复制分组
     *
     * @param sourceIds sourceIds
     * @param targetIds targetIds
     */
    @Override
    public void copyGroupToGroups(List<String> sourceIds, List<String> targetIds) {
        if (CollectionUtils.isEmpty(sourceIds) || CollectionUtils.isEmpty(targetIds)) {
            log.info("sourceIds or targetIds is null");
            return;
        }
        List<StudentGroupModel> sourceGroupModels = this.selectByIds(sourceIds);
        if (CollectionUtils.isEmpty(sourceGroupModels)) {
            log.warn("not find sourceGroupModels by ids {}", sourceIds);
            return;
        }
        List<StudentGroupModel> sourceChildrenList = this.getChildrenList(sourceGroupModels);
        List<StudentGroupModel> targetGroupModels = this.selectByIds(targetIds);
        if (CollectionUtils.isEmpty(targetGroupModels)) {
            log.warn("not find targetGroupModels by ids {}", targetIds);
            return;
        }
        Map<String, StudentGroupModel> sourceIdAndPidToCopyModelMap = new LinkedHashMap<>(targetGroupModels.size() * (sourceGroupModels.size() + sourceChildrenList.size()));
        targetGroupModels.forEach(pModel -> {
            sourceGroupModels.forEach(groupModel -> {
                StudentGroupModel newModel = new StudentGroupModel();
                BeanUtils.copyProperties(groupModel, newModel);
                newModel.setId(SqlUtil.getUUId());
                newModel.setGroupCode(newModel.getId());
                newModel.setPid(pModel.getId());
                newModel.setTreePath(pModel.getTreePath() + Constant.TREE_SEPARATOR + newModel.getId());
                newModel.setGradeLevel(pModel.getGradeLevel());
                sourceIdAndPidToCopyModelMap.put(groupModel.getId() + pModel.getId(), newModel);
            });
        });
        // 安装treePath的长度排序
        sourceChildrenList.sort(Comparator.comparingInt(o -> DataUtil.getTreePathNumber(o.getTreePath())));
        targetGroupModels.forEach(sModel -> {
            sourceChildrenList.forEach(item -> {
                StudentGroupModel pModel = sourceIdAndPidToCopyModelMap.get(item.getPid() + sModel.getId());
                if (pModel == null) {
                    log.error("data error,not find in sourceIdAndPidToCopyModelMap by sourceId {} pid {}", item.getPid(), sModel.getId());
                    throw new SipException("数据错误，找不到原始父节点 sourceId " + item.getPid() + " pid " + sModel.getId());
                }
                StudentGroupModel newModel = new StudentGroupModel();
                BeanUtils.copyProperties(item, newModel);
                newModel.setId(SqlUtil.getUUId());
                newModel.setPid(pModel.getId());
                newModel.setTreePath(pModel.getTreePath() + Constant.TREE_SEPARATOR + newModel.getId());
                newModel.setGradeLevel(pModel.getGradeLevel());
                sourceIdAndPidToCopyModelMap.put(item.getId() + sModel.getId(), newModel);
            });
        });
        // 保存
        if (!CollectionUtils.isEmpty(sourceIdAndPidToCopyModelMap)) {
            this.insertBatch(sourceIdAndPidToCopyModelMap.values());
        }
    }

    /**
     * 获取分组子子节点数据
     *
     * @param groupModels groupIds
     * @return List<StaffGroupModel>
     */
    private List<StudentGroupModel> getChildrenList(List<StudentGroupModel> groupModels) {
        if (CollectionUtils.isEmpty(groupModels)) {
            log.info("groupModels is null");
            return Collections.emptyList();
        }
        List<String> ids = groupModels.stream().map(StudentGroupModel::getId).collect(Collectors.toList());
        QueryWrapper<StudentGroupModel> wrapper = new QueryWrapper<>();
        wrapper.notIn("id", ids);
        wrapper.and((qw) -> {
            groupModels.forEach(item -> {
                qw.or();
                qw.like("tree_path", item.getTreePath());
            });
            return qw;
        });
        List<StudentGroupModel> children = this.selectList(wrapper);
        return CollectionUtils.isEmpty(children) ? Collections.emptyList() : children;
    }

    /**
     * 移动分组
     *
     * @param sourceIds sourceIds
     * @param targetId  targetId
     */
    @Override
    public void moveGroupToGroups(List<String> sourceIds, String targetId) {
        if (CollectionUtils.isEmpty(sourceIds) || StringUtils.isBlank(targetId)) {
            log.info("sourceIds or targetId is null");
            return;
        }
        List<StudentGroupModel> sourceGroupModels = this.selectByIds(sourceIds);
        if (CollectionUtils.isEmpty(sourceGroupModels)) {
            log.warn("not find sourceGroupModels by ids {}", sourceIds);
            return;
        }
        List<StudentGroupModel> sourceChildrenList = this.getChildrenList(sourceGroupModels);
        StudentGroupModel targetGroupModel = this.selectById(targetId);
        if (targetGroupModel == null) {
            log.warn("not find targetGroupModel by id {}", targetId);
            return;
        }
        Map<String, StudentGroupModel> idToMoveModelMap = new LinkedHashMap<>(sourceGroupModels.size() + sourceChildrenList.size());
        sourceGroupModels.forEach(item -> {
            item.setPid(targetGroupModel.getId());
            item.setTreePath(targetGroupModel.getTreePath() + Constant.TREE_SEPARATOR + item.getId());
            item.setGradeLevel(targetGroupModel.getGradeLevel());
            idToMoveModelMap.put(item.getId(), item);
        });
        sourceChildrenList.sort(Comparator.comparingInt(o -> DataUtil.getTreePathNumber(o.getTreePath())));
        sourceChildrenList.forEach(item -> {
            StudentGroupModel pModel = idToMoveModelMap.get(item.getPid());
            if (pModel == null) {
                log.error("data error,not find in idToMoveModelMap by id {}", item.getPid());
                throw new SipException("数据错误，找不到原始父节点 id " + item.getPid());
            }
            item.setPid(pModel.getId());
            item.setTreePath(pModel.getTreePath() + Constant.TREE_SEPARATOR + item.getId());
            item.setGradeLevel(pModel.getGradeLevel());
            idToMoveModelMap.put(item.getId(), item);
        });
        // 保存
        if (!CollectionUtils.isEmpty(idToMoveModelMap)) {
            this.updateBatch(idToMoveModelMap.values());
        }
    }

    /**
     * 移动用户到分组
     *
     * @param userIds  userIds
     * @param targetId targetId
     */
    @Override
    public void moveUserToGroups(List<String> userIds, String targetId) {
        if (CollectionUtils.isEmpty(userIds) || StringUtils.isBlank(targetId)) {
            log.warn("userIds or targetId is null");
            return;
        }
        List<StudentModel> userModels = studentService.selectByIds(userIds);
        if (CollectionUtils.isEmpty(userModels)) {
            log.warn("not find userModels by ids {}", userIds);
            return;
        }
        StudentGroupModel targetGroupModel = this.selectById(targetId);
        if (targetGroupModel == null) {
            log.warn("not find targetGroupModel by id {}", targetId);
            return;
        }
        userModels.forEach(item -> {
            item.setGroupId(targetGroupModel.getId());
        });
        studentService.updateBatch(userModels);
    }

    /**
     * 返回班级树结构
     *
     * @param searchValue searchValue
     * @return List<UserGroupTreeVo>
     */
    @Override
    public List<UserGroupTreeVo> getUserGroupTreeList(UserGroupTreeVo searchValue) {
        StudentGroupModel searchModel = new StudentGroupModel();
        searchModel.setGroupName(searchValue.getGroupName());
        searchModel.setGroupCode(searchValue.getGroupCode());
        List<StudentGroupModel> studentGroupModels = this.getStudentGroupModelList(searchModel);
        if (CollectionUtils.isEmpty(studentGroupModels)) {
            return Collections.emptyList();
        }
        return TreeUtil.modelToTreeVo(studentGroupModels.stream().map(item -> {
            StaffGroupModel groupModel = new StaffGroupModel();
            BeanUtils.copyProperties(item, groupModel);
            return groupModel;
        }).collect(Collectors.toList()), new UserGroupTreeVo());
    }

    /**
     * 获取班级下的学生列表
     *
     * @param classId 班级id
     * @param name    名称
     * @param number  编号
     * @return List<StudentModel>
     */
    @Override
    public List<StudentModel> getStudentModelListByGroupId(String classId, String name, String number) {
        if (StringUtils.isBlank(classId)) {
            log.warn("classId is null");
            return Collections.emptyList();
        }
        StudentGroupModel groupModel = this.selectById(classId);
        if (groupModel == null) {
            log.warn("groupModel is null");
            throw new SipException("参数错误，找不到学生分组对象 classId " + classId);
        }
        List<String> groupIds = new ArrayList<>();
        groupIds.add(groupModel.getId());
        List<StudentGroupModel> childrenList = this.getChildrenList(Collections.singletonList(groupModel));
        if (!CollectionUtils.isEmpty(childrenList)) {
            groupIds.addAll(childrenList.stream().map(StudentGroupModel::getId).collect(Collectors.toList()));
        }
        List<StudentModel> studentModels = studentService.selectList(new QueryWrapper<StudentModel>()
                .in("group_id", groupIds)
        );
        if (CollectionUtils.isEmpty(studentModels)) {
            log.info("not find studentModels by groupIds {}", groupIds);
            return Collections.emptyList();
        }
        return studentModels;
    }

    /**
     * 查询需要展示的但是未搜索到的数据
     *
     * @param groupModels groupModels
     * @return List<String>
     */
    private List<String> getNotFindPids(List<StudentGroupModel> groupModels) {
        if (CollectionUtils.isEmpty(groupModels)) {
            return Collections.emptyList();
        }
        List<String> treePaths = groupModels.stream().map(StudentGroupModel::getTreePath).collect(Collectors.toList());
        List<String> ids = TreeUtil.treePathToIds(treePaths);
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        Map<String, StudentGroupModel> idToModelMap = groupModels.stream().collect(Collectors.toMap(StudentGroupModel::getId, v -> v));
        return ids.stream().filter(id -> !idToModelMap.containsKey(id)).collect(Collectors.toList());
    }
}
