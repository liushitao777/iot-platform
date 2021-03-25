package com.cpiinfo.authnz.service;

import com.cpiinfo.sysmgt.api.model.SysUser;
import com.cpiinfo.sysmgt.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 登陆成功监听
 *
 */
@Component
public class AuthenticationSuccessEventListener  implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private SysUserService userService;
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        UserDetails userDetails = (UserDetails) authenticationSuccessEvent.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        if(username.split(",").length>1){
            userService.updateMobileUserLoginTime(username.split(",")[0],null,new Date());
        }else {
            SysUser user = userService.queryUserByName(username);
            if (user != null) {
                user.setActive(true);
                user.setUserName(username);
                user.setFailsCount(0);
                userService.updateUser(username, user);
            }
        }
    }
}
