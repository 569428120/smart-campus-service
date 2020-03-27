/*
 Navicat Premium Data Transfer

 Source Server         : 106.13.204.18
 Source Server Type    : PostgreSQL
 Source Server Version : 100012
 Source Host           : 106.13.204.18:5432
 Source Catalog        : db_smart_campus
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100012
 File Encoding         : 65001

 Date: 27/03/2020 19:08:52
*/


-- ----------------------------
-- Table structure for tb_access_ser_car_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_access_ser_car_record";
CREATE TABLE "public"."tb_access_ser_car_record" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "user_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_code" varchar(255) COLLATE "pg_catalog"."default",
  "user_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "strategy_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "strategy_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "device_id" varchar(32) COLLATE "pg_catalog"."default",
  "in_or_out" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."user_name" IS '用户名称';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."user_code" IS '用户证件号码';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."user_type" IS '用户类型';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."strategy_type" IS '策略类型：出入策略，审核';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."strategy_id" IS '策略id，审核id';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."device_id" IS '设备id';
COMMENT ON COLUMN "public"."tb_access_ser_car_record"."in_or_out" IS '进还是出 in out';
COMMENT ON TABLE "public"."tb_access_ser_car_record" IS '车辆进出表';

-- ----------------------------
-- Table structure for tb_access_ser_control
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_access_ser_control";
CREATE TABLE "public"."tb_access_ser_control" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "user_group_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "user_group_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "strategy_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "control_desc" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_access_ser_control"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_access_ser_control"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_access_ser_control"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_access_ser_control"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_access_ser_control"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_access_ser_control"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_access_ser_control"."user_group_type" IS '用户组类型，学生，职工等';
COMMENT ON COLUMN "public"."tb_access_ser_control"."user_group_id" IS '用户组id';
COMMENT ON COLUMN "public"."tb_access_ser_control"."strategy_id" IS '策略id';
COMMENT ON COLUMN "public"."tb_access_ser_control"."control_desc" IS '描述';
COMMENT ON TABLE "public"."tb_access_ser_control" IS '给学生职工设置进出权限的表';

-- ----------------------------
-- Table structure for tb_access_ser_personnel_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_access_ser_personnel_record";
CREATE TABLE "public"."tb_access_ser_personnel_record" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "user_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_code" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "strategy_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "strategy_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "mode" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "mode_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "device_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "in_or_out" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "in_or_out_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."user_id" IS '人员id';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."user_name" IS '人员名称';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."user_code" IS '证件号码';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."user_type" IS '用户类型';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."strategy_type" IS '策略类型';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."strategy_id" IS '策略id，如审核id，进出策略的id';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."mode" IS '验证方式，刷脸，刷卡';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."mode_id" IS '关联卡，脸特征数据的id';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."device_id" IS '设备id';
COMMENT ON COLUMN "public"."tb_access_ser_personnel_record"."in_or_out" IS '进还是出  in || out';

-- ----------------------------
-- Table structure for tb_access_ser_strategy
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_access_ser_strategy";
CREATE TABLE "public"."tb_access_ser_strategy" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "strategy_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "strategy_status" varchar(32) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_access_ser_strategy"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_access_ser_strategy"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_access_ser_strategy"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_access_ser_strategy"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_access_ser_strategy"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_access_ser_strategy"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_access_ser_strategy"."strategy_name" IS '策略名称';
COMMENT ON COLUMN "public"."tb_access_ser_strategy"."strategy_status" IS '状态，英文单词';
COMMENT ON COLUMN "public"."tb_access_ser_strategy"."description" IS '描述';
COMMENT ON TABLE "public"."tb_access_ser_strategy" IS '门禁策略表';

-- ----------------------------
-- Table structure for tb_access_ser_strategy_time
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_access_ser_strategy_time";
CREATE TABLE "public"."tb_access_ser_strategy_time" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "strategy_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "date_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "start_time" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "end_time" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."strategy_id" IS '策略id';
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."date_type" IS '日期类型，节假日，工作日，无限制';
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."start_time" IS '开始时间，时分秒';
COMMENT ON COLUMN "public"."tb_access_ser_strategy_time"."end_time" IS '结束时间，时分秒';

