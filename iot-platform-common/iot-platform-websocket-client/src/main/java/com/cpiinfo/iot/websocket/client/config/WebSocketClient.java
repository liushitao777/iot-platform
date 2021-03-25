package com.cpiinfo.iot.websocket.client.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WebSocketClient{

	/**
	 * The unique identifier of the container managing for this endpoint.
	 * <p>If none is specified an auto-generated one is provided.
	 * <p>SpEL {@code #{...}} and property place holders {@code ${...}} are supported.
	 * @return
	 */
	String id() default "";

	
	/**
	 * the web socket server url
	 * <p>SpEL {@code #{...}} and property place holders {@code ${...}} are supported.
	 */
	String serverUrl() default "";

	/**
	 * Set to true or false, to indicate whether the websocket client auto connect to server or not
	 * <p>SpEL {@code #{...}} and property place holders {@code ${...}} are supported.
	 * @return true to auto start, false to not auto start.
	 */
	String autoStartup() default "true";

	/**
	 * Set to true or false, to indicate whether the websocket client auto re-connect while disconnected or not
	 * <p>SpEL {@code #{...}} and property place holders {@code ${...}} are supported.
	 * @return true to auto start, false to not auto start.
	 */
	String autoReconnect() default "true";
	
	/**
     * Set to true or false, to indicate whether the websocket client enabled or not
     * <p>SpEL {@code #{...}} and property place holders {@code ${...}} are supported.
     * @return true to enable, false to disable.
     */
    String enabled() default "true";

	/**
	 * A pseudo bean name used in SpEL expressions within this annotation to reference
	 * the current bean within which this listener is defined. This allows access to
	 * properties and methods within the enclosing bean.
	 * Default '__client'.
	 * <p>
	 * Example: {@code topics = "#{__client.id}"}.
	 * @return the pseudo bean name.
	 */
	String beanRef() default "__client";
}
