package com.cpiinfo.iot.websocket.server.service;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.cpiinfo.iot.commons.utils.SpringContextUtils;
import com.cpiinfo.iot.websocket.server.config.WebSocketServerConfig;

/**
 * @ClassName WebSocketServer
 * @Deacription 公共WebSocket通知服务入口点，处理集群部署及数据类型/范围过虑推送内容
 * @Author yangbo
 * @Date 2020年09月02日 20:08
 * @Version 1.0
 **/
@ServerEndpoint(value = "/server/{clientGroup}", configurator = WebSocketServerConfig.class)
@Component
public class WebSocketServerEndpoint {

	private volatile WebSocketServer webSocketServer;
	
	private WebSocketServer getWebSocketServer() {
		if(webSocketServer == null) {
			synchronized (this) {
				if(webSocketServer == null) {
					webSocketServer = SpringContextUtils.getBean(WebSocketServer.class);
				}
			}
		}
		return webSocketServer;
	}
	
	@OnOpen
    public void onOpen(@PathParam("clientGroup") String clientGroup, Session session, EndpointConfig config) {
		getWebSocketServer().onOpen(clientGroup, session, config);
	}

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) {
    	getWebSocketServer().onClose(session, reason);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message - 客户端发送过来的消息
     * @param session - 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
		System.out.println("收到消息：" + message);
    	getWebSocketServer().onMessage(message, session);
    }

    /**
     * 错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
    	getWebSocketServer().onError(session, error);
    }
}
