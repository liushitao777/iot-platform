package com.cpiinfo.iot.websocket.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cpiinfo.iot.commons.message.MessageListener;
import com.cpiinfo.iot.websocket.server.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WebSocketServerImpl
 * @Deacription 公共WebSocket通知服务实现对象，处理集群部署及数据类型/范围过虑推送内容
 * @Author yangbo
 * @Date 2020年09月02日 20:08
 * @Version 1.0
 **/
@Component
@Slf4j
public class WebSocketServerImpl implements WebSocketServer {
	
	private ConcurrentHashMap<String, WebSocketSession> wsSessions = new ConcurrentHashMap<String, WebSocketSession>();
	
	@Override
	public void onOpen(String clientGroup, Session session, EndpointConfig config) {
		String userCode = (String)config.getUserProperties().get("userCode");

		WebSocketSession wsSession = new WebSocketSession();
		wsSession.setSession(session);
		wsSession.setUserCode(userCode);
		wsSession.setClientGroup(clientGroup);
		wsSessions.put(session.getId(), wsSession);		
		
		if(log.isDebugEnabled()) {
			log.debug("Web socket client connect, client:" + clientGroup + ", userCode:" + userCode);
		}
	}

	@Override
	public void onClose(Session session, CloseReason reason) {
		WebSocketSession wsSession = wsSessions.remove(session.getId());
    	if(log.isDebugEnabled()) {
			log.debug("Web socket client disconnect, client:" + wsSession.getClientGroup() + ", userCode:" + wsSession.getUserCode());
		}
	}

	@Override
	public void onMessage(String message, Session session) {
		JSONObject json = JSONObject.parseObject(message);
        if("applyFilter".equals(json.getString("cmd"))) {
        	WebSocketSession wsSession = wsSessions.get(session.getId());
        	wsSession.setDataTypeFilter(json.getString("dataTypeFilter"));
        	wsSession.setDataScopeFilter(json.getString("dataScopeFilter"));
			wsSession.setEmptyMessage(json.getString("emptyMessage"));
        	wsSession.setFilterApplied(true);
        }
	}

	@Override
	public void onError(Session session, Throwable error) {
		WebSocketSession wsSession = wsSessions.get(session.getId());	
        log.error("error in web socket session, client:" + wsSession.getClientGroup() + ", userCode:" + wsSession.getUserCode(), error);
	}

	@MessageListener(topics = "${iot.websocket.notify.topic:WebSocketNotify}")
	public void notifyMessage(String message) {
		if (log.isDebugEnabled()) {
			log.debug("Websocket notifyMessage 收到消息 message: {}", message);
		}
		if(wsSessions.size() > 0) {
			JSONObject json = JSONObject.parseObject(message);
			String clientGroup = json.getString("clientGroup");
			String userCode = json.getString("userCode");
			String dataType = json.getString("dataType");
			String dataScope = json.getString("dataId");
			Collection<WebSocketSession> sessions = wsSessions.values();
			sessions.forEach(session -> {
			    if (log.isDebugEnabled()) {
		            log.debug("Websocket notifyMessage for session[" + session.isFilterApplied() + "] " 
		                + session.getClientGroup() + ":" + session.getUserCode()
		                + ":" + session.getDataTypeFilter() + ":" +session.getDataScopeFilter() 
		                + "==" + clientGroup + ":" + userCode + ":" + dataType + ":" + dataScope);
		        }
			    if(session.isFilterApplied()) {
    				boolean clientGroupMatch = clientGroup == null || clientGroup.contains(session.getClientGroup());
    				boolean userCodeMatch = userCode == null || userCode.contains(session.getUserCode());
    				boolean dataTypeMatch = dataType == null || session.getDataTypeFilter() == null
    						|| "".equals(session.getDataTypeFilter()) || session.getDataTypeFilter().contains(dataType);
    				boolean dataScopeMatch = dataScope == null || session.getDataScopeFilter() == null 
    						|| "".equals(session.getDataScopeFilter()) || session.getDataScopeFilter().contains(dataScope);
    				if(clientGroupMatch && userCodeMatch && dataTypeMatch && dataScopeMatch) {
    				    json.remove("clientGroup");
    				    json.remove("userCode");
    					try {
    						synchronized (session) {
    							String emptyMessage = session.getEmptyMessage();
    							if (StringUtils.isNotBlank(emptyMessage) && "empty".equals(emptyMessage)) {
									session.getSession().getBasicRemote().sendText(new JSONArray().toString());
								} else {
									session.getSession().getBasicRemote().sendText(json.toString());
								}
    						}
    					} catch (IOException e) {
    						log.error("error while sending websocket notify message. clientGroup:" + session.getClientGroup() 
    							+ ", userCode:" + session.getUserCode(), e);
    					}
    				}
			    }
			});
		}
	}
	
}
