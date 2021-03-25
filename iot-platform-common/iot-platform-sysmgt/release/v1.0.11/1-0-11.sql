alter table `sys_user` add `active` tinyint NULL DEFAULT 0 COMMENT '账户是否锁定';
alter table `sys_user` add `last_change_password` datetime(0) NULL DEFAULT NULL COMMENT '最后一次修改密码时间';
alter table `sys_user` add `fails_count` int NULL DEFAULT NULL COMMENT '失败登录次数';
alter table `sys_user` add `user_number` int NULL DEFAULT NULL COMMENT '场站内部人员编号';
alter table `sys_user` add `guard_card_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '门禁卡号';
alter table `sys_user` add `lock_end_time` datetime(0) NULL DEFAULT NULL COMMENT '账号锁定截止时间';
alter table `sys_user` add `is_first_login` tinyint NULL DEFAULT 1 COMMENT '是否首次登陆系统 1:是 0否';