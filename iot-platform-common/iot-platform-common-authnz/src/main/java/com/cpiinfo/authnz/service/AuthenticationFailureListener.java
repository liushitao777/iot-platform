package com.cpiinfo.authnz.service;


import com.cpiinfo.sysmgt.api.model.SysUser;
import com.cpiinfo.sysmgt.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 登陆失败监听
 *
 */
@Component
public class AuthenticationFailureListener  implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private SysUserService userService;
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent ) {
        //在这里记录登陆失败的次数
        String username = authenticationFailureBadCredentialsEvent.getAuthentication().getPrincipal().toString();
        SysUser user = userService.queryUserByName(username);
        if (user != null) {
            // 用户失败次数
            Integer fails = user.getFailsCount();
            if (fails == null) {
                fails = 0;
            }
            fails++;
            // 超出失败5次，停用账户
            if (fails >= 5) {
                user.setActive(false);
                user.setFailsCount(fails);
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.MINUTE, 30);
                user.setLockEndTime(c.getTime());
                userService.updateUser(username, user);
            } else {
               user.setFailsCount(fails);
                userService.updateUser(username, user);
            }
        }
    }


}
