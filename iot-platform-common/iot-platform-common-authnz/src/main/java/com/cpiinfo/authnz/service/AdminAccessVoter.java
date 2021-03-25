package com.cpiinfo.authnz.service;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AdminAccessVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		if(authorities != null) {
			boolean hasAdminRole = false;
			for(GrantedAuthority authority : authorities) {
				if(Constants.ROLE_ADMIN.equals(authority.getAuthority())) {
					hasAdminRole = true;
				}
			}
			if(hasAdminRole) {
				return ACCESS_GRANTED;
			}
		}
		return ACCESS_ABSTAIN;
	}

}
