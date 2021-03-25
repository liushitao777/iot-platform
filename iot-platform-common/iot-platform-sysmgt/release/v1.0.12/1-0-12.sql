alter table `sys_user` add `fixed_value` varchar(1) NULL DEFAULT '0' COMMENT '是否固定 1:固定 0:不固定';
alter table `sys_role` add `fixed_value` varchar(1) NULL DEFAULT '0' COMMENT '是否固定 1:固定 0:不固定';
INSERT INTO `sys_user` VALUES ('001', 'administrator', '系统管理员', '$2a$10$2sNEkryuaZvTPEZ8ScXlVOO338yg./t9RKQ2VuyEke9CPPS.SBwJC', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '2021-01-15 09:43:52', NULL, NULL, NULL, 1, '2021-01-15 09:44:07', 0, NULL, NULL, '2021-01-14 09:44:14', 0, NULL, NULL, '1');
INSERT INTO `sys_user` VALUES ('002', 'comptoller', '审计员', '$2a$10$2sNEkryuaZvTPEZ8ScXlVOO338yg./t9RKQ2VuyEke9CPPS.SBwJC', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '2021-01-15 09:45:16', NULL, NULL, NULL, 1, '2021-01-15 09:45:20', 0, NULL, NULL, '2021-01-15 09:45:28', 0, NULL, NULL, '1');
INSERT INTO `sys_user` VALUES ('003', 'security', '安全管理员', '$2a$10$2sNEkryuaZvTPEZ8ScXlVOO338yg./t9RKQ2VuyEke9CPPS.SBwJC', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '2021-01-15 09:46:49', NULL, NULL, NULL, 1, '2021-01-15 09:46:51', 0, NULL, NULL, '2021-01-15 09:46:57', 0, NULL, NULL, '1');
update  `sys_user` set user_name ='后台管理员' where id ='116edacff41a42859c17cb70b2fed728';
update  `sys_role` set role_name ='后台管理员' where id ='888950a1f87e4165a4f6270b07212840';
delete from `sys_role_resource` where role_id ='888950a1f87e4165a4f6270b07212840' and resource_id = '42005b31f09e4255b79e26124abb551c';

INSERT INTO `sys_user_role` VALUES ('001', '001', '001');
INSERT INTO `sys_user_role` VALUES ('002', '002', '002');
INSERT INTO `sys_user_role` VALUES ('003', '003', '003');

INSERT INTO `sys_role` VALUES ('001', 'administrator', '系统管理员', '系统管理员', NULL, '2021-01-15 09:29:03', '1', '1', 1, NULL, '1');
INSERT INTO `sys_role` VALUES ('002', 'comptoller', '审计员', '审计员', NULL, '2021-01-15 09:29:03', '1', '1', 1, NULL, '1');
INSERT INTO `sys_role` VALUES ('003', 'safety_manager', '安全管理员', '安全管理员', NULL, '2021-01-15 09:29:03', '1', '1', 1, NULL, '1');

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
