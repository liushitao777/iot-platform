/*
 Navicat Premium Data Transfer

 Source Server         : 97
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 172.30.200.97:3306
 Source Schema         : iotcangzhou

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 24/03/2021 16:08:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

create database `iot_db`;
use `iot_db`;

create user 'iotuser'@'%' identified by 'lmM#qIt.6K';
grant all privileges on iot_db.* to 'iotuser'@'%' with grant option;

-- ----------------------------
-- Table structure for sys_depart_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_depart_user`;
CREATE TABLE `sys_depart_user`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `depart_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `depart_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名称',
  `org_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属单位',
  `org_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '单位编码',
  `depart_status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态0启用1停用',
  `depart_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门编码',
  `parent_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父id 顶层为0',
  `principal` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `postscript` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注信息',
  `parent_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '上级部门名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '上级ID，一级为0',
  `dict_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型',
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典名称',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(0) UNSIGNED NOT NULL COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `mode_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型编码',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_key` tinyint(0) NULL DEFAULT NULL COMMENT '是否是key （1 是）',
  `model_file` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型文件路径',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pid`(`pid`) USING BTREE,
  INDEX `idx_dict_type`(`dict_type`) USING BTREE,
  INDEX `idx_sort`(`sort`) USING BTREE,
  INDEX `idx_del_flag`(`del_flag`) USING BTREE,
  INDEX `idx_create_date`(`create_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作用户',
  `way` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求路径',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '参数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接口名称',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `org_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司编码',
  `org_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司名称',
  `short_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简称',
  `main_industry` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `org_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司类型',
  `parent_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上级公司编码',
  `parent_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '上级公司名称',
  `org_path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `org_country` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `s_state` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司状态',
  `sub_count` int(0) NULL DEFAULT NULL,
  `l_level` int(0) NULL DEFAULT NULL COMMENT '公司等级',
  `short_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `capacity` decimal(18, 2) NULL DEFAULT NULL,
  `jt_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  `is_normal` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '0：停运 1：运行',
  `installed_units` int(0) NULL DEFAULT NULL,
  `reted_voltage` decimal(18, 2) NULL DEFAULT NULL,
  `location` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `province` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省份',
  `linkman` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `coordinate_sys` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `longitude` decimal(18, 6) NULL DEFAULT NULL,
  `latitude` decimal(18, 6) NULL DEFAULT NULL,
  `assets_ratio` decimal(18, 2) NULL DEFAULT NULL,
  `access_situation` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '接入情况(1 是 0 否)',
  `isalignment` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否统调(1 统调 0 非统调)',
  `is_production` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否投产',
  `equipment_capacity` decimal(18, 2) NULL DEFAULT NULL,
  `design_capacity` decimal(18, 2) NULL DEFAULT NULL,
  `is_increase` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `production_time` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '投产日期',
  `intro_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `intro_text` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `is_ts` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '技术监督组织机构区分，1：技术监督显示，2：技术监督不显示',
  `power_capacity` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `power_unit` int(0) NULL DEFAULT NULL COMMENT '发电单元数量',
  `battery_manufacturer` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电池组件厂家',
  `battery_type` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '（机组）电池类型',
  `support_form` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '（叶片直径）支架形式',
  `inverter_manufacture` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '（风机）逆变器厂家',
  `inverter_model` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '（风机）逆变器型号',
  `inverter_capacity` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `first_startup_time` datetime(0) NULL DEFAULT NULL COMMENT '首台投产时间',
  `year_power` decimal(18, 2) NULL DEFAULT NULL COMMENT '年设计值发电量',
  `year_user_hour` decimal(18, 2) NULL DEFAULT NULL COMMENT '年利用小时设计值',
  `electricity_price` decimal(18, 2) NULL DEFAULT NULL COMMENT '电价',
  `actual_capacity` decimal(18, 2) NULL DEFAULT NULL COMMENT '实际容量',
  `full_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `simple_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '记录创建者',
  `create_by_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者姓名',
  `create_at` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改者账号',
  `update_by_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改者姓名',
  `update_at` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `registered_capital` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '注册资本',
  `legal_representative` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '法定代表人',
  `establish_time` datetime(0) NULL DEFAULT NULL COMMENT '机构建立时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `rname` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源名称',
  `res_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源路径',
  `res_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源类型(1:菜单、0:按钮)',
  `sort` int(0) NULL DEFAULT NULL COMMENT '资源序号',
  `parent_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源父ID',
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源链接',
  `loadfile` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源加载js文件，暂不用',
  `icon` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源图标样式',
  `update_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `s_state` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源状态；0：删除，1：正常',
  `system_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '系统名称code\r\n',
  `rname_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源名称code',
  `page_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '页面code',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `update_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者账号',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `s_state` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态',
  `root` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `l_level` int(0) NULL DEFAULT NULL,
  `unit` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fixed_value` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否固定 1:固定 0:不固定',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `role_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `resource_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `opt_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户编码',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `user_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别 1:男 0女',
  `phone` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_type` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '系统角色类型',
  `duties` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '岗位',
  `s_state` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态',
  `unit` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组织名称',
  `unit_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组织id',
  `unit_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组织编码',
  `postscript` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `depart_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `head_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `station_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '场站code',
  `md5_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'md5密文',
  `active` tinyint(0) NULL DEFAULT 0 COMMENT '账户是否锁定',
  `last_change_password` datetime(0) NULL DEFAULT NULL COMMENT '最后一次修改密码时间',
  `fails_count` int(0) NULL DEFAULT NULL COMMENT '失败登录次数',
  `user_number` int(0) NULL DEFAULT NULL COMMENT '场站内部人员编号',
  `guard_card_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '门禁卡号',
  `lock_end_time` datetime(0) NULL DEFAULT NULL COMMENT '账号锁定截止时间',
  `is_first_login` tinyint(0) NULL DEFAULT 1 COMMENT '是否首次登陆系统 1:是 0否',
  `maker_password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '厂商密码',
  `maker_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '厂商用户ID',
  `fixed_value` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否固定 1:固定 0:不固定',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Compact;





INSERT INTO `sys_resource` VALUES ('31b367ba61ab4d349a8d4d46095e8548', '新增用户', NULL, '0', 1, '88ea725fad104441b15c178d27b05266', '', '', '', '', '2018-11-17 10:01:35', '1', NULL, '', 'back');
INSERT INTO `sys_resource` VALUES ('3e8f1b064c524eaea72558295890305a', '编辑', NULL, '0', 1, '88ea725fad104441b15c178d27b05266', '', '', '', '', '2018-11-17 10:01:35', '1', NULL, '', 'back');
INSERT INTO `sys_resource` VALUES ('42005b31f09e4255b79e26124abb551c', '系统管理', NULL, '1', 150, '0', '/userAuth', '', 'assets/indexPage/sysManager', NULL, '2019-09-17 15:51:27', '1', NULL, NULL, 'back');
INSERT INTO `sys_resource` VALUES ('64d4f55dcb4243508d6b24c4dcddefe3', '角色管理', NULL, '1', 34, '42005b31f09e4255b79e26124abb551c', '/userAuth/auth/index', NULL, NULL, NULL, NULL, '1', NULL, NULL, 'back');
INSERT INTO `sys_resource` VALUES ('7471a3022bc34b7e916f6af5e406129b', '删除', NULL, '0', 1, '88ea725fad104441b15c178d27b05266', '', '', '', '', '2018-11-17 10:01:35', '1', NULL, '', 'back');
INSERT INTO `sys_resource` VALUES ('88ea725fad104441b15c178d27b052609', '密码修改', NULL, '1', 33, '42005b31f09e4255b79e26124abb551c', '/userAuth/updatePwd', NULL, NULL, NULL, NULL, '1', NULL, NULL, 'back');
INSERT INTO `sys_resource` VALUES ('88ea725fad104441b15c178d27b05266', '用户管理', NULL, '1', 33, '42005b31f09e4255b79e26124abb551c', '/userAuth/user', NULL, NULL, NULL, NULL, '1', NULL, NULL, 'back');
INSERT INTO `sys_resource` VALUES ('88ea725fad104441b15c178d27b05267', '日志中心', NULL, '1', 33, '42005b31f09e4255b79e26124abb551c', '/userAuth/logs', NULL, NULL, NULL, NULL, '1', NULL, NULL, 'back');
INSERT INTO `sys_resource` VALUES ('88ea725fad104441b15c178d27b05277', '组织机构', NULL, '1', 33, '42005b31f09e4255b79e26124abb551c', '/userAuth/organization', NULL, NULL, NULL, NULL, '1', NULL, NULL, 'back');
INSERT INTO `sys_resource` VALUES ('974456cef816434c8b8d0cd22515ce22', '删除', NULL, '0', 1, '64d4f55dcb4243508d6b24c4dcddefe3', '', '', '', '', '2018-11-17 10:01:35', '1', NULL, '', 'back');
INSERT INTO `sys_resource` VALUES ('9c780bcfa9fd464c8dc92a24c1c9fb4d', '编辑', NULL, '0', 1, '64d4f55dcb4243508d6b24c4dcddefe3', '', '', '', '', '2018-11-17 10:01:35', '1', NULL, '', 'back');
INSERT INTO `sys_resource` VALUES ('AEC764A6A7B74F7D884814DDA36EE5BD', '首页', NULL, '1', 10, '0', '/indexPage', '', 'assets/indexPage/home', '', '2018-11-17 10:01:35', '1', NULL, '', 'back');
INSERT INTO `sys_resource` VALUES ('d23e57451c914e6eae6461ee0ca412c3', '新增角色', NULL, '0', 1, '64d4f55dcb4243508d6b24c4dcddefe3', '', '', '', '', '2018-11-17 10:01:35', '1', NULL, '', 'back');




INSERT INTO `sys_user` VALUES ('001', 'administrator', '系统管理员', '$2a$10$h99QyNQTrGaCUej/rykjIuB5Lu5sbf3ev9fqBPk7L23zwJz1vjOaC', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '2021-01-15 09:43:52', NULL, NULL, NULL, 1, '2021-02-08 16:56:52', 0, NULL, NULL, '2021-01-14 09:44:14', 0, NULL, NULL, '1');
INSERT INTO `sys_user` VALUES ('002', 'comptoller', '审计员', '$2a$10$h99QyNQTrGaCUej/rykjIuB5Lu5sbf3ev9fqBPk7L23zwJz1vjOaC', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '2021-01-15 09:45:16', NULL, NULL, NULL, 1, '2021-02-08 16:56:52', 0, NULL, NULL, '2021-01-15 09:45:28', 0, NULL, NULL, '1');
INSERT INTO `sys_user` VALUES ('003', 'security', '安全管理员', '$2a$10$h99QyNQTrGaCUej/rykjIuB5Lu5sbf3ev9fqBPk7L23zwJz1vjOaC', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '2021-01-15 09:46:49', NULL, NULL, NULL, 1, '2021-02-08 16:56:52', 0, NULL, NULL, '2021-01-15 09:46:57', 0, NULL, NULL, '1');
INSERT INTO `sys_user` VALUES ('116edacff41a42859c17cb70b2fed728', 'admin', '后台管理员', '$2a$10$QVrDamxX.QFlmwxlgzS5b.7Ruk0eg5W5.R93NCp2F0tvcX9RKnG9e', NULL, '13000000000', NULL, '888950a1f87e4165a4f6270b07212840', NULL, '1', NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, 1, '2021-02-08 16:56:52', 0, NULL, NULL, NULL, 1, NULL, '116edacff41a42859c17cb70b2fed728', '0');



INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A01', '001', '42005b31f09e4255b79e26124abb551c', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A02', '001', '88ea725fad104441b15c178d27b05266', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A03', '001', '88ea725fad104441b15c178d27b052609', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A04', '002', '42005b31f09e4255b79e26124abb551c', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A05', '002', '88ea725fad104441b15c178d27b05267', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A06', '003', '42005b31f09e4255b79e26124abb551c', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A07', '003', '64d4f55dcb4243508d6b24c4dcddefe3', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A08', '002', '88ea725fad104441b15c178d27b052609', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A09', '003', '88ea725fad104441b15c178d27b052609', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A10', '003', '9c780bcfa9fd464c8dc92a24c1c9fb4d', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A11', '003', '974456cef816434c8b8d0cd22515ce22', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A14', '001', '974456cef816434c8b8d0cd22515ce22', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A15', '001', '9c780bcfa9fd464c8dc92a24c1c9fb4d', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A16', '001', 'd23e57451c914e6eae6461ee0ca412c3', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A17', '001', '88ea725fad104441b15c178d27b05277', NULL);
INSERT INTO `sys_role_resource` VALUES ('814ca1f7d4574a21ac52393afc849A18', '888950a1f87e4165a4f6270b07212840', 'AEC764A6A7B74F7D884814DDA36EE5BD', NULL);



INSERT INTO `sys_role` VALUES ('001', 'administrator', '系统管理员', '系统管理员', NULL, '2021-01-15 09:29:03', '1', '1', 1, NULL, '1');
INSERT INTO `sys_role` VALUES ('002', 'comptoller', '审计员', '审计员', NULL, '2021-01-15 09:29:03', '1', '1', 1, NULL, '1');
INSERT INTO `sys_role` VALUES ('003', 'safety_manager', '安全管理员', '安全管理员', NULL, '2021-01-15 09:29:03', '1', '1', 1, NULL, '1');
INSERT INTO `sys_role` VALUES ('888950a1f87e4165a4f6270b07212840', 'ADMIN', '后台管理员', '系统管理员', '', '2020-05-13 17:37:33', '1', '1', 1, '101325', '0');


INSERT INTO `sys_user_role` VALUES ('001', '001', '001');
INSERT INTO `sys_user_role` VALUES ('002', '002', '002');
INSERT INTO `sys_user_role` VALUES ('003', '003', '003');
INSERT INTO `sys_user_role` VALUES ('15a25e61b1d34f5bb16702a98c43da10', '116edacff41a42859c17cb70b2fed728', '888950a1f87e4165a4f6270b07212840');
