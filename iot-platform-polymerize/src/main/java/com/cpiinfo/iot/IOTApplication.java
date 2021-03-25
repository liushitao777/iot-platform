package com.cpiinfo.iot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cpiinfo.iot.websocket.client.config.EnableWebSocketClient;

/**
 * @ClassName IOTApplication
 * @Deacription TODO
 * @Author liwenjie
 * @Date 2020/7/16 15:10
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages={"com.cpiinfo.*","com.cpiinfo.iot.*"})
@MapperScan("com.cpiinfo.**.dao")
@EnableWebSocketClient
public class IOTApplication {
    public static void main(String[] args) {
        SpringApplication.run(IOTApplication.class,args);
    }
}
