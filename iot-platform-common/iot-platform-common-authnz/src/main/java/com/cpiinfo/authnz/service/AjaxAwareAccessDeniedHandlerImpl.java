package com.cpiinfo.authnz.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.authnz.util.JacksonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

public class AjaxAwareAccessDeniedHandlerImpl extends AccessDeniedHandlerImpl {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		RequestHeaderRequestMatcher ajaxRequestMatcher = new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");
		if(ajaxRequestMatcher.matches(request)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("application/json;charset=UTF-8");
			ServiceResponse respObj = new ServiceResponse(false, "当前用户权限不足", 0, null);
			response.getWriter().println(JacksonUtil.toJson(respObj));
		}
		else {
			super.handle(request, response, accessDeniedException);
		}
	}

}
