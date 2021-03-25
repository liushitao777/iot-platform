package com.cpiinfo.authnz.service;

import com.cpiinfo.authnz.exception.UserLockException;
import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.authnz.util.JacksonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		RequestHeaderRequestMatcher ajaxRequestMatcher = new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");
		if(ajaxRequestMatcher.matches(request)) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json;charset=UTF-8");
			ServiceResponse respObj =null;
			if(exception instanceof UserLockException){
				respObj = new ServiceResponse(false, exception.getMessage(), 0, null);
			}else {
			 	respObj = new ServiceResponse(false, "登录失败，请检查用户名或密码是否正确!", 0, null);
			}
			response.getWriter().println(JacksonUtil.toJson(respObj));
		}
		else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}



}
