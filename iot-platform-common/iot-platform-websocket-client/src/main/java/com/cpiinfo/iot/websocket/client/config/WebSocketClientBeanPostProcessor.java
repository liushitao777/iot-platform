package com.cpiinfo.iot.websocket.client.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.log.LogAccessor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.client.standard.AnnotatedEndpointConnectionManager;

public class WebSocketClientBeanPostProcessor implements 
	BeanPostProcessor, Ordered, BeanFactoryAware, InitializingBean, DisposableBean {
	
	private static final String GENERATED_ID_PREFIX = "com.cpiinfo.iot.websocket.client.config.WebSocketClient#";

	private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));

	private final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));
	
	private final ClientObjectScope clientObjectScope = new ClientObjectScope();
	
	/**
	 * re-connect enabled clients
	 */
	private final List<IotAnnotatedEndpointConnectionManager> rcClients = new ArrayList<>();
	
	private BeanFactory beanFactory;

	private final AtomicInteger counter = new AtomicInteger();

	private BeanExpressionResolver resolver = new StandardBeanExpressionResolver();

	private BeanExpressionContext expressionContext;
	
	private volatile ScheduledExecutorService scheduledService;
	private boolean createdScheduler = false;

	private Charset charset = StandardCharsets.UTF_8;

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}


	/**
	 * 
	 * @param beanFactory the {@link BeanFactory} to be used.
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
		if (beanFactory instanceof ConfigurableListableBeanFactory) {
			this.resolver = ((ConfigurableListableBeanFactory) beanFactory).getBeanExpressionResolver();
			this.expressionContext = new BeanExpressionContext((ConfigurableListableBeanFactory) beanFactory,
					this.clientObjectScope);
		}
	}

	/**
	 * Set a charset to use when converting byte[] to String in method arguments.
	 * Default UTF-8.
	 * 
	 * @param charset the charset.
	 */
	public void setCharset(Charset charset) {
		Assert.notNull(charset, "'charset' cannot be null");
		this.charset = charset;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		scheduleReconnect();
	}
	
	@Override
	public void destroy() throws Exception {
		if(scheduledService != null && createdScheduler) {
			scheduledService.shutdown();
		}
		rcClients.forEach(client -> {
            try {
                if(client.isConnected()) {
                    try {
                        client.closeConnection();
                    } catch (Exception e) {
                        logger.error(e, "error while close connection.");
                    }
                }
                if(client.isRunning()) {
                    client.stop();
                }
            }
            catch(Throwable ex) {
                logger.error(ex, "error while connect websocket " + client);
            }
        });
	}

	private void scheduleReconnect() {
		if(scheduledService == null && rcClients.size() > 0){
			getScheduledService().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					rcClients.forEach(client -> {
					    try {
    						if(!client.isRunning()) {
    							client.start();
    						}
    						else if(!client.isConnected()) {
    							try {
    								client.closeConnection();
    							} catch (Exception e) {
    								logger.error(e, "error while close connection.");
    							}
    							try {
    							    client.openConnection();
    							}
    							catch(Exception ex) {
    							    throw new RuntimeException("error while init websocket connection to:"+client.getServerUrl(), ex);
    							}
    							if(!client.isConnected()) {
    							    logger.error("error while init websocket connection to:"+client.getServerUrl());
    							}
    						}
					    }
					    catch(Throwable ex) {
					        logger.error(ex, "error while connect websocket " + client);
					    }
					});
				}
			}, 60, 5, TimeUnit.SECONDS);
		}
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		if (!this.nonAnnotatedClasses.contains(bean.getClass())) {
			Class<?> targetClass = AopUtils.getTargetClass(bean);
			WebSocketClient client = AnnotationUtils.findAnnotation(targetClass, WebSocketClient.class);
			if (client == null) {
				this.nonAnnotatedClasses.add(bean.getClass());
				if(this.logger.isTraceEnabled()) {
					this.logger.trace(() -> "No @WebSocketClient annotations found on bean type: " + bean.getClass());
				}
			} else {
				processWebSocketClient(client, bean, beanName);
				if(this.logger.isDebugEnabled()) {
					this.logger.debug(() -> " @WebSocketClient processed on bean '"
							+ beanName + "' ");
				}
				scheduleReconnect();
			}
		}
		return bean;
	}

	protected void processWebSocketClient(WebSocketClient client, Object bean, String beanName) {

		String beanRef = client.beanRef();
		if (StringUtils.hasText(beanRef)) {
			this.clientObjectScope.addClient(beanRef, bean);
		}
		String id = getClientId(client);
        Boolean enabled = getEnabled(client);
        if(Boolean.TRUE.equals(enabled)) {
    		String serverUrl = getServerUrl(client);
    		Boolean autoStartup = getAutoStartup(client);
    		Boolean autoReconnect = getAutoReconnect(client);
    		if(serverUrl == null || "".equals(serverUrl)) {
    			throw new BeanInitializationException("Could not register websocket client endpoint"
    					+ " for bean " + beanName + ", no serverUrl was found in the WebSocketClient annotation.");
    		}
    		IotAnnotatedEndpointConnectionManager clientManager = new IotAnnotatedEndpointConnectionManager(bean, serverUrl);
    		clientManager.setAutoStartup(autoStartup);
    		if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
    			((ConfigurableBeanFactory)beanFactory).registerSingleton(id, clientManager);
    		}
    		if(autoReconnect) {
    			rcClients.add(clientManager);
    		}
        }

		if (StringUtils.hasText(beanRef)) {
			this.clientObjectScope.removeClient(beanRef);
		}
	}

	protected ScheduledExecutorService getScheduledService() {
		if(scheduledService == null) {
			synchronized (this) {
				if(scheduledService == null) {
					String[] schedulers = ((ListableBeanFactory)beanFactory).getBeanNamesForType(ScheduledExecutorService.class);
					if(schedulers != null && schedulers.length > 0) {
						scheduledService = (ScheduledExecutorService)beanFactory.getBean(schedulers[0]);
					}
					else {
						scheduledService = Executors.newSingleThreadScheduledExecutor();
						createdScheduler = true;
					}
				}
			}
		}
		return scheduledService;
	}
	
	private String getClientId(WebSocketClient client) {
		if (StringUtils.hasText(client.id())) {
			return resolveExpressionAsString(client.id(), "id");
		} else {
			return GENERATED_ID_PREFIX + this.counter.getAndIncrement();
		}
	}
	
	private String getServerUrl(WebSocketClient client) {
		if (StringUtils.hasText(client.serverUrl())) {
			return resolveExpressionAsString(client.serverUrl(), "serverUrl");
		} else {
			return "";
		}
	}
	
	private Boolean getAutoStartup(WebSocketClient client) {
		if (StringUtils.hasText(client.autoStartup())) {
			return resolveExpressionAsBoolean(client.autoStartup(), "autoStartup");
		} else {
			return Boolean.TRUE;
		}
	}
	
	private Boolean getAutoReconnect(WebSocketClient client) {
        if (StringUtils.hasText(client.autoReconnect())) {
            return resolveExpressionAsBoolean(client.autoReconnect(), "autoReconnect");
        } else {
            return Boolean.TRUE;
        }
    }

	private Boolean getEnabled(WebSocketClient client) {
        if (StringUtils.hasText(client.enabled())) {
            return resolveExpressionAsBoolean(client.enabled(), "enable");
        } else {
            return Boolean.TRUE;
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

	private Boolean resolveExpressionAsBoolean(String value, String attribute) {
		Object resolved = resolveExpression(value);
		Boolean result = null;
		if (resolved instanceof Boolean) {
			result = (Boolean) resolved;
		} else if (resolved instanceof String) {
			result = Boolean.parseBoolean((String) resolved);
		} else if (resolved != null) {
			throw new IllegalStateException(
					"The [" + attribute + "] must resolve to a Boolean or a String that can be parsed as a Boolean. "
							+ "Resolved to [" + resolved.getClass() + "] for [" + value + "]");
		}
		return result;
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

	private static class IotAnnotatedEndpointConnectionManager extends AnnotatedEndpointConnectionManager implements TaskExecutor {
		/**
		 * 信号量对象，处理有网络延迟时建立连接无法在单次重连定时间隔内完成。前一次连接中时，如果第二次定时触发，则直接跳过不再连接
		 */
		private Semaphore singleSemaphore = new Semaphore(1);
		
		private String serverUrl;
		
		public IotAnnotatedEndpointConnectionManager(Object endpoint, String uriTemplate, Object... uriVariables) {
			super(endpoint, uriTemplate, uriVariables);
			this.serverUrl = uriTemplate;
			setTaskExecutor(this);
		}
		
		@Override
		public void closeConnection() throws Exception {
			super.closeConnection();
		}
		
		@Override
		public void openConnection() {
			if(singleSemaphore.tryAcquire()) {
				try {
					super.openConnection();
				}
				finally {
					singleSemaphore.release();
				}
			}
		}
		
		@Override
		public boolean isConnected() {
			return super.isConnected();
		}

		@Override
		public void execute(Runnable task) {
			//同步方式建立连接,避免断线定时重连并发重复连接问题
			task.run();
		}
		
		public String getServerUrl() {
		    return this.serverUrl;
		}
	}
	
	private static class ClientObjectScope implements Scope {

		private final Map<String, Object> clients = new HashMap<>();

		ClientObjectScope() {
			super();
		}

		public void addClient(String key, Object bean) {
			this.clients.put(key, bean);
		}

		public void removeClient(String key) {
			this.clients.remove(key);
		}

		@Override
		public Object get(String name, ObjectFactory<?> objectFactory) {
			return this.clients.get(name);
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
			return this.clients.get(key);
		}

		@Override
		public String getConversationId() {
			return null;
		}

	}
}