-- ----------------------------
-- Table structure for tb_flow_ser_access_flow
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_flow_ser_access_flow";
CREATE TABLE "public"."tb_flow_ser_access_flow" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "flow_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "start_time" timestamp(6) NOT NULL,
  "end_time" timestamp(6) NOT NULL,
  "is_car" int2,
  "car_number" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."flow_id" IS '流程id';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."start_time" IS '开始时间 精确到时分';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."end_time" IS '结束时间';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."is_car" IS '是否车辆';
COMMENT ON COLUMN "public"."tb_flow_ser_access_flow"."car_number" IS '车牌号';
COMMENT ON TABLE "public"."tb_flow_ser_access_flow" IS '门禁申请电子流';

-- ----------------------------
-- Table structure for tb_flow_ser_flow_pool
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_flow_ser_flow_pool";
CREATE TABLE "public"."tb_flow_ser_flow_pool" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "originator_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "originator_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "originator_code" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "applicant_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "applicant_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "applicant_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "applicant_code" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "steps" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "curr_step" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "examine_status" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "flow_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "service_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."originator_id" IS '发起人id';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."originator_name" IS '发起人名称';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."originator_code" IS '发起人证件号码';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."applicant_type" IS '申请人类型';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."applicant_id" IS '申请人id';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."applicant_name" IS '申请人名称';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."applicant_code" IS '申请人证件号码';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."steps" IS '步骤id##步骤id';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."curr_step" IS '当前步骤的id';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."examine_status" IS '状态，待审批，撤回，审核中，审核完成，审核不同意';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."flow_type" IS '流程类型，门禁申请，学生请假，职工请假，忘打卡等等';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_pool"."service_id" IS '业务id';
COMMENT ON TABLE "public"."tb_flow_ser_flow_pool" IS '电子流程主表';

-- ----------------------------
-- Table structure for tb_flow_ser_flow_step
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_flow_ser_flow_step";
CREATE TABLE "public"."tb_flow_ser_flow_step" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "step_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "opinion" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "handle_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "handle_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "handle_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "handle_code" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "handle_status" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."step_name" IS '步骤名称';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."opinion" IS '审核内容';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."handle_type" IS '处理人类型';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."handle_id" IS '处理人id';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."handle_name" IS '处理人名称';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."handle_code" IS '处理人证件号码';
COMMENT ON COLUMN "public"."tb_flow_ser_flow_step"."handle_status" IS '状态，默认等待处理，处理中，处理完成，取消';

-- ----------------------------
-- Table structure for tb_human_ser_staff
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_human_ser_staff";
CREATE TABLE "public"."tb_human_ser_staff" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "group_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_type" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_password" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "user_identity" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_job_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "address" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_human_ser_staff"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."group_id" IS '分组id';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."name" IS '名称';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."user_type" IS '用户类型';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."user_name" IS '用户登录名称';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."user_password" IS '密码，sha256编码';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."user_identity" IS '身份证号码';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."user_job_code" IS '工号';
COMMENT ON COLUMN "public"."tb_human_ser_staff"."address" IS '住址';
COMMENT ON TABLE "public"."tb_human_ser_staff" IS '职工表';

-- ----------------------------
-- Table structure for tb_human_ser_staff_contact
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_human_ser_staff_contact";
CREATE TABLE "public"."tb_human_ser_staff_contact" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "mode" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "number" varchar(128) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."type" IS '用户，学生家长，等';
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."user_id" IS '职工id';
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."mode" IS '通讯方式，手机，微信等';
COMMENT ON COLUMN "public"."tb_human_ser_staff_contact"."number" IS '号码';
COMMENT ON TABLE "public"."tb_human_ser_staff_contact" IS '人员联系方式表';

-- ----------------------------
-- Table structure for tb_human_ser_staff_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_human_ser_staff_group";
CREATE TABLE "public"."tb_human_ser_staff_group" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "pid" varchar(32) COLLATE "pg_catalog"."default",
  "tree_path" varchar(255) COLLATE "pg_catalog"."default",
  "group_name" varchar(64) COLLATE "pg_catalog"."default",
  "group_code" varchar(64) COLLATE "pg_catalog"."default",
  "authority_id" varchar(64) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."pid" IS '父节点id';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."tree_path" IS '关联关系';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."group_name" IS '分组名称';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."group_code" IS '分组编码';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."authority_id" IS '权限id';
