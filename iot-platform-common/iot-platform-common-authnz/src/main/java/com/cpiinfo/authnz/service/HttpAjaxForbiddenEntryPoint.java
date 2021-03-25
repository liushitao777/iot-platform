package com.cpiinfo.authnz.service;

import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.authnz.util.JacksonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpAjaxForbiddenEntryPoint implements AuthenticationEntryPoint {
	private static final Log logger = LogFactory.getLog(HttpAjaxForbiddenEntryPoint.class);

	/**
	 * Always returns a 403 error code to the client.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException arg2) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json;charset=UTF-8");
		ServiceResponse respObj = new ServiceResponse(false, "未登录或会话已过期", 0, null);
		response.getWriter().println(JacksonUtil.toJson(respObj));
	}
}
