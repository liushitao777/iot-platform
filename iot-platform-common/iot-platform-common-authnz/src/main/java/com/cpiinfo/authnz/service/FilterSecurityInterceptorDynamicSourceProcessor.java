package com.cpiinfo.authnz.service;

import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class FilterSecurityInterceptorDynamicSourceProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {

	@Override
	public <O extends FilterSecurityInterceptor> O postProcess(O object) {
		FilterInvocationSecurityMetadataSource source = object.getSecurityMetadataSource();
		object.setSecurityMetadataSource(new DynamicSecurityMetadataSource(source));
		return object;
	}
}
