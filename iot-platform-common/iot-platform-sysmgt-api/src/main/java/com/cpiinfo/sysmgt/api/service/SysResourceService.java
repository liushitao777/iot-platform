package com.cpiinfo.sysmgt.api.service;

import com.cpiinfo.sysmgt.api.model.SysResource;

import java.util.List;

/**
 * 系统资源服务接口
 * <p>
 * 系统资源服务接口管理模块管理，作为API接口提供其它模块与系统管理模块交互
 *
 * @author yabo
 * @version 1.0
 * @date  2019/11/11
 * @apiNote 初始版本
 */
public interface SysResourceService {

	public List<SysResource> queryResourcesByUser(String userName, String systemCodes, String includeButton);

	List<SysResource> getButtonByMenusUrl(String id);
}