COMMENT ON COLUMN "public"."tb_human_ser_staff_group"."description" IS '描述';
COMMENT ON TABLE "public"."tb_human_ser_staff_group" IS '职工分组';

-- ----------------------------
-- Table structure for tb_human_ser_staff_to_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_human_ser_staff_to_group";
CREATE TABLE "public"."tb_human_ser_staff_to_group" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "group_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_human_ser_staff_to_group"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_human_ser_staff_to_group"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_human_ser_staff_to_group"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_human_ser_staff_to_group"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_human_ser_staff_to_group"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_human_ser_staff_to_group"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_human_ser_staff_to_group"."group_id" IS '分组id';
COMMENT ON COLUMN "public"."tb_human_ser_staff_to_group"."user_id" IS '用户id';
COMMENT ON TABLE "public"."tb_human_ser_staff_to_group" IS '职工分组和职工关系表';

-- ----------------------------
-- Table structure for tb_human_ser_student
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_human_ser_student";
CREATE TABLE "public"."tb_human_ser_student" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "group_id" varchar(32) COLLATE "pg_catalog"."default",
  "name" varchar(64) COLLATE "pg_catalog"."default",
  "student_code" varchar(64) COLLATE "pg_catalog"."default",
  "address" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_human_ser_student"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_human_ser_student"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_human_ser_student"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_human_ser_student"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_human_ser_student"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_human_ser_student"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_human_ser_student"."group_id" IS '班级id';
COMMENT ON COLUMN "public"."tb_human_ser_student"."name" IS '姓名';
COMMENT ON COLUMN "public"."tb_human_ser_student"."student_code" IS '学号';
COMMENT ON COLUMN "public"."tb_human_ser_student"."address" IS '家庭住址';
COMMENT ON TABLE "public"."tb_human_ser_student" IS '学生表';

-- ----------------------------
-- Table structure for tb_human_ser_student_contact
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_human_ser_student_contact";
CREATE TABLE "public"."tb_human_ser_student_contact" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "student_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "family_type" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "certificate" varchar(255) COLLATE "pg_catalog"."default",
  "occupation" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."student_id" IS '学生id';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."family_type" IS '家庭关系类型，父亲，母亲';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."name" IS '姓名';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."certificate" IS '证件号码';
COMMENT ON COLUMN "public"."tb_human_ser_student_contact"."occupation" IS '职业';
COMMENT ON TABLE "public"."tb_human_ser_student_contact" IS '学生家长表';

-- ----------------------------
-- Table structure for tb_human_ser_student_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_human_ser_student_group";
CREATE TABLE "public"."tb_human_ser_student_group" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "pid" varchar(32) COLLATE "pg_catalog"."default",
  "tree_path" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(32) COLLATE "pg_catalog"."default",
  "grade_name" varchar(64) COLLATE "pg_catalog"."default",
  "grade_level" varchar(64) COLLATE "pg_catalog"."default",
  "group_name" varchar(64) COLLATE "pg_catalog"."default",
  "group_code" varchar(32) COLLATE "pg_catalog"."default",
  "authority_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."pid" IS '父节点id';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."tree_path" IS '结构关系';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."type" IS '班级，分组';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."grade_name" IS '年级名称';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."grade_level" IS '年级编码，比如一年级 就为1';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."group_name" IS '班级或者分组名称';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."group_code" IS '分组编码';
COMMENT ON COLUMN "public"."tb_human_ser_student_group"."authority_id" IS '权限组id';
COMMENT ON TABLE "public"."tb_human_ser_student_group" IS '班级及学生分组表';

-- ----------------------------
-- Table structure for tb_human_ser_student_to_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_human_ser_student_to_group";
CREATE TABLE "public"."tb_human_ser_student_to_group" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "group_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "student_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_human_ser_student_to_group"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_human_ser_student_to_group"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_human_ser_student_to_group"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_human_ser_student_to_group"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_human_ser_student_to_group"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_human_ser_student_to_group"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_human_ser_student_to_group"."group_id" IS '班级id';
COMMENT ON COLUMN "public"."tb_human_ser_student_to_group"."student_id" IS '学生id';
COMMENT ON TABLE "public"."tb_human_ser_student_to_group" IS '班级关联学生表';

