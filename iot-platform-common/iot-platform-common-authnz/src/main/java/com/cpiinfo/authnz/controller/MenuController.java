package com.cpiinfo.authnz.controller;

import com.cpiinfo.authnz.model.MenuItem;
import com.cpiinfo.authnz.service.UserResourceService;
import com.cpiinfo.iot.exception.ServiceException;
import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.sysmgt.api.model.SysResource;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private UserResourceService userResourceService;

    @ApiOperation(value = "加载用户有权限的菜单列表", notes = "用户登录后，显示用户有权限操作的菜单列表", response = MenuItem.class)
    @ApiImplicitParams({
	    	@ApiImplicitParam(name = "systemCode", value = "指定要取得用户菜单的系统编号，对应菜单表中系统编号字段，不指定取全部",
	                required = false, paramType = "query", dataType = "string", defaultValue = "false"),
            @ApiImplicitParam(name = "includeButtons", value = "回传的数据中是否包括界面上用户有权限的按钮，默认不包含",
                    required = false, paramType = "query", dataType = "string", defaultValue = "false")
    })
    @RequestMapping(value = "/loadUserMenus", method = {RequestMethod.GET})
    public ServiceResponse loadUserMenus(
    					@RequestParam(value = "systemCode", required = false) String systemCode,
    					@RequestParam(value = "includeButtons", required = false) String includeButtons,
                        @RequestParam(value = "judgeUser", required = false ,defaultValue = "true") Boolean judgeUser) throws IOException {
        try {
            ServiceResponse result = userResourceService.loadUserMenus(systemCode, includeButtons ,judgeUser);
            return result != null ? result : new ServiceResponse(false, "", 0, null);
        }catch(Exception e){
            logger.error("error in MenuController.loadUserMenus...", e);
            if(e instanceof ServiceException){
                return new ServiceResponse(false, ((ServiceException)e).getMessage(), 0, null);
            }
            return new ServiceResponse(false, "", 0, null);
        }
    }

    @ApiOperation(value = "根据菜单id查询该页面有权限的按钮", notes = "根据菜单id查询该页面有权限的按钮", response = MenuItem.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "根据菜单id查询该页面有权限的按钮",
                    required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/getButtonByMenusUrl", method = {RequestMethod.GET})
    public ServiceResponse getButtonByMenusUrl(@RequestParam(value = "id", required = true) String id)  {
        List<SysResource> buttonByMenusUrl = userResourceService.getButtonByMenusUrl(id);
        return new ServiceResponse(true,"success",1,buttonByMenusUrl);
    }
}
