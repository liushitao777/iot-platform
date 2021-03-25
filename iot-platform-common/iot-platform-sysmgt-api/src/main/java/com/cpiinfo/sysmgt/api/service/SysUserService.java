package com.cpiinfo.sysmgt.api.service;

import com.cpiinfo.sysmgt.api.model.SysUser;

import java.util.Date;

/**
 * 系统用户服务接口
 * <p>
 * 系统用户服务接口管理模块管理，作为API接口提供其它模块与系统管理模块交互
 *
 * @author yabo
 * @version 1.0
 * @date  2019/11/11
 * @apiNote 初始版本
 */
public interface SysUserService {

	public SysUser queryUserByName(String userName);

	public SysUser queryMobileUserByName(String userName);

    void updateUser(String username, SysUser newUser);

    void reloadFailsCount();

    void updateMobileUserLoginTime(String userCode, Long totalLoginTime, Date lastLoginTime);
}