-- ----------------------------
-- Table structure for tb_sys_ser_authority
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_sys_ser_authority";
CREATE TABLE "public"."tb_sys_ser_authority" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "template" int2,
  "authority_name" varchar(64) COLLATE "pg_catalog"."default",
  "authority_code" varchar(64) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."template" IS '是否为模板';
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."authority_name" IS '权限名称';
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."authority_code" IS '权限编码';
COMMENT ON COLUMN "public"."tb_sys_ser_authority"."description" IS '描述';
COMMENT ON TABLE "public"."tb_sys_ser_authority" IS '权限表';

-- ----------------------------
-- Table structure for tb_sys_ser_authority_to_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_sys_ser_authority_to_menu";
CREATE TABLE "public"."tb_sys_ser_authority_to_menu" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "group_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."tb_sys_ser_authority_to_menu"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_sys_ser_authority_to_menu"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_sys_ser_authority_to_menu"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_sys_ser_authority_to_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_sys_ser_authority_to_menu"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_sys_ser_authority_to_menu"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_sys_ser_authority_to_menu"."group_id" IS '权限组id';
COMMENT ON COLUMN "public"."tb_sys_ser_authority_to_menu"."menu_id" IS '菜单id';
COMMENT ON TABLE "public"."tb_sys_ser_authority_to_menu" IS '权限关联菜单表';

-- ----------------------------
-- Table structure for tb_sys_ser_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_sys_ser_menu";
CREATE TABLE "public"."tb_sys_ser_menu" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "pid" varchar(32) COLLATE "pg_catalog"."default",
  "tree_path" varchar(255) COLLATE "pg_catalog"."default",
  "app_type" varchar(32) COLLATE "pg_catalog"."default",
  "menu_name" varchar(64) COLLATE "pg_catalog"."default",
  "menu_level" int4,
  "route" varchar(255) COLLATE "pg_catalog"."default",
  "operate_name" varchar(64) COLLATE "pg_catalog"."default",
  "operate_code" varchar(64) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."tree_path" IS '树结构关系';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."app_type" IS '手机，pc';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."menu_level" IS '菜单级别，一级菜单，二级，三级';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."route" IS '路由';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."operate_name" IS '操作名称';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."operate_code" IS '操作编码';
COMMENT ON COLUMN "public"."tb_sys_ser_menu"."description" IS '描述';
COMMENT ON TABLE "public"."tb_sys_ser_menu" IS '菜单表';

-- ----------------------------
-- Table structure for tb_sys_ser_region
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_sys_ser_region";
CREATE TABLE "public"."tb_sys_ser_region" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "pid" varchar(32) COLLATE "pg_catalog"."default",
  "tree_path" varchar(255) COLLATE "pg_catalog"."default",
  "region_name" varchar(255) COLLATE "pg_catalog"."default",
  "education_name" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(525) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_sys_ser_region"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."pid" IS '父节点id';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."tree_path" IS '关联关系';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."region_name" IS '区域名称';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."education_name" IS '教育局名称';
COMMENT ON COLUMN "public"."tb_sys_ser_region"."description" IS '描述';
COMMENT ON TABLE "public"."tb_sys_ser_region" IS '教育局信息表';

-- ----------------------------
-- Table structure for tb_sys_ser_school
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_sys_ser_school";
CREATE TABLE "public"."tb_sys_ser_school" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" varchar(32) COLLATE "pg_catalog"."default",
  "school_id" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "tree_path" varchar(255) COLLATE "pg_catalog"."default",
  "school_name" varchar(128) COLLATE "pg_catalog"."default",
  "school_level" varchar(128) COLLATE "pg_catalog"."default",
  "school_type" varchar(64) COLLATE "pg_catalog"."default",
  "address" varchar(255) COLLATE "pg_catalog"."default",
  "contact" varchar(255) COLLATE "pg_catalog"."default",
  "description" varchar(525) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tb_sys_ser_school"."id" IS 'id';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."region_id" IS '所属教育局';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."school_id" IS '所属学校';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."tree_path" IS '为了以后扩展，教育局有多层';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."school_name" IS '学校名称';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."school_level" IS '完全小学，完全中学，小学&中学';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."school_type" IS '公立，私立';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."address" IS '地址';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."contact" IS '联系方式';
