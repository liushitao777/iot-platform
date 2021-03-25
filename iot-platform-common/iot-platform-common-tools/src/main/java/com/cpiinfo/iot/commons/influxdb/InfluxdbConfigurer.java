package com.cpiinfo.iot.commons.influxdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = InfluxDbConfigProperties.PREFIX, matchIfMissing = false)
@EnableConfigurationProperties(InfluxDbConfigProperties.class)
public class InfluxdbConfigurer {
    
    @Autowired
    private InfluxDbConfigProperties properties;
    
    @Bean
    @ConditionalOnMissingBean
    public InfluxdbDatabaseProvider influxdbDatabaseProvider() {
        return new SimpleInfluxdbDatabaseProvider(properties.getDatabase());
    }
}
