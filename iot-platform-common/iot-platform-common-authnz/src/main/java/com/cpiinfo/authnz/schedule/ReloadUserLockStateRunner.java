package com.cpiinfo.authnz.schedule;


import com.cpiinfo.sysmgt.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReloadUserLockStateRunner {
    @Autowired
    private SysUserService userService;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void execute() {
        //每隔半小时重置登录错误次数
        userService.reloadFailsCount();
    }
}
