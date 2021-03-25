package com.cpiinfo.iot.common.message.direct;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.log.LogAccessor;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.cpiinfo.iot.commons.message.MessageListener;

public class MessageListenerAnnotationBeanPostProcessor
		implements BeanPostProcessor, Ordered, BeanFactoryAware {

	private static final String GENERATED_ID_PREFIX = "com.cpiinfo.iot.common.message.messageListener#";

	private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));

	private final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

	private final ListenerScope listenerScope = new ListenerScope();

	private BeanFactory beanFactory;
	
	private volatile DirectMessageBroker directMessageBroker;

	private final AtomicInteger counter = new AtomicInteger();

	private BeanExpressionResolver resolver = new StandardBeanExpressionResolver();

	private BeanExpressionContext expressionContext;

	private Charset charset = StandardCharsets.UTF_8;

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	/**
	 * Making a {@link BeanFactory} available ;
	 * @param beanFactory the {@link BeanFactory} to be used.
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
		if (beanFactory instanceof ConfigurableListableBeanFactory) {
			this.resolver = ((ConfigurableListableBeanFactory) beanFactory).getBeanExpressionResolver();
			this.expressionContext = new BeanExpressionContext((ConfigurableListableBeanFactory) beanFactory,
					this.listenerScope);
		}
	}
	
	private DirectMessageBroker getDirectMessageBroker() {
		if(directMessageBroker == null && beanFactory != null) {
			synchronized (this) {
				if(directMessageBroker == null) {
					if (beanFactory instanceof ConfigurableListableBeanFactory) {
						String[] beanNames = ((ConfigurableListableBeanFactory) beanFactory).getBeanNamesForType(DirectMessageBroker.class);
						if(beanNames != null && beanNames.length > 0) {
							directMessageBroker = (DirectMessageBroker)((ConfigurableListableBeanFactory) beanFactory).getBean(beanNames[0]);
						}
					}
				}
				
			}
		}
		return directMessageBroker;
	}

	/**
	 * Set a charset to use when converting byte[] to String in method arguments.
	 * Default UTF-8.
	 * 
	 * @param charset the charset.
	 * @since 2.2
	 */
	public void setCharset(Charset charset) {
		Assert.notNull(charset, "'charset' cannot be null");
		this.charset = charset;
	}


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		if (!this.nonAnnotatedClasses.contains(bean.getClass())) {
			Class<?> targetClass = AopUtils.getTargetClass(bean);
			Collection<MessageListener> classLevelListeners = findListenerAnnotations(targetClass);
			final boolean hasClassLevelListeners = classLevelListeners.size() > 0;
			final List<Method> multiMethods = new ArrayList<>();
			Map<Method, Set<MessageListener>> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
					(MethodIntrospector.MetadataLookup<Set<MessageListener>>) method -> {
						Set<MessageListener> listenerMethods = findListenerAnnotations(method);
						return (!listenerMethods.isEmpty() ? listenerMethods : null);
					});
			//if (hasClassLevelListeners) {
			//	Set<Method> methodsWithHandler = MethodIntrospector.selectMethods(targetClass,
			//			(ReflectionUtils.MethodFilter) method -> AnnotationUtils.findAnnotation(method,
			//					KafkaHandler.class) != null);
			//	multiMethods.addAll(methodsWithHandler);
			//}
			if (annotatedMethods.isEmpty()) {
				this.nonAnnotatedClasses.add(bean.getClass());
				this.logger.trace(() -> "No @MessageListener annotations found on bean type: " + bean.getClass());
			} else {
				// Non-empty set of methods
				for (Map.Entry<Method, Set<MessageListener>> entry : annotatedMethods.entrySet()) {
					Method method = entry.getKey();
					for (MessageListener listener : entry.getValue()) {
						processMessageListener(listener, method, bean, beanName);
					}
				}
				this.logger.debug(() -> annotatedMethods.size() + " @MessageListener methods processed on bean '"
						+ beanName + "': " + annotatedMethods);
			}
			//if (hasClassLevelListeners) {
			//	processMultiMethodListeners(classLevelListeners, multiMethods, bean, beanName);
			//}
		}
		return bean;
	}

	/*
	 * AnnotationUtils.getRepeatableAnnotations does not look at interfaces
	 */
	private Collection<MessageListener> findListenerAnnotations(Class<?> clazz) {
		Set<MessageListener> listeners = new HashSet<>();
		MessageListener ann = AnnotationUtils.findAnnotation(clazz, MessageListener.class);
		if (ann != null) {
			listeners.add(ann);
		}
		//MessageListeners anns = AnnotationUtils.findAnnotation(clazz, MessageListeners.class);
		//if (anns != null) {
		//	listeners.addAll(Arrays.asList(anns.value()));
		//}
		return listeners;
	}

	/*
	 * AnnotationUtils.getRepeatableAnnotations does not look at interfaces
	 */
	private Set<MessageListener> findListenerAnnotations(Method method) {
		Set<MessageListener> listeners = new HashSet<>();
		MessageListener ann = AnnotatedElementUtils.findMergedAnnotation(method, MessageListener.class);
		if (ann != null) {
			listeners.add(ann);
		}
		//MessageListeners anns = AnnotationUtils.findAnnotation(method, MessageListeners.class);
		//if (anns != null) {
		//	listeners.addAll(Arrays.asList(anns.value()));
		//}
		return listeners;
	}

	private Method checkProxy(Method methodArg, Object bean) {
		Method method = methodArg;
		if (AopUtils.isJdkDynamicProxy(bean)) {
			try {
				// Found a @MessageListener method on the target class for this JDK proxy ->
				// is it also present on the proxy itself?
				method = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
				Class<?>[] proxiedInterfaces = ((Advised) bean).getProxiedInterfaces();
				for (Class<?> iface : proxiedInterfaces) {
					try {
						method = iface.getMethod(method.getName(), method.getParameterTypes());
						break;
					} catch (@SuppressWarnings("unused") NoSuchMethodException noMethod) {
						// NOSONAR
					}
				}
			} catch (SecurityException ex) {
				ReflectionUtils.handleReflectionException(ex);
			} catch (NoSuchMethodException ex) {
				throw new IllegalStateException(String.format(
						"@MessageListener method '%s' found on bean target class '%s', "
								+ "but not found in any interface(s) for bean JDK proxy. Either "
								+ "pull the method up to an interface or switch to subclass (CGLIB) "
								+ "proxies by setting proxy-target-class/proxyTargetClass " + "attribute to 'true'",
						method.getName(), method.getDeclaringClass().getSimpleName()), ex);
			}
		}
		return method;
	}	

	protected void processMessageListener(MessageListener messageListener, Method method, Object bean, String beanName) {
		Method methodToUse = checkProxy(method, bean);
		
		String beanRef = messageListener.beanRef();
		if (StringUtils.hasText(beanRef)) {
			this.listenerScope.addListener(beanRef, bean);
		}
		String id = getEndpointId(messageListener);
		String[] topics = resolveTopics(messageListener);
		String group = getEndpointGroupId(messageListener, id);
		DirectMessageBroker broker = this.getDirectMessageBroker();
		if(broker == null) {
			throw new BeanInitializationException("Could not register Message listener endpoint on [" + methodToUse
					+ "] for bean " + beanName + ", no " + DirectMessageBroker.class.getSimpleName()
					+ " was found in the application context");
		}
		for(int i = 0; i < topics.length; i++) {
			broker.registerMessageListener(topics[i], group, bean, methodToUse);
		}

		if (StringUtils.hasText(beanRef)) {
			this.listenerScope.removeListener(beanRef);
		}
	}

	private String getEndpointId(MessageListener MessageListener) {
		if (StringUtils.hasText(MessageListener.id())) {
			return resolveExpressionAsString(MessageListener.id(), "id");
		} else {
			return GENERATED_ID_PREFIX + this.counter.getAndIncrement();
		}
	}

	private String getEndpointGroupId(MessageListener MessageListener, String id) {
		String groupId = null;
		if (StringUtils.hasText(MessageListener.groupId())) {
			groupId = resolveExpressionAsString(MessageListener.groupId(), "groupId");
		}
		if (groupId == null) {
			groupId = GENERATED_ID_PREFIX + this.counter.getAndIncrement() + "_Group#"+this.counter.getAndIncrement();
		}
		return groupId;
	}

	private String[] resolveTopics(MessageListener MessageListener) {
		String[] topics = MessageListener.topics();
		List<String> result = new ArrayList<>();
		if (topics.length > 0) {
			for (String topic1 : topics) {
				Object topic = resolveExpression(topic1);
				resolveAsString(topic, result);
			}
		}
		return result.toArray(new String[0]);
	}


	@SuppressWarnings("unchecked")
	private void resolveAsString(Object resolvedValue, List<String> result) {
		if (resolvedValue instanceof String[]) {
			for (Object object : (String[]) resolvedValue) {
				resolveAsString(object, result);
			}
		} else if (resolvedValue instanceof String) {
			result.add((String) resolvedValue);
		} else if (resolvedValue instanceof Iterable) {
			for (Object object : (Iterable<Object>) resolvedValue) {
				resolveAsString(object, result);
			}
		} else {
			throw new IllegalArgumentException(
					String.format("@MessageListener can't resolve '%s' as a String", resolvedValue));
		}
	}


	private String resolveExpressionAsString(String value, String attribute) {
		Object resolved = resolveExpression(value);
		if (resolved instanceof String) {
			return (String) resolved;
		} else if (resolved != null) {
			throw new IllegalStateException("The [" + attribute + "] must resolve to a String. " + "Resolved to ["
					+ resolved.getClass() + "] for [" + value + "]");
		}
		return null;
	}

	private Object resolveExpression(String value) {
		return this.resolver.evaluate(resolve(value), this.expressionContext);
	}

	/**
	 * Resolve the specified value if possible.
	 * 
	 * @param value the value to resolve
	 * @return the resolved value
	 * @see ConfigurableBeanFactory#resolveEmbeddedValue
	 */
	private String resolve(String value) {
		if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
			return ((ConfigurableBeanFactory) this.beanFactory).resolveEmbeddedValue(value);
		}
		return value;
	}

	private static class ListenerScope implements Scope {

		private final Map<String, Object> listeners = new HashMap<>();

		ListenerScope() {
			super();
		}

		public void addListener(String key, Object bean) {
			this.listeners.put(key, bean);
		}

		public void removeListener(String key) {
			this.listeners.remove(key);
		}

		@Override
		public Object get(String name, ObjectFactory<?> objectFactory) {
			return this.listeners.get(name);
		}

		@Override
		public Object remove(String name) {
			return null;
		}

		@Override
		public void registerDestructionCallback(String name, Runnable callback) {
		}

		@Override
		public Object resolveContextualObject(String key) {
			return this.listeners.get(key);
		}

		@Override
		public String getConversationId() {
			return null;
		}

	}
}
