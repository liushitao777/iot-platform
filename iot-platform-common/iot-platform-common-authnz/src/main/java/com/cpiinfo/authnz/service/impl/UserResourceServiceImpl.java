package com.cpiinfo.authnz.service.impl;

import com.cpiinfo.authnz.model.MenuItem;
import com.cpiinfo.authnz.service.UserResourceService;
import com.cpiinfo.authnz.session.SessionUtils;
import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.sysmgt.api.model.SysResource;
import com.cpiinfo.sysmgt.api.model.SysUser;
import com.cpiinfo.sysmgt.api.service.SysResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("userResourceService")
public class UserResourceServiceImpl implements UserResourceService {
    private static final Logger logger = LoggerFactory.getLogger(UserResourceServiceImpl.class);
	@Autowired
	private SysResourceService sysResourceService;

	@Value("${com.cpiinfo.iot.sys-code:}")
	private String systemCodes;

    @Value("${com.cpiinfo.iot.user-manage-resource-id:}")
    private String userManageResourceId;

	@Override
	public ServiceResponse loadUserMenus(String systemCode, String includeButtons, Boolean judgeUser) {
		SysUser sessionUser = SessionUtils.getSessionUserInfo();
        String loginUser=null;
		if(judgeUser){
            loginUser =sessionUser == null ? null : sessionUser.getUserCode();
        }
        if(systemCode == null || "".equals(systemCode)) {
        	systemCode = systemCodes;
        }
        List<SysResource> menuRes = sysResourceService.queryResourcesByUser(loginUser, systemCode, includeButtons);
        //过滤掉系统管理的资源
        if(!judgeUser){
            menuRes =menuRes.stream().filter(e->  !userManageResourceId.equals(e.getResId())).collect(Collectors.toList());
        }
         List<MenuItem> menus = new ArrayList<MenuItem>();
        if(menuRes != null && menuRes.size() > 0){
            Collections.sort(menuRes, new Comparator<SysResource>() {
                @Override
                public int compare(SysResource o1, SysResource o2) {
                    String pid1 = o1.getParentId() == null ? "" : o1.getParentId();
                    String pid2 = o2.getParentId() == null ? "" : o2.getParentId();
                    int result = pid1.compareTo(pid2);
                    if(result == 0){
                        int s1 = o1.getSort() == null ? 0 : o1.getSort();
                        int s2 = o2.getSort() == null ? 0 : o2.getSort();
                        return (int) (s1 - s2);
                    }
                    return result;
                }
            });
            Map<String, MenuItem> mapMenu = new HashMap<>();
            for(SysResource m : menuRes){
                MenuItem item = new MenuItem();
                item.setId(m.getResId().toString());
                item.setName(m.getResName());
                item.setPid(m.getParentId());
                item.setIcon(m.getIcon());
                item.setUrl(m.getResUrl());
                item.setIcon(m.getIcon());
                item.setSort(m.getSort());
                item.setType(m.getResType() == null ? "0" : m.getResType());
                item.setPermissions("");
                item.setPageCode(m.getPageCode());
                item.setNameCode(m.getResNameCode());
                mapMenu.put(item.getId(), item);
            }
            for(SysResource m : menuRes){
                String pid = m.getParentId();
                MenuItem item = mapMenu.get(m.getResId());
                if(pid == null || pid == "" || "0".equals(pid)){
                    menus.add(item);
                }
                else{
                    MenuItem pItem = mapMenu.get(pid.toString());
                    if(pItem != null) {
	                    List<MenuItem> children = pItem.getChildren();
	                    if(children == null){
	                        children = new ArrayList<>();
	                        pItem.setChildren(children);
	                    }
	                    children.add(item);
                    }
                }
            }
        }
        List<MenuItem> menusF = new ArrayList<>();
        List<MenuItem> menusB = new ArrayList<>();
        for (MenuItem menu : menus) {
            if("front".equals(menu.getPageCode()))
                    menusF.add(menu);
            else
                menusB.add(menu);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("front",menusF);
        map.put("back",menusB);
        return new ServiceResponse(true, "", map.size(), map);
	}
	
	@Override
	public List<SysResource> loadUserResources(String userName, String includeButtons) {
		return sysResourceService.queryResourcesByUser(userName, systemCodes, includeButtons); 
    }

    @Override
    public List<SysResource> getButtonByMenusUrl(String id) {
        return sysResourceService.getButtonByMenusUrl(id);
    }
}
