package com.cpiinfo.iot.commons.influxdb;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @ClassName iot-platform
 * @Deacription TODO
 * @Author wuchangjiang
 * @Date 2020年08月11日 21:10
 * @Version 1.0
 **/
@Data
@ConfigurationProperties(prefix = InfluxDbConfigProperties.PREFIX)
public class InfluxDbConfigProperties {
    public static final String PREFIX = "com.cpiinfo.iot.influxdb";
    
    /**
     * influxDB服务地址
     */
    private String url;

    /**
     * 访问用户名
     */
    private String username;

    /**
     * 访问密码
     */
    private String password;

    /**
     * influxDB数据
     */
    private String database;
    
    /**
     * 数据保留规则
     */
    private String retentionPolicy = "autogen";

    /**
     * 连接超时 秒
     */
    private int connectTimeout = 10;
    
    /**
     * 读取超时 秒
     */
    private int readTimeout = 60;
    
    /**
     * 写入超时 秒
     */
    private int writeTimeout = 10;

    /**
     * 开启压缩
     */
    private boolean gzip = true;
    
    /**
     * 开启批处理
     */
    private boolean batchEnabled = true;
    
    /**
     * 日志级别
     */
    private String logLevel = "BASIC";
}
