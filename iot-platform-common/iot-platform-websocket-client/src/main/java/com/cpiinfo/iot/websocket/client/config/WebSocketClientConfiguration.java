package com.cpiinfo.iot.websocket.client.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class WebSocketClientConfiguration implements ImportBeanDefinitionRegistrar {
	
	private static final String WEBSOCKET_CLIENT_ANNOTATION_PROCESSOR_BEAN_NAME = "com.cpiinfo.iot.websocket.client.config.webSocketClientBeanPostProcessor";
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		if (!registry.containsBeanDefinition(
				WEBSOCKET_CLIENT_ANNOTATION_PROCESSOR_BEAN_NAME)) {
			registry.registerBeanDefinition(WEBSOCKET_CLIENT_ANNOTATION_PROCESSOR_BEAN_NAME,
				new RootBeanDefinition(WebSocketClientBeanPostProcessor.class));
		}
	}
}