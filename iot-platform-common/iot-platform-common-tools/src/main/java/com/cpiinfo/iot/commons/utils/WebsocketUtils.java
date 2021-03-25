package com.cpiinfo.iot.commons.utils;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cpiinfo.iot.commons.message.MessageProducer;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName WebsocketUtils
 * @Deacription TODO websocket工具类
 * @Author liwenjie
 * @Date 2020/7/23 16:56
 * @Version 1.0
 **/
@Component
@Slf4j
public class WebsocketUtils implements ApplicationContextAware {
    
    private ApplicationContext ctx;

    private MessageProducer messageProducer;

    @Value("${iot.websocket.notify.topic:WebSocketNotify}")
    private String webSocketNotifyTopic;

    /**
     * 发送信息给前端
     * @param dataType
     * @param dataId
     * @param msg
     */
    public void sendBrowserMessage(String dataType, String dataId,
            JSONObject msg) {
        sendMessage("browser", null, dataType, dataId, msg);
    }

    /**
     * 发送信息给前端
     * @param clientGroup
     * @param userCode
     * @param dataType
     * @param dataId
     * @param msg
     */
    public void sendMessage(String clientGroup, String userCode, String dataType, String dataId,
            JSONObject msg) {
        JSONObject packet = new JSONObject();
        packet.put("clientGroup", clientGroup);
        packet.put("userCode", userCode);
        packet.put("dataType", dataType);
        packet.put("dataId", dataId);
        packet.put("msg", msg);
        getMessageProducer().sendMessage(webSocketNotifyTopic, packet.toString());
    }

    public void sendMessageArr(String clientGroup, String userCode, String dataType, String dataId,
                            JSONArray arr) {
        JSONObject packet = new JSONObject();
        packet.put("clientGroup", clientGroup);
        packet.put("userCode", userCode);
        packet.put("dataType", dataType);
        packet.put("dataId", dataId);
        packet.put("msg", arr);
        getMessageProducer().sendMessage(webSocketNotifyTopic, packet.toString());
    }

    private MessageProducer getMessageProducer() {
        if(messageProducer == null){
            if(ctx != null){
                Map<String, MessageProducer> producers = ctx.getBeansOfType(MessageProducer.class);
                if(producers != null && producers.size() > 0){
                    messageProducer = producers.values().iterator().next();
                }
            }
        }
        return messageProducer;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
