package com.cpiinfo.authnz.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.cpiinfo.authnz.model.CustomUserDetails;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private FilterInvocationSecurityMetadataSource chain;
	
	private Map<String, RequestMatcher> reqMatcher = new ConcurrentHashMap<String, RequestMatcher>();
	
	public DynamicSecurityMetadataSource(FilterInvocationSecurityMetadataSource chain) {
		this.chain = chain;
	}
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		Map<String, Collection<ConfigAttribute>> requestMap = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if(auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
			requestMap = (Map<String, Collection<ConfigAttribute>>) ((CustomUserDetails)auth.getPrincipal()).getResources();
		}
		if(requestMap != null) {
			final HttpServletRequest request = ((FilterInvocation) object).getRequest();
			for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
				if (getRequestMatcher(entry.getKey()).matches(request)) {
					return entry.getValue();
				}
			}
		}
		return chain == null ? null : chain.getAttributes(object);
	}

	private RequestMatcher getRequestMatcher(String url) {
		RequestMatcher matcher = reqMatcher.get(url);
		if(matcher == null) {
			matcher = new AntPathRequestMatcher(url);
			reqMatcher.put(url, matcher);
		}
		return matcher;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
