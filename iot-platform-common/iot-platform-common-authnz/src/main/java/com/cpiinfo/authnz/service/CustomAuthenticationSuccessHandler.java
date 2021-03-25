package com.cpiinfo.authnz.service;

import com.cpiinfo.authnz.model.CustomUserDetails;
import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.authnz.util.JacksonUtil;
import com.cpiinfo.sysmgt.api.model.SysResource;
import com.cpiinfo.sysmgt.api.model.SysRole;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private static final Log log = LogFactory.getLog(CustomAuthenticationSuccessHandler.class);

	private UserResourceService userResourceService;
	
	public CustomAuthenticationSuccessHandler(UserResourceService userResourceService) {
		super();
		this.userResourceService = userResourceService;
	}

	public CustomAuthenticationSuccessHandler(UserResourceService userResourceService, String defaultTargetUrl) {
		super(defaultTargetUrl);
		this.userResourceService = userResourceService;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		loadGrantedResources(authentication);
		RequestHeaderRequestMatcher ajaxRequestMatcher = new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");
		if(ajaxRequestMatcher.matches(request)) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json;charset=UTF-8");
			ServiceResponse respObj = new ServiceResponse(true, "用户登录成功", 0, null);
			if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
				CustomUserDetails userDetail = (CustomUserDetails)authentication.getPrincipal();
				respObj.setData(userDetail.getSysUser());

			}
			response.getWriter().println(JacksonUtil.toJson(respObj));
			clearAuthenticationAttributes(request);
		}
		else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

	private void loadGrantedResources(Authentication authentication) {
		if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetail = (CustomUserDetails)authentication.getPrincipal();
			Map<String, Collection<ConfigAttribute>> resources = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();
			
			List<SysResource> sysResources = userResourceService.loadUserResources(userDetail.getUsername(), "true");
			
			for(SysResource r : sysResources) {
				if(r.getResUrl() != null && !"".equals(r.getResUrl()) 
					&& r.getGrantRoles() != null && r.getGrantRoles().size() > 0) {
					List<ConfigAttribute> resRoles = new ArrayList<>(r.getGrantRoles().size());
					for(SysRole role : r.getGrantRoles()) {
						resRoles.add(new SecurityConfig(Constants.ROLE_PREFIX + role.getRoleCode()));
					}
					//RequestMatcher matcher = new AntPathRequestMatcher(r.getResUrl());
					resources.put(r.getResUrl(), resRoles);
				}
			}
			
			userDetail.setResources(resources);
		}
	}

}
