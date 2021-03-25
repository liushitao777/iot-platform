package com.cpiinfo.authnz.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cpiinfo.authnz.model.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);

	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		Authentication auth = super.attemptAuthentication(request, response);
		if(auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
			String tenant = request.getParameter("tenant");
			if(tenant == null || "".equals("tenant")) {
				tenant = getParameterFromRequestBody(request, "tenant");
			}
			if(tenant != null && !"".equals(tenant)) {
				((CustomUserDetails)auth.getPrincipal()).setTenant(tenant);
			}
		}
		return auth;
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		
		String username = super.obtainUsername(request);
		if(username == null || "".equals(username)) {
			username = getParameterFromRequestBody(request, getUsernameParameter());
		}
		return username;
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String password = super.obtainPassword(request);
		if(password == null || "".equals(password)) {
			password = getParameterFromRequestBody(request, getPasswordParameter());
		}
		return password;
	}

	private String getParameterFromRequestBody(HttpServletRequest request, String parameter) {
		String username;
		JsonNode json = (JsonNode)request.getAttribute("___RequestBodyInfo__" + request.toString());
		if(json == null) {
			ObjectMapper jsonMapper = new ObjectMapper();
			try {
				json = jsonMapper.readTree(request.getInputStream());
				request.setAttribute("___RequestBodyInfo__" + request.toString(), json);
			} catch (IOException e) {
				if(logger.isDebugEnabled()) {
					logger.debug("read user login info error. ", e);
				}
				else {
					logger.warn("read user login info error. " + e.getMessage());
				}
			}
		}
		username = json == null || json.get(parameter) == null ? null : json.get(parameter).textValue();
		return username;
	}
}
