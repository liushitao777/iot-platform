package com.cpiinfo.iot.logdict.tasks;

import com.cpiinfo.iot.logdict.service.SysLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author ShuPF
 * @Date 2021-01-07 15:34
 */
@Component
public class SysLogTask {

    private Logger logger = LogManager.getLogger(SysLogTask.class);

    @Autowired
    private SysLogService sysLogService;

    @Value("${com.cpiinfo.iot.sys-log.keep-days:180}")
    private int keepDays;

    /**
     *  每90天执行一次
     */
    @Scheduled(fixedDelay = 90 * 24 * 60 * 60 * 1000l)
    public void timingExe() {
        logger.info("------------- SysLogTask -------------");
        sysLogService.timingExe(keepDays);
    }

}
