package com.cpiinfo.authnz.service.impl;

import com.cpiinfo.authnz.model.CustomUserDetails;
import com.cpiinfo.authnz.service.Constants;
import com.cpiinfo.iot.utility.DateUtils;
import com.cpiinfo.sysmgt.api.model.SysUser;
import com.cpiinfo.authnz.exception.UserLockException;
import com.cpiinfo.sysmgt.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private SysUserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser user =new SysUser();
		String[] split = username.split(",");
		if(split.length==1){
			user=userService.queryUserByName(username);
		}else {
			user=userService.queryMobileUserByName(split[0]);
			if(!user.getLoginType().equals(split[1])){
				throw new UsernameNotFoundException("用户类型和用户名不匹配!");
			}
		}
		if(user != null) {
			if(user.getLockEndTime()!=null){
				Date lockEndTime = user.getLockEndTime();
				if(new Date().compareTo(lockEndTime) == -1){
					throw new UserLockException("用户账户连续输入错误次数过多!已被锁定半小时,请稍后再试!");
				}
			}
			Date lastChangePassword = user.getLastChangePassword();
			if(lastChangePassword!=null){
				Date dateNew = DateUtils.addDay(lastChangePassword, 180);
				if( new Date().compareTo(dateNew) == 1){
					user.setExpired(true);
				}
			}
			List<GrantedAuthority> authorities = new ArrayList<>();
			if(user.getUserRoles() != null) {
				user.getUserRoles().forEach(e -> {
					authorities.add(new SimpleGrantedAuthority(Constants.ROLE_PREFIX + e.getRoleCode()));
				});
			}
			if(user.getUserOrgs() != null) {
				user.getUserOrgs().forEach(e -> {
					authorities.add(new SimpleGrantedAuthority(Constants.ORG_PREFIX + e.getOrgCode()));
				});
			}
			CustomUserDetails userDetails = new CustomUserDetails(username, user.getUserPwd(),
					!SysUser.STATUS_DISABLED.equals(user.getState()),
					!SysUser.STATUS_DELETED.equals(user.getState()),
					true,
					!SysUser.STATUS_DISABLED.equals(user.getState()), 
					authorities);
			userDetails.setSysUser(user);

			return userDetails;
		}
		throw new UsernameNotFoundException("用户名不存在!");
	}
}
