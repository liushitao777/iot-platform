INSERT INTO `sys_org` (`ID`, `ORG_CODE`, `ORG_NAME`, `SHORT_NAME`, `MAIN_INDUSTRY`, `org_type`, `PARENT_CODE`, `PARENT_NAME`, `ORG_PATH`, `ORG_COUNTRY`, `S_STATE`, `SUB_COUNT`, `L_LEVEL`, `SHORT_CODE`, `CAPACITY`, `IS_NORMAL`, `LOCATION`, `PROVINCE`, `LINKMAN`, `TELEPHONE`, `COORDINATE_SYS`, `LONGITUDE`, `LATITUDE`, `ACCESS_SITUATION`, `is_production`, `equipment_capacity`, `design_capacity`) VALUES ('2f5fbd729c094bacb6fb7d13be3f1000', '101325', '重庆中电自能科技有限公司', '中电科技', 'FG', 'L2ORG', '', '', '/101325', '中国', '1', '78', '2', '101325', '4027.46', '1', '重庆市九龙坡区', '重庆市', '', '', '95°19′9\"，40°38′22\"', '126.233552', '43.811092', '1', '1', '0.00', '201.00');
INSERT INTO `sys_department` (`id`, `depart_name`, `org_name`, `org_code`, `depart_status`) VALUES ('16bf99c960b2490b9d2916cac0db2000', '临时部门', '中电科技', '101325', '0');

INSERT INTO `sys_user` (`id`, `user_code`, `user_name`, `user_pwd`, `sex`, `phone`, `email`, `duties`, `s_state`) VALUES ('116edacff41a42859c17cb70b2fed728', 'admin', '系统管理员', '$2a$10$uix8Xn/IiI2pwSqxUev7U.weZZUk2Vl0Fg7NHuuHKT5L.rVqAmXA.', '0', '13000000000', 'admin@jyjt.lc', 'N/A', '1');
INSERT INTO `sys_depart_user` (`id`, `depart_id`, `user_id`, `user_code`) VALUES ('9f71d2029d8c4fdeb947efb3f557068c', '16bf99c960b2490b9d2916cac0db2000', '116edacff41a42859c17cb70b2fed728', 'admin');

INSERT INTO `sys_role` (`role_id`, `role_code`, `role_name`, `role_desc`, `update_user`, `update_time`, `s_state`, `root`, `l_level`, `unit`) VALUES ('888950a1f87e4165a4f6270b07212840', 'ADMIN', '系统管理员', '系统管理员', '', '2018-10-12 16:50:43', '1', '1', '1', '101325');

INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES ('4d762c74db8947d7a19be5c63803eeba', '116edacff41a42859c17cb70b2fed728', '888950a1f87e4165a4f6270b07212840');

INSERT INTO `sys_resource` (`ID`, `rname`, `sort`, `parent_id`, `url`, `loadfile`, `icon`, `update_user`, `update_time`, `s_state`) VALUES ('AEC764A6A7B74F7D884814DDA36EE5BD', '首页', '1', '0', '', '', '', '', '2018-11-17 10:01:35', '1');
INSERT INTO `sys_resource` (`ID`, `rname`, `sort`, `parent_id`, `url`, `loadfile`, `icon`, `update_user`, `update_time`, `s_state`) VALUES ('FC98A132AA5648CF8D6C01BE2BB841BD', '首页', '1', 'AEC764A6A7B74F7D884814DDA36EE5BD', '/views/index/index.html', '', '', '', '2018-11-17 10:03:48', '1');
INSERT INTO `sys_resource` (`ID`, `rname`, `sort`, `parent_id`, `url`, `loadfile`, `icon`, `update_user`, `update_time`, `s_state`) VALUES ('EF359244FF9A49D4A821CEFFBAADA079', '组织管理', '5', 'A20B29F594C641959F3AC7CE73287083', '/org/orglist.html', '', '', '', '2018-10-12 16:41:39', '1');
INSERT INTO `sys_resource` (`ID`, `rname`, `sort`, `parent_id`, `url`, `loadfile`, `icon`, `update_user`, `update_time`, `s_state`) VALUES ('676D44C8B6474C4C86FC838AD77F1D60', '人员管理', '10', 'A20B29F594C641959F3AC7CE73287083', '/user/main.html', '', '', '', '2018-10-12 16:42:22', '1');
INSERT INTO `sys_resource` (`ID`, `rname`, `sort`, `parent_id`, `url`, `loadfile`, `icon`, `update_user`, `update_time`, `s_state`) VALUES ('AD3C6645F424435AA6AA3CD1B2C09A1C', '资源管理', '15', 'A20B29F594C641959F3AC7CE73287083', '/resource/main.html', '', '', '', '2018-10-12 16:42:13', '1');
INSERT INTO `sys_resource` (`ID`, `rname`, `sort`, `parent_id`, `url`, `loadfile`, `icon`, `update_user`, `update_time`, `s_state`) VALUES ('B6FDB48418E24BE88B43AFB6B8A1D51C', '角色管理', '20', 'A20B29F594C641959F3AC7CE73287083', '/role/rolelist.html', '', '', '', '2018-10-12 16:42:03', '1');
INSERT INTO `sys_resource` (`ID`, `rname`, `sort`, `parent_id`, `url`, `loadfile`, `icon`, `update_user`, `update_time`, `s_state`) VALUES ('D8A2AEE8FE75489981BA29016D494F7B', '部门管理', '25', 'A20B29F594C641959F3AC7CE73287083', '/depart/main.html', '', '', '', '2018-10-12 16:41:52', '1');
INSERT INTO `sys_resource` (`ID`, `rname`, `sort`, `parent_id`, `url`, `loadfile`, `icon`, `update_user`, `update_time`, `s_state`) VALUES ('A20B29F594C641959F3AC7CE73287083', '系统管理', '35', '0', '', '', '', '', '2018-11-17 10:42:47', '1');

INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `opt_id`) VALUES ('0cf84e8c7fd9490f80fe371e5421d68d', '888950a1f87e4165a4f6270b07212840', 'D8A2AEE8FE75489981BA29016D494F7B', '');
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `opt_id`) VALUES ('3881d9096dbe449cbbccf55d698dd3d6', '888950a1f87e4165a4f6270b07212840', 'FC98A132AA5648CF8D6C01BE2BB841BD', '');
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `opt_id`) VALUES ('6b0049e4131144b09c9cdc6a98a947e1', '888950a1f87e4165a4f6270b07212840', 'B6FDB48418E24BE88B43AFB6B8A1D51C', '');
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `opt_id`) VALUES ('ab8ab72beecf438f890e169f0a673d71', '888950a1f87e4165a4f6270b07212840', 'EF359244FF9A49D4A821CEFFBAADA079', '');
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `opt_id`) VALUES ('cd2bdd2eae1b4384b1c790dbbaf70f3f', '888950a1f87e4165a4f6270b07212840', 'A20B29F594C641959F3AC7CE73287083', '');
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `opt_id`) VALUES ('cdf827932f2a4e8588b025dc318407d1', '888950a1f87e4165a4f6270b07212840', 'AD3C6645F424435AA6AA3CD1B2C09A1C', '');
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `opt_id`) VALUES ('e275b9f047ac4a6fb324ceff8ba9e0bc', '888950a1f87e4165a4f6270b07212840', 'AEC764A6A7B74F7D884814DDA36EE5BD', '');
INSERT INTO `sys_role_resource` (`id`, `role_id`, `resource_id`, `opt_id`) VALUES ('e5dfc74f51a04d49ac2eea39415458a5', '888950a1f87e4165a4f6270b07212840', '676D44C8B6474C4C86FC838AD77F1D60', '');


INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('45042e32c0fd11e8aee2005056t576a0', '新增', 'INSERT');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('45042e32c0fd11e8aee2005056t576a1', '复制', 'COPY');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('49b270c3c0fd11e8aee2005056a076a1', '提交', 'SUBMIT');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('4c72b8f6c0fd11e8aee2005056a076a1', '审核', 'REVIEWED');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('4f61408fc0fd11e8aee2005056a076a1', '修改', 'REWIRTE');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('52e34731c0fd11e8aee2005056a076a1', '导出', 'EXPORT');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('52e34731c0fd11e8aee2005056a076f7', '批准', 'APPROVE');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('52e34731c0fd11e8aee2005056a086a1', '签发', 'SIGN');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('52e34731c0fd11e8aee2005056a086a4', '关闭', 'CLOSED');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('52e34731c0fd11e8aee6005056a086a1', '下发', 'FORWARD');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('52e34731c0fd11e8aee6005356a086a1', '退回', 'BACK');
INSERT INTO `sys_operate` (`id`, `name`, `code`) VALUES ('a112c0aa510611e111f0005056a076a1', '删除', 'DELETE');
