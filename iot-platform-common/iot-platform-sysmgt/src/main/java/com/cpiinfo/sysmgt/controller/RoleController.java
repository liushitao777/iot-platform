package com.cpiinfo.sysmgt.controller;

import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.sysmgt.dto.RoleAndResourceDTO;
import com.cpiinfo.sysmgt.dto.RoleDTO;
import com.cpiinfo.sysmgt.entity.Oper;
import com.cpiinfo.sysmgt.entity.Role;
import com.cpiinfo.sysmgt.entity.ZNode;
import com.cpiinfo.sysmgt.service.OrganizationService;
import com.cpiinfo.sysmgt.service.RoleService;
import com.cpiinfo.sysmgt.utils.PageData;
import com.cpiinfo.sysmgt.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**   
 * @Title: RoleController.java 
 * @Package com.cpiinfo.iisp.manager.controller
 * @Description: TODO(角色roleController) 
 * @author 牛棚
 * @date 2016年8月26日 下午5:30:09 
 * @version V1.0   
 */
@Controller
@RequestMapping(value = "/role")
@Api(value="sys-mgt:角色管理",tags={"sys-mgt:角色管理"})
public class RoleController {

	
	@Autowired
    RoleService roleService;
	@Autowired
    OrganizationService orgService;
	
	/** 
	* @Title: querytRoleByRoleId 
	* @Description: TODO(查询角色列表)
	* @return
	*/
	@ApiOperation(value = "查询角色列表,可分页可不分页", notes = "查询角色列表,可分页可不分页")
	@RequestMapping(value = "/pageRole", method = RequestMethod.GET)
	@ResponseBody
	public Result<PageData<RoleAndResourceDTO>> pageRole(@RequestParam (value = "roleName",required = false)String roleName,
                                                         @RequestParam (value = "page" ,required = false)String page,
                                                         @RequestParam (value = "limit" ,required = false)String limit) {
		return roleService.pageRole(roleName,page,limit);
	}

	/**
	 * @Title: addRole
	 * @Description: TODO(新增或者修改角色信息)
	 * @param role
	 * @return
	 */
	@ApiOperation(value = "新增或者修改角色信息", notes = "新增或者修改角色信息")
	@RequestMapping(value="/addOrUpdateRole", method = RequestMethod.POST)
	@ResponseBody
	public Result addOrUpdateRole(@RequestBody RoleDTO role){
		roleService.addRole(role);
		return new Result().ok("操作成功!");
	}

	/**
	 * @Title: delRoleById
	 * @Description: TODO(根据id删除角色)
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据id删除角色,多个以,隔开", notes = "根据id删除角色,多个以,隔开")
	@RequestMapping(value="/delRoleById", method = RequestMethod.DELETE)
	@ResponseBody
	public Result delRoleById(HttpServletRequest request,@RequestParam String id){
		return roleService.delRoleById(id);
	}


	/**
	 * @Title: querytRoleByRoleId
	 * @Description: TODO(查询角色)
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/queryRole", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse querytRoleByRoleId(@RequestParam String roleId) {
		Role role = roleService.queryRoleByRoleId(roleId);
		return new ServiceResponse(true, "ok", 1, role);
	}
	
	/**
	* @Title: listRole
	* @Description: TODO(根据管理员id查询角色列表)
	* @param
	* @return
	*/
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	@ResponseBody
//	public ServiceResponse listRole(HttpServletRequest request) {
//		String unitCode = request.getParameter("unitCode");
//		String currentPage = request.getParameter("current");
//		String showCount = request.getParameter("showRows");
//		String roleName = request.getParameter("roleName");
//		String userCode = request.getParameter("userCode");
//		System.out.println(userCode);
//		List<ZNode> list = orgService.queryUserAllOrg(userCode);
//		//List<Role> roles = roleService.listRoles(unitCode, currentPage, showCount, roleName);
//		//int totalCount = roleService.getRoleCount(unitCode, roleName);
//		List<String> orgCode = new ArrayList<String>();
//		for (ZNode zNode : list) {
//			orgCode.add(zNode.getId());
//		}
//		List<Role> roles = roleService.listRolesByOrgCode(orgCode, currentPage, showCount, roleName);
//		int totalCount = roleService.getRoleCountByOrgCode(orgCode, roleName);
//		return new ServiceResponse(true, "ok", totalCount, roles);
//	}
	
