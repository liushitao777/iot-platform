package com.cpiinfo.iot.commons.config;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ConcurrentSchedulingConfigurer implements SchedulingConfigurer {

    @Value("${iot.common.scheduling.thread-count:10}")
    private int schedulingThreadCount;
    
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(schedulingThreadCount));
    }

}
