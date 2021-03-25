package com.cpiinfo.iot.websocket.server.service;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 * @ClassName WebSocketServer
 * @Deacription 公共WebSocket通知服务接口，处理集群部署及数据类型/范围过虑推送内容
 * @Author yangbo
 * @Date 2020年09月02日 20:08
 * @Version 1.0
 **/
public interface WebSocketServer {

	/**
	 * 客户端连接
	 * @param clientGroup - 连接客户端组别  browser-浏览器  android-移动app
	 * @param session 会话
	 * @param config 配置对象
	 */
	public void onOpen(String clientGroup, Session session, EndpointConfig config);
	
	/**
	 * 连接关闭调用的方法
	 * @param session 会话
	 * @param reason 关闭原因
	 */
	public void onClose(Session session, CloseReason reason);
	
	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 会话
	 */
	public void onMessage(String message, Session session);
	
	 /**
     * 错误
     * @param session
     * @param error
     */
	public void onError(Session session, Throwable error);
}
