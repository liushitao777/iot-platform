package com.cpiinfo.authnz.service;

import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.sysmgt.api.model.SysResource;

import java.util.List;

public interface UserResourceService {
	
	/**
     * 加载用户有权限的菜单列表
     * @param includeButtons - 回传的数据中是否包括界面上用户有权限的按钮，默认不包含
     * @return
     */
    ServiceResponse loadUserMenus(String systemCode, String includeButtons ,Boolean judgeUser);
    
    /**
     * 查询用户有权限的资源列表
     * @param userName
     * @param includeButtons
     * @return
     */
	List<SysResource> loadUserResources(String userName, String includeButtons);

    List<SysResource> getButtonByMenusUrl(String id);
}
