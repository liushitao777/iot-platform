package com.cpiinfo.iot.commons.message.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @ClassName KafkaProducerConfig
 * @Deacription TODO kafka生成者配置
 * @Author liwenjie
 * @Date 2020/8/12 16:51
 * @Version 1.0
 **/
@Configuration
@ConditionalOnProperty(name = "com.cpiinfo.iot.kafka.bootstrap-server")
@EnableKafka
public class KafkaProducerConfig {

    public KafkaProducerConfig(){
        System.out.println("kafka生产者配置加载...");
    }

    @Value("${com.cpiinfo.iot.kafka.bootstrap-server}")
    public String kafkaBootstrapServer;

    /**
     * 配置生产者参数
     * @return
     */
    public Map<String, Object> producerConfigs() {

        Map<String, Object> properties = new HashMap<>();
        //properties.put("bootstrap.servers", "kafka集群IP1:9092,kafka集群IP2:9092");
        properties.put("bootstrap.servers", kafkaBootstrapServer);
        //ack是判别请求是否为完整的条件（就是是判断是不是成功发送了）。我们指定了“all”将会阻塞消息，这种设置性能最低，但是是最可靠的。
        properties.put("acks", "all");
        //如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性。
        properties.put("retries", 0);
        //producer(生产者)缓存每个分区未发送消息。缓存的大小是通过 batch.size 配置指定的
        properties.put("batch.size", 16384);
        //sender线程在检查batch是否ready时候，判断有没有过期的参数，默认大小是0ms
        properties.put("linger.ms", 1);
        //生产者可用于缓冲等待发送到服务器的记录的内存总字节数，默认值为33554432
        properties.put("buffer.memory", 33554432);
        //指定消息key和消息体的编解码方式
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return properties;
    }


    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        KafkaTemplate kafkaTemplate = new KafkaTemplate<>(producerFactory(),true);
        return kafkaTemplate;
    }

    @Bean
    public KafkaMessageProducerImpl messageProducer() {
        return new KafkaMessageProducerImpl();
    }
}