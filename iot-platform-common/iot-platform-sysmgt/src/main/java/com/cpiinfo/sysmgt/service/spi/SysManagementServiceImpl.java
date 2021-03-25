package com.cpiinfo.sysmgt.service.spi;

import com.cpiinfo.sysmgt.api.model.SysOrg;
import com.cpiinfo.sysmgt.api.model.SysResource;
import com.cpiinfo.sysmgt.api.model.SysRole;
import com.cpiinfo.sysmgt.api.model.SysUser;
import com.cpiinfo.sysmgt.api.service.SysResourceService;
import com.cpiinfo.sysmgt.api.service.SysUserService;
import com.cpiinfo.sysmgt.dao.MenuMapper;
import com.cpiinfo.sysmgt.dao.RoleMapper;
import com.cpiinfo.sysmgt.dao.UserMapper;
import com.cpiinfo.sysmgt.entity.Role;
import com.cpiinfo.sysmgt.entity.User;
import com.cpiinfo.sysmgt.entity.ZNode;
import com.cpiinfo.sysmgt.service.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysManagementServiceImpl implements SysUserService, SysResourceService {
	
	@Autowired
    UserMapper userMapper;
	@Autowired
    RoleMapper roleMapper;
	@Autowired
    MenuMapper menuMapper;
	@Autowired
    OrganizationService organizationService;

	@Override
	public SysUser queryUserByName(String userName) {
		SysUser sysUser = null;
		User user = userMapper.queryUserByCode(userName);
		if(user != null) {
			sysUser = new SysUser();
			BeanUtils.copyProperties(user, sysUser);
			sysUser.setUserName(user.getUsername());
			sysUser.setUserPwd(user.getUserpwd());
			sysUser.setUserId(user.getId());
			List<Role> roles = roleMapper.queryRolesByUserCode(user.getUserCode());
			if(roles != null) {
				List<SysRole> userRoles = roles.stream().map(e -> {
					SysRole r = new SysRole();
					BeanUtils.copyProperties(e, r);
					return r;
				}).collect(Collectors.toList());
				sysUser.setUserRoles(userRoles);
			}
			
			List<ZNode> orgs = organizationService.queryUserAllOrg(user.getUserCode());
			if(orgs != null) {
				List<SysOrg> userOrgs = orgs.stream().map(e -> {
					SysOrg uOrg = new SysOrg();
					return uOrg;
				}).collect(Collectors.toList());
				sysUser.setUserOrgs(userOrgs);
				if(userOrgs.size() > 0) {
					SysOrg uOrg = userOrgs.get(0);
					sysUser.setOrgCode(uOrg.getOrgCode());
					sysUser.setOrgName(uOrg.getOrgName());
					sysUser.setOrgType(uOrg.getOrgType());
				}
			}
		}
		return sysUser;
	}

	@Override
	public SysUser queryMobileUserByName(String userName) {
		SysUser sysUser = null;
		User user = userMapper.queryMobileUserByuserName(userName);
		if(user != null) {
			sysUser = new SysUser();
			BeanUtils.copyProperties(user, sysUser);
			sysUser.setUserName(user.getUsername());
			sysUser.setUserPwd(user.getUserpwd());
			List<Role> roles = roleMapper.queryRolesByUserCode(user.getUserCode());
			if(roles != null) {
				List<SysRole> userRoles = roles.stream().map(e -> {
					SysRole r = new SysRole();
					BeanUtils.copyProperties(e, r);
					return r;
				}).collect(Collectors.toList());
				sysUser.setUserRoles(userRoles);
			}

			List<ZNode> orgs = organizationService.queryUserAllOrg(user.getUserCode());
			if(orgs != null) {
				List<SysOrg> userOrgs = orgs.stream().map(e -> {
					SysOrg uOrg = new SysOrg();
					return uOrg;
				}).collect(Collectors.toList());
				sysUser.setUserOrgs(userOrgs);
				if(userOrgs.size() > 0) {
					SysOrg uOrg = userOrgs.get(0);
					sysUser.setOrgCode(uOrg.getOrgCode());
					sysUser.setOrgName(uOrg.getOrgName());
					sysUser.setOrgType(uOrg.getOrgType());
				}
			}
		}
		return sysUser;
	}

	@Override
	public void updateUser(String username, SysUser newUser) {
		User user = new User();
		BeanUtils.copyProperties(newUser,user);
		user.setId(newUser.getUserId());
		userMapper.updateUser(user);
	}

	@Override
	public void reloadFailsCount() {
		userMapper.reloadFailsCount();
	}

	@Override
	public void updateMobileUserLoginTime(String userCode, Long totalLoginTime, Date lastLoginTime) {
		userMapper.updateMobileUserLoginTime(userCode,totalLoginTime,lastLoginTime);
	}

	@Override
	public List<SysResource> queryResourcesByUser(String userName, String systemCodes, String includeButton) {
		String[] sysCodes = systemCodes == null || "".equals(systemCodes) ? null : systemCodes.split(",");
		return menuMapper.queryResourceByUserCode(userName, sysCodes, includeButton);
	}

	@Override
	public List<SysResource> getButtonByMenusUrl(String id) {
		return menuMapper.getButtonByMenusUrl(id);
	}
}
