package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/*CREATE TABLE `tb_human_ser_student_contact` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
  `school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',
  `student_id` varchar(32) NOT NULL COMMENT '学生id',
  `family_type` varchar(64) NOT NULL COMMENT '家庭关系类型，父亲，母亲',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `certificate` varchar(255) DEFAULT NULL COMMENT '证件号码',
  `occupation` varchar(64) DEFAULT NULL COMMENT '职业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生家长表';
* */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_student_contact")
public class StudentContactModel extends BaseModel {
    // 这些字段在 BaseModel 里
    // `id` varchar(32) NOT NULL COMMENT 'id', 自动增长
    //`region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
    //`school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
    //`create_time` datetime DEFAULT NULL COMMENT '创建时间',
    //`update_time` datetime DEFAULT NULL COMMENT '更新时间',
    //`logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',

    //`student_id` varchar(32) NOT NULL COMMENT '学生id',
    private String studentId;

    //`family_type` varchar(64) NOT NULL COMMENT '家庭关系类型，父亲，母亲',  relation_type
    private String familyType;

    //`name` varchar(64) NOT NULL COMMENT '姓名',
    private String name;

    //`certificate` varchar(255) DEFAULT NULL COMMENT '证件号码',  identity
    private String contact;
}
