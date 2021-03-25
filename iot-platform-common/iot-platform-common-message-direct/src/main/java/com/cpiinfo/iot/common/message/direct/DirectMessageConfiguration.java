package com.cpiinfo.iot.common.message.direct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectMessageConfiguration {
	
	@Bean
	MessageListenerAnnotationBeanPostProcessor messageListenerAnnotationBeanPostProcessor() {
		return new MessageListenerAnnotationBeanPostProcessor();
	}
	
	@Bean
	DirectMessageBroker directMessageBroker() {
		return new DirectMessageBroker();
	}
}