	/** 
	* @Title: queryRoleListByOrgCode 
	* @Description: TODO(根据单位查询角色列表) 
	* @param request
	* @return
	*/
	@RequestMapping(value = "/queryRoleListByOrgCode", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse queryRoleListByOrgCode(HttpServletRequest request) {
		String orgCode = request.getParameter("orgCode");
		List<Role> roles = roleService.queryRoleListByOrgCode(orgCode);
		return new ServiceResponse(true, "ok", roles.size(), roles);
	}
	
	/** 
	* @Title: listMenuByRoleId 
	* @Description: TODO(员工的资源列表) 
	* @param
	* @param roleId
	* @return
	*/
	@RequestMapping(value = "/listMenu", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse listMenuByRoleId(@RequestParam String adminCode, @RequestParam String roleId, @RequestParam String userCode) {
		List<ZNode> zNodes = roleService.listMenusByRoleId(adminCode, roleId,userCode);
		return new ServiceResponse(true, "ok", zNodes.size(), zNodes);
	}
	
	/** 
	* @Title: updateMenus 
	* @Description: TODO(修改角色所拥有的权限) 
	* @param paramMap
	* @return
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateMenus", method = RequestMethod.PUT)
	@ResponseBody
	public ServiceResponse updateMenusByRoleId(@RequestBody Map<String, Object> paramMap) {
		String roleId = (String)paramMap.get("roleId");
		List<Map<String,Object>> menus =(List<Map<String,Object>>) paramMap.get("menus");
		roleService.updateMenusByRoleId(roleId, menus);
		return new ServiceResponse(true, "ok", 1, null);
	}
	
	/** 
	* @Title: queryRolesByUserId 
	* @Description: TODO(查询用户的角色) 
	* @param request
	* @return
	*/
	@RequestMapping(value = "/queryRolesByUserId", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse queryRolesByUserId(HttpServletRequest request) {
		String userId=request.getParameter("userId");
		List<ZNode> zNodes = roleService.queryRolesByUserId(userId);
		return new ServiceResponse(true, "ok", 1, zNodes);
	}
	

	
	/** 
	* @Title: queryRoleById 
	* @Description: TODO(根据id查询角色) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/queryRoleById", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse queryRoleById(HttpServletRequest request){
		String roleId= request.getParameter("roleId");
		Role role = roleService.queryRoleById(roleId);
		return new ServiceResponse(true, "ok", 1, role);
	}

	

	
	/** 
	* @Title: queryOperListByRoleAndResourceId 
	* @Description: TODO(根据角色id和资源id查询操作列表) 
	* @return
	*/
	@RequestMapping(value="/queryOperListByRoleAndResourceId", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse queryOperListByRoleAndResourceId(HttpServletRequest request){
		String userCode = request.getParameter("userCode");
		String roleId = request.getParameter("roleId");
		String resourceId = request.getParameter("resourceId");
		List <ZNode> list = roleService.queryOperListByRoleAndResourceId(userCode, roleId, resourceId);
		return new ServiceResponse(true, "ok", list == null ? 0 : list.size(), list);
	}
	
	/** 
	* @Title: queryOperatePermission 
	* @Description: 根据人员和菜单查询相应的操作按钮权限 
	* @return
	*/
	@RequestMapping(value="/queryOperatePermission", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse queryOperatePrivs(HttpServletRequest request){
		String userCode = request.getParameter("userCode");
		String menuUrl = request.getParameter("menuUrl");
		List <Oper> list = roleService.queryOperatePermission(userCode, menuUrl);
		return new ServiceResponse(true, "ok", list == null ? 0 : list.size(), list);
	}

	/** 
	* @Title: queryResAndOptList 
	* @Description: TODO(查询角色下的资源级操作列表) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/queryResAndOptList", method = RequestMethod.GET)
	@ResponseBody
	public ServiceResponse queryResAndOptList(HttpServletRequest request){
		String roleId = request.getParameter("roleId");
		List<Map<String,Object>> list = roleService.queryResAndOptList(roleId);
		return new ServiceResponse(true, "ok", list.size(), list);
	}

}
