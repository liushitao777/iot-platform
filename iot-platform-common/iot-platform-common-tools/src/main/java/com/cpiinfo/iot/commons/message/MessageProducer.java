package com.cpiinfo.iot.commons.message;

/**
 * @ClassName MessageProducer
 * @Deacription 消息发送接口，用于抽像具体的消息中间件(Kafka)或内部直接转发<p>
 * 不同项目可能使用不同消息中间件，如A项目集群使用Kafka，B项目只有一台应用，直接在消息服务内部转发完成各模块之间的交互
 * @Author yangbo
 * @Date 2020年09月02日 20:08
 * @Version 1.0
 **/
public interface MessageProducer {

	/**
	 * 向指定主题发送传发入的消息内容
	 * @param topic - 要发送消息的主题
	 * @param message - 要发送消息的内容
	 * @return
	 */
	public Object sendMessage(String topic, Object message);
}
