package com.cpiinfo.iot.commons.message.kafka;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MessageBootstrapConfiguration implements ImportBeanDefinitionRegistrar {
	private static final String MESSAGE_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME = "com.cpiinfo.iot.commons.message.kafka.messageListenerAnnotationBeanPostProcessor";
	
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		if (!registry.containsBeanDefinition(
				MESSAGE_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME)) {
			registry.registerBeanDefinition(MESSAGE_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME,//KafkaListenerConfigUtils.KAFKA_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME,
				new RootBeanDefinition(MessageListenerAnnotationBeanPostProcessor.class));
		}
	}
}