COMMENT ON COLUMN "public"."tb_sys_ser_school"."description" IS '描述';
COMMENT ON TABLE "public"."tb_sys_ser_school" IS '学校数据表';

-- ----------------------------
-- Table structure for tb_test
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_test";
CREATE TABLE "public"."tb_test" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "region_id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "school_id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "logic_del" int2,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "age" int4
)
;

-- ----------------------------
-- Primary Key structure for table tb_access_ser_car_record
-- ----------------------------
ALTER TABLE "public"."tb_access_ser_car_record" ADD CONSTRAINT "tb_access_ser_car_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_access_ser_control
-- ----------------------------
ALTER TABLE "public"."tb_access_ser_control" ADD CONSTRAINT "_copy_21" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_access_ser_personnel_record
-- ----------------------------
ALTER TABLE "public"."tb_access_ser_personnel_record" ADD CONSTRAINT "_copy_20" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_access_ser_strategy
-- ----------------------------
ALTER TABLE "public"."tb_access_ser_strategy" ADD CONSTRAINT "_copy_19" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_access_ser_strategy_time
-- ----------------------------
ALTER TABLE "public"."tb_access_ser_strategy_time" ADD CONSTRAINT "_copy_18" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_flow_ser_access_flow
-- ----------------------------
ALTER TABLE "public"."tb_flow_ser_access_flow" ADD CONSTRAINT "_copy_17" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_flow_ser_flow_pool
-- ----------------------------
ALTER TABLE "public"."tb_flow_ser_flow_pool" ADD CONSTRAINT "_copy_16" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_flow_ser_flow_step
-- ----------------------------
ALTER TABLE "public"."tb_flow_ser_flow_step" ADD CONSTRAINT "_copy_15" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_human_ser_staff
-- ----------------------------
ALTER TABLE "public"."tb_human_ser_staff" ADD CONSTRAINT "_copy_14" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_human_ser_staff_contact
-- ----------------------------
ALTER TABLE "public"."tb_human_ser_staff_contact" ADD CONSTRAINT "_copy_13" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_human_ser_staff_group
-- ----------------------------
ALTER TABLE "public"."tb_human_ser_staff_group" ADD CONSTRAINT "_copy_12" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_human_ser_staff_to_group
-- ----------------------------
ALTER TABLE "public"."tb_human_ser_staff_to_group" ADD CONSTRAINT "_copy_11" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_human_ser_student
-- ----------------------------
ALTER TABLE "public"."tb_human_ser_student" ADD CONSTRAINT "_copy_10" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_human_ser_student_contact
-- ----------------------------
ALTER TABLE "public"."tb_human_ser_student_contact" ADD CONSTRAINT "_copy_9" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_human_ser_student_group
-- ----------------------------
ALTER TABLE "public"."tb_human_ser_student_group" ADD CONSTRAINT "_copy_8" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_human_ser_student_to_group
-- ----------------------------
ALTER TABLE "public"."tb_human_ser_student_to_group" ADD CONSTRAINT "_copy_7" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_sys_ser_authority
-- ----------------------------
ALTER TABLE "public"."tb_sys_ser_authority" ADD CONSTRAINT "_copy_6" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_sys_ser_authority_to_menu
-- ----------------------------
ALTER TABLE "public"."tb_sys_ser_authority_to_menu" ADD CONSTRAINT "_copy_5" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_sys_ser_menu
-- ----------------------------
ALTER TABLE "public"."tb_sys_ser_menu" ADD CONSTRAINT "_copy_4" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_sys_ser_region
-- ----------------------------
ALTER TABLE "public"."tb_sys_ser_region" ADD CONSTRAINT "_copy_3" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_sys_ser_school
-- ----------------------------
ALTER TABLE "public"."tb_sys_ser_school" ADD CONSTRAINT "_copy_2" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tb_test
-- ----------------------------
ALTER TABLE "public"."tb_test" ADD CONSTRAINT "_copy_1" PRIMARY KEY ("id");
