package com.cpiinfo.sysmgt.controller;

import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.sysmgt.entity.Dictionary;
import com.cpiinfo.sysmgt.entity.Menu;
import com.cpiinfo.sysmgt.entity.Oper;
import com.cpiinfo.sysmgt.entity.ResourceVo;
import com.cpiinfo.sysmgt.service.ResourceService;
import com.cpiinfo.sysmgt.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Title: ResourceController.java
 * @Package com.cpiinfo.iisp.manager.controller
 * @Description: TODO(资源管理控制器类)
 * @author 孙德
 * @date 2016年8月31日 下午3:20:01
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/resource")
@Api(value="sys-mgt:菜单资源配置",tags={"sys-mgt:菜单资源配置"})
public class ResourceController {

	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

	// 依赖注入资源管理服务接口
	@Autowired
    ResourceService resourceService;

	/**
	 * 
	 * @Title: queryResource
	 * @Description: TODO(查询所有资源信息)
	 * @param req
	 * @param resp
	 * @return
	 */
	@ApiOperation(value = "查询所有资源信息", notes = "查询所有资源信息")
	@RequestMapping(value = "/queryResource", method=RequestMethod.GET)
	@ResponseBody
	public Result queryResource() {
		return resourceService.queryResource();
	}
	
	/**
	 * 
	* @Title: queryResourceByPid 
	* @Description: TODO(根据父ID查询子菜单) 
	* @param req
	* @param resp
	* @return
	 */
	@RequestMapping(value = "/queryResourceByPid", method=RequestMethod.GET)
	public @ResponseBody
    ServiceResponse queryResourceByPid(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> paras = new HashMap<String,Object>();
		paras.put("currentPage", req.getParameter("current")!=null?req.getParameter("current"):null);
		paras.put("showCount",req.getParameter("showRows")!=null?Integer.parseInt(req.getParameter("showRows")):null);
		if (paras.get("currentPage")!=null&&paras.get("showCount")!=null) {
			int beginCount = (Integer.parseInt(paras.get("currentPage").toString()) -1) * Integer.parseInt(paras.get("showCount").toString());
			paras.put("beginCount", beginCount);
		}else {
			paras.put("beginCount", null);
		}
		paras.put("pId", req.getParameter("id"));
		ServiceResponse result = null;
		List<Menu> data = resourceService.queryResourceByPid(paras);
		int totalCount = resourceService.getResourceCountByPid(paras.get("pId").toString());
		result = new ServiceResponse(true, "query success", totalCount, data);
		logger.info(" info :query success");
		return result;
	}
	
	/**
	 * 
	* @Title: addResource 
	* @Description: TODO(添加菜单资源) 
	* @param
	* @param
	* @return
	 */
	@RequestMapping(value = "/addResource", method = RequestMethod.POST)
	public @ResponseBody ServiceResponse addResource(@RequestBody Menu menu ) {
		ServiceResponse result = null;
		String data = resourceService.addResource(menu);
		result = new ServiceResponse(true, "add success ", 0, data);
		logger.info(" info :add success");
		return result;
	}

	/**
	 * 
	* @Title: updateResource 
	* @Description: TODO(更新菜单资源) 
	* @param
	* @param
	* @return
	 */
	@RequestMapping(value = "/updateResource", method = RequestMethod.PUT)
	public @ResponseBody ServiceResponse updateResource(@RequestBody Menu menu) {
		ServiceResponse result = null;
		String data = resourceService.updateResource(menu);
		result = new ServiceResponse(true, "add success ", 0, data);
		logger.info(" info :update success");
		return result;
	}
	
	/**
	 * 
	* @Title: deleResource 
	* @Description: TODO(根据资源ID删除资源数据) 
	* @param menuId
	* @return
	 */
	@RequestMapping(value = "/deleResource", method = RequestMethod.DELETE)
	public @ResponseBody ServiceResponse deleResource(@RequestParam String menuId) {
		ServiceResponse result = null;
		String data = resourceService.deleResource(menuId);
		result = new ServiceResponse(true, "add success ", 0, data);
		logger.info(" info :delete success");
		return result;
	}
	
	
	/**
	 * 
	* @Title: queryResourceById 
	* @Description: TODO(根据菜单id查询菜单详情) 
	* @param
	* @param
	* @return
	 */
	@RequestMapping(value = "/queryResourceById", method = RequestMethod.GET)
	public @ResponseBody ServiceResponse queryResourceById(@RequestParam String menuId) {
		ServiceResponse result = null;
		Menu data = resourceService.queryResourceById(menuId);
		result = new ServiceResponse(true, "add success ", 0, data);
		logger.info(" info :query success");
		return result;
	}
	
	/**
	 * 
	* @Title: queryResourceOper 
	* @Description: TODO(查询操作字典表数据) 
	* @param req
	* @param resp
	* @return
	 */
	@RequestMapping(value = "/queryResourceOper", method = RequestMethod.GET)
	public @ResponseBody ServiceResponse queryResourceOper(HttpServletRequest req, HttpServletResponse resp) {
		ServiceResponse result = null;
		List<Oper> data = resourceService.queryResourceOper();
		result = new ServiceResponse(true, "query success", data.size(), data);
		logger.info(" info :query success");
		return result;
	}
	
	/** 
	* @Title: querySourceOpt 
	* @Description: TODO(添加单位时的主要能源列表) 
	* @return
	*/
	@RequestMapping(value = "/querySourceOpt", method = RequestMethod.POST)
	@ResponseBody
	public ServiceResponse querySourceOpt() {
		List<Dictionary> list = resourceService.querySourceOpt();
		logger.info(" info :query success");
		return new ServiceResponse(true, "ok", list.size(), list);
	}
	
	@RequestMapping(value = "/getfun")
	@ResponseBody
	public ServiceResponse querytreenode(){
		List<ResourceVo> list = this.resourceService.getbasefun();
		return new ServiceResponse(true, "ok", list.size(), list);
	}
	
	/**
	 * 
	* @Title: deleAllResource 
	* @Description: TODO(根据资源ID递归删除资源数据) 
	* @param menuId
	* @return
	 */
	@RequestMapping(value = "/deleAllResource", method = RequestMethod.DELETE)
	public @ResponseBody ServiceResponse deleAllResource(@RequestParam String menuId) {
		ServiceResponse result = null;
		String data = resourceService.deleAllResource(menuId);
		result = new ServiceResponse(true, "add success ", 0, data);
		logger.info(" info :delete success");
		return result;
	}
}
