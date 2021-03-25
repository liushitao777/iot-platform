package com.cpiinfo.iot.commons.message.kafka;

import com.cpiinfo.iot.commons.message.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaMessageProducerImpl implements MessageProducer {

	@Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public Object sendMessage(String topic, Object message) {
		return kafkaTemplate.send(topic, message);
	}

}
