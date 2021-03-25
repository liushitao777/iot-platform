package com.cpiinfo.authnz.mobile;

import com.cpiinfo.authnz.model.CustomUserDetails;
import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.authnz.util.JacksonUtil;
import com.cpiinfo.sysmgt.api.model.SysUser;
import com.cpiinfo.sysmgt.api.service.SysUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName
 * @Deacription TODO
 * @Author wuchangjiang
 * @Date 2020年11月04日 15:10
 * @Version 1.0
 **/
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final Log log = LogFactory.getLog(CustomizeLogoutSuccessHandler.class);
    @Autowired
    private SysUserService userService;
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        ServiceResponse respObj = new ServiceResponse(true, "用户退出登录成功!", 0, null);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletRequest.getSession(true).invalidate();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetail = (CustomUserDetails)authentication.getPrincipal();
            SysUser sysUser = userDetail.getSysUser();
            //计算用户登录时长--移动端
            if("outer".equals(sysUser.getLoginType())){
                Long totalLoginTime = sysUser.getTotalLoginTime()==null?0L:sysUser.getTotalLoginTime();
                Date lastLoginTime = sysUser.getLastLoginTime();
                if(lastLoginTime!=null){
                    Date now = new Date();
                    totalLoginTime += (now.getTime() - lastLoginTime.getTime())/1000;
                }
                sysUser.setTotalLoginTime(totalLoginTime);
                userService.updateMobileUserLoginTime(sysUser.getUserCode(),sysUser.getTotalLoginTime(),null);
            }
        }
        httpServletResponse.getWriter().write(JacksonUtil.toJson(respObj));

    }
}
