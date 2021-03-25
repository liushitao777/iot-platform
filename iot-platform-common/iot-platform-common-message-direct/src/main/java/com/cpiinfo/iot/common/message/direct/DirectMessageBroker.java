package com.cpiinfo.iot.common.message.direct;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cpiinfo.iot.commons.message.MessageProducer;
import com.cpiinfo.iot.commons.utils.RandomUtils;

public class DirectMessageBroker implements MessageProducer {
	
	private Map<String, Map<String, List<MessageListenerEndpoint>>> listeners = new ConcurrentHashMap<>();
	
	public void registerMessageListener(String topic, String group, Object bean, Method method) {
		Map<String, List<MessageListenerEndpoint>> map = listeners.get(topic);
		if(map == null) {
			map = new ConcurrentHashMap<String, List<MessageListenerEndpoint>>();
			listeners.put(topic, map);
		}
		List<MessageListenerEndpoint> list = map.get(group);
		if(list == null) {
			list = new ArrayList<DirectMessageBroker.MessageListenerEndpoint>();
			map.put(group, list);
		}
		MessageListenerEndpoint endpoint = new MessageListenerEndpoint(bean, method);
		if(!list.contains(endpoint)) {
			list.add(endpoint);
		}
	}
	
	@Override
	public Object sendMessage(String topic, Object message) {
		Map<String, List<MessageListenerEndpoint>> map = listeners.get(topic);
		if(map != null) {
			map.values().forEach(list -> {
				if(list != null && list.size() > 0) {
					int i = RandomUtils.random(0, list.size() - 1);
					MessageListenerEndpoint endpoint = list.get(i);
					try {
						endpoint.method.invoke(endpoint.bean, message);
					} catch (Exception e) {
						throw new RuntimeException("error while dispatch message to MessageListener " + 
								endpoint.method + " in object " + endpoint.bean, e);
					}
				}
			});
		}
		return null;
	}
	
	private static class MessageListenerEndpoint {
		Object bean;
		Method method;
		MessageListenerEndpoint(Object bean, Method method){
			this.bean = bean;
			this.method = method;
		}
		
		@Override
		public int hashCode() {
			return bean.hashCode() ^ method.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof MessageListenerEndpoint) {
				MessageListenerEndpoint other = (MessageListenerEndpoint)obj;
				return this.bean == other.bean && this.method == other.method;
			}
			return false;
		}
	}
}
