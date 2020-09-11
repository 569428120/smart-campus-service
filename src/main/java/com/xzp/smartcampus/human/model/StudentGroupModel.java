package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * CREATE TABLE `tb_human_ser_student_group` (
 * `id` varchar(32) NOT NULL COMMENT 'id',
 * `region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
 * `school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
 * `create_time` datetime DEFAULT NULL COMMENT '创建时间',
 * `update_time` datetime DEFAULT NULL COMMENT '更新时间',
 * `logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',
 * `pid` varchar(32) DEFAULT NULL COMMENT '父节点id',
 * `tree_path` varchar(255) DEFAULT NULL COMMENT '结构关系',
 * `type` varchar(32) DEFAULT NULL COMMENT '班级，分组',
 * `grade_name` varchar(64) DEFAULT NULL COMMENT '年级名称',
 * `grade_level` varchar(64) DEFAULT NULL COMMENT '年级编码，比如一年级 就为1',
 * `group_name` varchar(64) DEFAULT NULL COMMENT '班级或者分组名称',
 * `group_code` varchar(32) DEFAULT NULL COMMENT '分组编码',
 * `authority_id` varchar(32) DEFAULT NULL COMMENT '权限组id',
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级及学生分组表';
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_student_group")
public class StudentGroupModel extends BaseModel {

    /**
     * 年级
     */
    public static final String TYPE_GRADE = "grade";

    /**
     * 班级
     */
    public static final String TYPE_CLASS = "class";

    /**
     * 分组
     */
    public static final String TYPE_GROUP = "group";
    // 这些字段在 BaseModel 里
    // `id` varchar(32) NOT NULL COMMENT 'id', 自动增长
    //`region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
    //`school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
    //`create_time` datetime DEFAULT NULL COMMENT '创建时间',
    //`update_time` datetime DEFAULT NULL COMMENT '更新时间',
    //`logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',

    //`pid` varchar(32) DEFAULT NULL COMMENT '父节点id',
    private String pid;

    //`tree_path` varchar(255) DEFAULT NULL COMMENT '结构关系',
    private String treePath;

    //`type` varchar(32) DEFAULT NULL COMMENT '班级，分组',
    private String type;

    //`grade_level` varchar(64) DEFAULT NULL COMMENT '年级编码，比如一年级 就为1',
    private Integer gradeLevel;

    //`group_name` varchar(64) DEFAULT NULL COMMENT '班级或者分组名称',
    private String groupName;

    //`group_code` varchar(32) DEFAULT NULL COMMENT '分组编码',
    private String groupCode;

    //`authority_id` varchar(32) DEFAULT NULL COMMENT '权限组id',
    private String authorityId;

    /**
     * 门禁策略id
     */
    private String accessStrategyId;

    //`description` varchar(255) DEFAULT NULL COMMENT '描述',
    @TableField(strategy = FieldStrategy.IGNORED)
    private String description;
}
