package com.cpiinfo.authnz.session;

import com.cpiinfo.authnz.model.CustomUserDetails;
import com.cpiinfo.authnz.service.AdminAccessVoter;
import com.cpiinfo.sysmgt.api.model.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 * @author yabo
 * @date 2019/09/16
 */
public class SessionUtils {
	
	public static SysUser getSessionUserInfo() {
		SysUser userInfo = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if(auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
			userInfo = userDetails == null ? null : userDetails.getSysUser();
		}
		return userInfo;
	}
	
	public static boolean isAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AdminAccessVoter adminVoter = new AdminAccessVoter();
		return adminVoter.vote(auth, null, null) == AdminAccessVoter.ACCESS_GRANTED;
	}
}
