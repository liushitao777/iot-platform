CREATE TABLE `sys_depart_user` (
  `id` varchar(36) NOT NULL,
  `depart_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `user_code` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_department` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `depart_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `org_name` varchar(50) DEFAULT NULL COMMENT '所属单位',
  `org_code` varchar(36) DEFAULT NULL COMMENT '单位编码',
  `depart_status` varchar(1) DEFAULT NULL COMMENT '状态0启用1停用',
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_dictionary` (
  `category_id` varchar(36) NOT NULL COMMENT '分类id',
  `category_code` varchar(36) DEFAULT NULL COMMENT '代码',
  `category_name` varchar(50) DEFAULT NULL COMMENT '名称',
  `category_order` int(11) DEFAULT NULL COMMENT '顺序',
  `parent_category_code` varchar(36) DEFAULT NULL COMMENT '上级代码',
  `group_code` varchar(36) DEFAULT NULL COMMENT '代码大类',
  `allow_edit` varchar(1) DEFAULT NULL COMMENT '允许编辑',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `category_value` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
);

CREATE TABLE `sys_operate` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `code` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_org` (
  `ID` varchar(36) NOT NULL,
  `ORG_CODE` varchar(36) DEFAULT NULL,
  `ORG_NAME` varchar(128) DEFAULT NULL,
  `SHORT_NAME` varchar(50) DEFAULT NULL,
  `MAIN_INDUSTRY` varchar(36) DEFAULT NULL,
  `org_type` varchar(20) DEFAULT NULL,
  `PARENT_CODE` varchar(36) DEFAULT NULL,
  `PARENT_NAME` varchar(50) DEFAULT NULL,
  `ORG_PATH` varchar(256) DEFAULT NULL,
  `ORG_COUNTRY` varchar(36) DEFAULT NULL,
  `S_STATE` varchar(20) DEFAULT NULL,
  `SUB_COUNT` int(11) DEFAULT NULL,
  `L_LEVEL` int(11) DEFAULT NULL,
  `SHORT_CODE` varchar(36) DEFAULT NULL,
  `CAPACITY` decimal(18,2) DEFAULT NULL,
  `JT_CODE` varchar(36) DEFAULT NULL,
  `SORT` int(11) DEFAULT NULL,
  `IS_NORMAL` varchar(1) DEFAULT NULL COMMENT '0：停运 1：运行',
  `INSTALLED_UNITS` int(11) DEFAULT NULL,
  `RETED_VOLTAGE` decimal(18,2) DEFAULT NULL,
  `LOCATION` varchar(128) DEFAULT NULL,
  `PROVINCE` varchar(36) DEFAULT NULL,
  `LINKMAN` varchar(36) DEFAULT NULL,
  `TELEPHONE` varchar(36) DEFAULT NULL,
  `COORDINATE_SYS` varchar(36) DEFAULT NULL,
  `LONGITUDE` decimal(18,6) DEFAULT NULL,
  `LATITUDE` decimal(18,6) DEFAULT NULL,
  `ASSETS_RATIO` decimal(18,2) DEFAULT NULL,
  `ACCESS_SITUATION` varchar(1) DEFAULT NULL COMMENT '接入情况(1 是 0 否)',
  `ISALIGNMENT` varchar(1) DEFAULT NULL COMMENT '是否统调(1 统调 0 非统调)',
  `is_production` varchar(1) DEFAULT NULL COMMENT '是否投产',
  `equipment_capacity` decimal(18,2) DEFAULT NULL,
  `design_capacity` decimal(18,2) DEFAULT NULL,
  `is_increase` varchar(64) DEFAULT NULL,
  `production_time` varchar(36) DEFAULT NULL COMMENT '投产日期',
  `intro_image` varchar(255) DEFAULT NULL,
  `intro_text` varchar(4000) DEFAULT NULL,
  `is_ts` varchar(1) DEFAULT NULL COMMENT '技术监督组织机构区分，1：技术监督显示，2：技术监督不显示',
  `power_capacity` varchar(255) DEFAULT NULL,
  `power_unit` int(11) DEFAULT NULL COMMENT '发电单元数量',
  `battery_manufacturer` varchar(512) DEFAULT NULL COMMENT '电池组件厂家',
  `battery_type` varchar(512) DEFAULT NULL COMMENT '（机组）电池类型',
  `support_form` varchar(512) DEFAULT NULL COMMENT '（叶片直径）支架形式',
  `inverter_manufacture` varchar(512) DEFAULT NULL COMMENT '（风机）逆变器厂家',
  `inverter_model` varchar(512) DEFAULT NULL COMMENT '（风机）逆变器型号',
  `inverter_capacity` varchar(255) DEFAULT NULL,
  `first_startup_time` datetime DEFAULT NULL COMMENT '首台投产时间',
  `year_power` decimal(18,2) DEFAULT NULL COMMENT '年设计值发电量',
  `year_user_hour` decimal(18,2) DEFAULT NULL COMMENT '年利用小时设计值',
  `electricity_price` decimal(18,2) DEFAULT NULL COMMENT '电价',
  `actual_capacity` decimal(18,2) DEFAULT NULL COMMENT '实际容量',
  `full_name` varchar(50) DEFAULT NULL,
  `simple_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

CREATE TABLE `sys_org_mapping` (
  `id` varchar(36) NOT NULL,
  `org_id` varchar(36) DEFAULT NULL,
  `org_code` varchar(36) DEFAULT NULL,
  `org_name` varchar(128) DEFAULT NULL,
  `opposite_sys` varchar(36) DEFAULT NULL,
  `opposite_org_id` varchar(36) DEFAULT NULL,
  `opposite_org_code` varchar(36) DEFAULT NULL,
  `opposite_org_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='与其它系统的组织机构编码的映射关系';

CREATE TABLE `sys_resource` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `rname` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `res_path` varchar(256) DEFAULT NULL COMMENT '资源路径',
  `res_type` varchar(20) DEFAULT NULL COMMENT '资源类型(菜单、按钮)',
  `sort` int(11) DEFAULT NULL COMMENT '资源序号',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '资源父ID',
  `url` varchar(128) DEFAULT NULL COMMENT '资源链接',
  `loadfile` varchar(128) DEFAULT NULL COMMENT '资源加载js文件，暂不用',
  `icon` varchar(128) DEFAULT NULL COMMENT '资源图标样式',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `s_state` varchar(1) DEFAULT NULL COMMENT '资源状态；0：删除，1：正常',
  PRIMARY KEY (`ID`)
);

CREATE TABLE `sys_resource_oper` (
  `id` varchar(36) NOT NULL,
  `resource_id` varchar(36) DEFAULT NULL,
  `oper_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_role` (
  `role_id` varchar(36) NOT NULL,
  `role_code` varchar(50) DEFAULT NULL,
  `role_name` varchar(128) DEFAULT NULL,
  `role_desc` varchar(256) DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `s_state` varchar(1) DEFAULT NULL,
  `root` varchar(1) DEFAULT NULL,
  `l_level` int(11) DEFAULT NULL,
  `unit` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE `sys_role_resource` (
  `id` varchar(36) NOT NULL,
  `role_id` varchar(36) DEFAULT NULL,
  `resource_id` varchar(36) DEFAULT NULL,
  `opt_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL,
  `user_code` varchar(36) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_pwd` varchar(100) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `phone` varchar(36) DEFAULT NULL,
  `email` varchar(36) DEFAULT NULL,
  `user_type` varchar(20) DEFAULT NULL,
  `org_id` varchar(36) DEFAULT NULL,
  `duties` varchar(36) DEFAULT NULL,
  `s_state` varchar(1) DEFAULT NULL,
  `unit` varchar(128) DEFAULT NULL,
  `unit_id` varchar(36) DEFAULT NULL,
  `unitCode` varchar(36) DEFAULT NULL,
  `unit_code` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `sys_user_role` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `role_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
