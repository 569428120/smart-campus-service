package com.xzp.smartcampus.human.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xzp.smartcampus.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/*CREATE TABLE `tb_human_ser_staff` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
  `school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',
  `group_id` varchar(32) NOT NULL COMMENT '分组id',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `user_type` varchar(64) NOT NULL COMMENT '用户类型',
  `user_name` varchar(64) NOT NULL COMMENT '用户登录名称',
  `user_password` varchar(128) NOT NULL COMMENT '密码，sha256编码',
  `user_identity` varchar(255) NOT NULL COMMENT '身份证号码',
  `user_job_code` varchar(255) NOT NULL COMMENT '工号',
  `address` varchar(255) DEFAULT NULL COMMENT '住址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职工表';
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_human_ser_student")
public class StaffModel extends BaseModel {
    // 这些字段在 BaseModel 里
    // `id` varchar(32) NOT NULL COMMENT 'id', 自动增长
    //`region_id` varchar(32) DEFAULT NULL COMMENT '所属教育局',
    //`school_id` varchar(32) DEFAULT NULL COMMENT '所属学校',
    //`create_time` datetime DEFAULT NULL COMMENT '创建时间',
    //`update_time` datetime DEFAULT NULL COMMENT '更新时间',
    //`logic_del` tinyint(2) DEFAULT NULL COMMENT '逻辑删除',

    //`group_id` varchar(32) NOT NULL COMMENT '分组id',
    private String groupId;
    //`name` varchar(64) NOT NULL COMMENT '名称',
    private String name;

    //`user_type` varchar(64) NOT NULL COMMENT '用户类型',
    private String userType;

    //`user_name` varchar(64) NOT NULL COMMENT '用户登录名称',
    private String userName;

    //`user_password` varchar(128) NOT NULL COMMENT '密码，sha256编码',
    private String userPassword;

    //`user_identity` varchar(255) NOT NULL COMMENT '身份证号码',
    private String userIdentity;

    //`user_job_code` varchar(255) NOT NULL COMMENT '工号',
    private String userJobCode;

    //`address` varchar(255) DEFAULT NULL COMMENT '住址',
    private String address;

}
