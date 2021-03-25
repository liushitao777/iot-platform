package com.cpiinfo.sysmgt.controller;

import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.sysmgt.dto.QueryUserByCodeDTO;
import com.cpiinfo.sysmgt.dto.UpdatePwdDTO;
import com.cpiinfo.sysmgt.dto.UserListDTO;
import com.cpiinfo.sysmgt.dto.pageUserListDTO;
import com.cpiinfo.sysmgt.entity.User;
import com.cpiinfo.sysmgt.service.UserService;
import com.cpiinfo.sysmgt.utils.PageData;
import com.cpiinfo.sysmgt.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @Title: UserController.java
* @Package com.cpiinfo.iisp.manager.controller
* @Description: TODO(定义员工的controller层)
* @author 牛棚
* @date 2016年8月26日 下午5:23:11
* @version V1.0
*/

@Controller
@RequestMapping(value="/user")
@Api(value="sys-mgt:用户管理",tags={"sys-mgt:用户管理"})
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
    UserService userService;

	/**
	 * @Title: userList
	 * @Description: TODO(分页查询员工列表)
	 * @return
	 */
	@ApiOperation(value = "分页查询员工列表", notes = "分页查询员工列表")
	@RequestMapping(value="/pageUserList", method=RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
			@ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "orgCode", value = "单位编码", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "userName", value = "用户姓名", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "roleId", value = "角色id", paramType = "query", dataType = "String")
	})
	@ResponseBody
	public Result<PageData<UserListDTO>> pageUserList(@ApiIgnore pageUserListDTO dto){
		return userService.pageUserList(dto);
	}

	/**
	 * @Title: queryUserById
	 * @Description: TODO(根据code查询员工)
	 * @return
	 */
	@ApiOperation(value = "根据code查询员工", notes = "根据code查询员工")
	@RequestMapping(value="/queryUserByCode", method=RequestMethod.GET)
	@ResponseBody
	public Result<QueryUserByCodeDTO> queryUserByCode(@RequestParam(value = "userCode",required = true) String userCode){
		QueryUserByCodeDTO user =userService.queryUserByCode(userCode);
		if(user != null){
			return new Result<QueryUserByCodeDTO>().ok(user);
		}else{
			return new Result<QueryUserByCodeDTO>().error("无记录!");
		}

	}

	/**
	 * @Title: addUser
	 * @Description: TODO(添加员工)
	 * @param user
	 * @return
	 */
	@ApiOperation(value = "添加员工", notes = "添加员工")
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	@ResponseBody
	public Result addUser(@RequestBody User user) {
		String msg = userService.addUser(user);
		if(msg == null || "".equals(msg)) {
			return new Result<String>().ok(msg);
		}
		else {
			return new Result<String>().error(msg);
		}
	}

	/**
	 * @Title: delUserById
	 * @Description: TODO(根据id删除员工)
	 * @return
	 */
	@ApiOperation(value = "根据id删除员工", notes = "根据id删除员工")
	@RequestMapping(value="/delUserById", method=RequestMethod.DELETE)
	@ResponseBody
	public Result delUserById(String id){
		userService.delUserById(id);
		return new Result<>().ok("删除成功!");
	}

	/**
	 * @Title: updateUser
	 * @Description: TODO(根据id修改员工信息)
	 * @param user
	 * @return
	 */
	@ApiOperation(value = "根据id修改员工信息", notes = "根据id修改员工信息")
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	@ResponseBody
	public Result updateUser(@RequestBody User user){
		return	userService.updateUser(user);
	}

	/**
	* @Title: userList
	* @Description: TODO(根据单位编号查询员工列表)
	* @param request
	* @return
	*/
	@RequestMapping(value="/userList", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResponse userList(HttpServletRequest request){
 		String unitCode = request.getParameter("unitCode");
		String currentPage = request.getParameter("current");
		String showCount = request.getParameter("showRows");
		String username = request.getParameter("username");
		List<User> list = userService.userList(unitCode, currentPage, showCount, username);
		int totalCount = userService.getUserCountByOrgCode(unitCode, username);
		return new ServiceResponse(true, "ok", totalCount, list);
	}
	
	

	
	
	/** 
	* @Title: queryUserById 
	* @Description: TODO(根据id查询员工) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/queryUserById", method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse queryUserById(HttpServletRequest request){
		String id = request.getParameter("id");
		User user =userService.queryUserById(id);
		return new ServiceResponse(true, "ok", 1, user);
	}
	

	

	

	
	 
	/** 
	* @Title: disableUserById 
	* @Description: TODO(根据id改变用户状态----禁用 ) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/disableUserById", method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse disableUserById(HttpServletRequest request){
		String id = request.getParameter("id");
		userService.disableUserByid(id);
		return new ServiceResponse(true, "ok", 1, null);
	}
	
	/** 
	* @Title: enableUserById 
	* @Description: TODO(根据id改变用户状态----正常) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/enableUserById", method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse enableUserById(HttpServletRequest request){
		String id = request.getParameter("id");
		userService.enableUserByid(id);
		return new ServiceResponse(true, "ok", 1, null);
	}
	
	/** 
	* @Title: addUserAndRole 
	* @Description: TODO(给员工添加角色) 
	* @param request
	* @return
	*/
	@RequestMapping(value=("/addUserAndRole"), method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse addUserAndRole(HttpServletRequest request){
		String userId=request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		userService.addUserAndRole(userId, roleId);
		return new ServiceResponse(true, "ok", 1, null);
	}
	
	/** 
	* @Title: queryUserListByRoleId 
	* @Description: TODO(查看相应角色下的用户列表) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/queryUserListByRoleId", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResponse queryUserListByRoleId(HttpServletRequest request){
		String roleId = request.getParameter("roleId");
		String currentPage = request.getParameter("current");
		String showCount = request.getParameter("showRows");
		String userName = request.getParameter("userName");
		List<User> list = userService.queryUserListByRoleId(roleId, currentPage, showCount, userName);
		int totalCount = userService.getUserCountByRoleId(roleId, userName);
		return new ServiceResponse(true, "ok", totalCount, list);
	}
	
	/** 
	* @Title: queryUserByEmail 
	* @Description: TODO(根据email查询员工) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/queryUserByEmail", method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse queryUserByEmail(HttpServletRequest request){
		String email = request.getParameter("email");
		User user = userService.queryUserByEmail(email);
		if(user == null){
			return new ServiceResponse(true, "ok", 0, null);
		}else{
			return new ServiceResponse(false, "邮箱已存在！", 1, user);
		}
		
	}
	
	/** 
	* @Title: initPwd 
	* @Description: TODO(初始化密码:123456)
	* @return
	*/
	@ApiOperation(value = "初始化密码:123456", notes = "初始化密码:123456")
	@RequestMapping(value="/initPwd", method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse initPwd(HttpServletRequest request){
		String userId = request.getParameter("userId");
		userService.initPwd(userId);
		return new ServiceResponse(true, "初始化密码成功！", 0, null);
		
	}
	
	/** 
	* @Title: delUsersRole 
	* @Description: TODO(根据用户id和角色id删除用户的角色) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/delUsersRole", method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse delUsersRole(HttpServletRequest request){
		String userId = request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		userService.delUsersRole(userId, roleId);
		return new ServiceResponse(true, "删除成功！", 0, null);
		
	}
	
	@RequestMapping(value="/rolesUserListByPage", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResponse rolesUserListByPage(HttpServletRequest request){
		String unitCode = request.getParameter("unitCode");
		String currentPage = request.getParameter("current");
		String showCount = request.getParameter("showRows");
		String username = request.getParameter("username");
		String roleId = request.getParameter("roleId");
		List<User> list = userService.rolesUserListByPage(unitCode, currentPage, showCount, username, roleId);
		int totalCount = userService.getRolesUserListCount(unitCode, username, roleId);
		return new ServiceResponse(true, "ok", totalCount, list);
	}
	
	/** 
	* @Title: addRoleAndUser 
	* @Description: TODO(给角色批量添加用户) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/addRoleAndUser", method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse addRoleAndUser(HttpServletRequest request){
		String userId = request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		userService.plAddRoleAndUser(userId, roleId);
		return new ServiceResponse(true, "ok", 0, null);
	}

	@ApiOperation(value = "修改密码", notes = "修改密码")
	@RequestMapping(value="/modPwd", method=RequestMethod.POST)
	@ResponseBody
	public Result modPwd(@RequestBody UpdatePwdDTO dto) {
		return userService.modifyUserPwd(dto.getUserCode(),dto.getNewPwd(),dto.getOldPwd());
	}
	
	@RequestMapping(value="/importSaveUser", method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse importSaveUser(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "file") MultipartFile uploadFile){
		String orgCode = request.getParameter("orgCode");
		String path=request.getServletContext().getRealPath("/upload");
		int count = userService.importSaveUser(path, uploadFile, orgCode);
		/*if(count > 0){
			return new ServiceResponse(true, "ok",count, null);
		}else{
			return new ServiceResponse(false, "导入失败", 0, null);
		}*/
		return new ServiceResponse(true, "ok",count, null);
	}
	
	/** 
	* @Title: batchAddUser 
	* @Description: TODO(批量添加200用户) 
	* @return
	*/
	@RequestMapping(value="/batchAddUser", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResponse batchAddUser(){
		userService.batchAddUser();
		return new ServiceResponse(true, "批量添加用户成功！", 0, null);
		
	}
	
	/***
	 * 
	 * @Description:分页例子
	 * @return
	 * @author: XUJUN
	 * @time:2017年3月22日 下午2:03:12
	 */
	@RequestMapping(value="/qu", method=RequestMethod.GET)
	@ResponseBody
	public String pageQuy(){
		/*
		PageQuery page = new PageQuery();
		//配置分页参数  ，前端传入pageNO,pageSize
		page.setPageNo(1);
		page.setPageSize(2);
		//条件查询，传参
		User user = new User();
		user.setUserCode("ad");

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user", user);//这儿是在mapper给sql语句赋值的，此例子接受指为 #{user.userCode}
		map.put("page", page);//这儿是个mybatis的拦截器用的。

		List<User> list = userService.queryuserby(map);
		//page.setData(list);
		logger.debug(JacksonUtil.toJSon(list)+"----"+list.size()+"====page="+page.getTotalRecord()+",pagetotal="+page.getTotalPage());
		//*/
		return null;
		
	}
	
	/** 
	* @Title: userList 
	* @Description: TODO(根据部门查询员工列表) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/userListByDepart", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResponse userListByDepart(HttpServletRequest request){
 		String unitCode = request.getParameter("unitCode");
		String currentPage = request.getParameter("current");
		String showCount = request.getParameter("showRows");
		String username = request.getParameter("username");
		List<User> list = userService.userListByDepart(unitCode, currentPage, showCount, username);
		int totalCount = userService.getUserCountByOrgCodeDepart(unitCode, username);
		return new ServiceResponse(true, "ok", totalCount, list);
	}
	
	/** 
	* @Title: userList 
	* @Description: TODO(根据组织查询员工列表) 
	* @param request
	* @return
	*/
	@RequestMapping(value="/userListByOrg", method=RequestMethod.GET)
	@ResponseBody
	public ServiceResponse userListByOrg(HttpServletRequest request){
 		String unitCode = request.getParameter("unitCode");
		String currentPage = request.getParameter("current");
		String showCount = request.getParameter("showRows");
		String username = request.getParameter("username");
		List<User> list = userService.userListByOrg(unitCode, currentPage, showCount, username);
		int totalCount = userService.getUserCountByOrgCodeOrg(unitCode, username);
		return new ServiceResponse(true, "ok", totalCount, list);
	}
	
	/**
	 * @Title: login
	 * @Description: 前台登陆业务处理
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserByCodeAndPass", method = RequestMethod.POST)
	@ResponseBody
	public ServiceResponse queryUserByCodeAndPass(HttpServletRequest request, HttpServletResponse response, @RequestParam String userCode, @RequestParam String password, @RequestParam String code) {
		User userInfo = null;
		try {
			logger.info("user login, usercode:"+userCode);
			userCode = userCode.toLowerCase();//用户名统一转小写
			userInfo = userService.queryUserByCodeAndPass(userCode, password);
		} catch (Exception e) {
			logger.error("查询失败", e);
		}
		if (userInfo == null) {
			return new ServiceResponse(false, "用户名或密码不正确，请重新输入", 0, null);
		} else {
			return new ServiceResponse(true, "ok", 1, userInfo);
		}
	}
	
}
