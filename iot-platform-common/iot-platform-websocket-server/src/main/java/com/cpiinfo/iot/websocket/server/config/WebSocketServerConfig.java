package com.cpiinfo.iot.websocket.server.config;

import java.security.Principal;
import java.util.Collections;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName WebSocketServerConfig
 * @Deacription WebSocket服务器端配置对象，完成连接建立时的用户校验等工作
 * @Author yangbo
 * @Date 2020年09月07日 10:08
 * @Version 1.0
 **/
@Configuration
public class WebSocketServerConfig extends ServerEndpointConfig.Configurator {
	
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		super.modifyHandshake(sec, request, response);
		//valid login user 
		if(!checkUserLogin(sec, request)) {
			//用户登录和权限检查不通过时，拒绝建立Web Socket连接
			response.getHeaders().put(HandshakeResponse.SEC_WEBSOCKET_ACCEPT, Collections.emptyList());
		}
	}
	
	private boolean checkUserLogin(ServerEndpointConfig sec, HandshakeRequest request) {
		Principal userPrincipal = request.getUserPrincipal();
		if(userPrincipal instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)userPrincipal;
			sec.getUserProperties().put("userCode", token.getName());
			return true;
		}
		return false;
	}

	@ConditionalOnMissingBean
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
