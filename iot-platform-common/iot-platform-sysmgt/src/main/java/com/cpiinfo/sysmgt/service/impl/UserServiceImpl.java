package com.cpiinfo.sysmgt.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.DigestUtil;
import com.cpiinfo.iot.exception.ServiceException;
import com.cpiinfo.iot.mybatis.dto.PageRequest;
import com.cpiinfo.iot.utility.DateUtils;
import com.cpiinfo.sysmgt.api.listener.SysUserChangeListener;
import com.cpiinfo.sysmgt.utils.ExcelUtil;
import com.cpiinfo.sysmgt.dao.MenuMapper;
import com.cpiinfo.sysmgt.dao.OrganizationMapper;
import com.cpiinfo.sysmgt.dao.RoleMapper;
import com.cpiinfo.sysmgt.dao.UserMapper;
import com.cpiinfo.sysmgt.dto.QueryUserByCodeDTO;
import com.cpiinfo.sysmgt.dto.UserListDTO;
import com.cpiinfo.sysmgt.dto.pageUserListDTO;
import com.cpiinfo.sysmgt.entity.*;
import com.cpiinfo.sysmgt.service.DepartService;
import com.cpiinfo.sysmgt.service.UserService;
import com.cpiinfo.sysmgt.utils.DESEncrypt;
import com.cpiinfo.sysmgt.utils.PageData;
import com.cpiinfo.sysmgt.utils.Result;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, ApplicationContextAware {
	public static final String PW_PATTERN = "^[A-Za-z0-9]{1}(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9a-zA-Z)]|[\\W])+$)([^(0-9a-zA-Z)]|[\\W]|[a-zA-Z]|[0-9]){8,}$";

	private ApplicationContext ctx;

	@Autowired
    RoleMapper roleMapper;
	@Autowired
    OrganizationMapper organizationMapper;
	@Autowired
    MenuMapper menuMapper;
	@Autowired
    UserMapper userMapper;
	@Autowired
    DepartService iDepartService;
	@Autowired
	OrganizationMapper org;

	/* (非 Javadoc) 
	* Title: addUser
	* TODO(添加员工)
	* @param user
	* @throws NoSuchAlgorithmException 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#addUser(com.cpiinfo.iisp.manager.entity.User)
	*/
	@Override
	public String addUser(User user){
		try {
			User dbUser = userMapper.findByUserCode(user.getUserCode());
			if(dbUser != null) {
				return "指定编码的用户已存在。";
			}
			user.setUsername(user.getUsername().toLowerCase());
			String pwd = user.getUserpwd();
			String userId  = UUID.randomUUID().toString().replace("-", "");
			user.setId(userId);
			user.setState("1");
			user.setUserpwd(encodeUserPwd(pwd));
			user.setMd5Pwd(DESEncrypt.desEncode(pwd));
			user.setMakerPassword(Base64.encode(DigestUtil.sha256(pwd)));
			user.setUpdateTime(DateUtils.now());
			user.setLastChangePassword(DateUtils.now());
			userMapper.addUser(user);
			//添加用户角色表
			addUserAndRole(user.getId(),user.getUserType());
		}catch(Exception ex) {
			throw new ServiceException("addUser failured.", ex);
		}
		return null;
	}

	/* (非 Javadoc) 
	* Title: queryUserById
	* TODO(根据id查询员工)
	* @param id
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#queryUserById(java.lang.String)
	*/
	@Override
	public User queryUserById(String id) {
		User user = userMapper.queryUserById(id);
		return user;
	}
	
	/* (非 Javadoc) 
	* Title: queryUserByCode
	* TODO(根据编号查询员工)
	* @param userCode
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#queryUserByCode(java.lang.String)
	*/
	@Override
	public QueryUserByCodeDTO queryUserByCode(String userCode) {
		User user = userMapper.queryUserByCode(userCode);
		QueryUserByCodeDTO userr= new QueryUserByCodeDTO();
		if(user!=null){
			BeanUtils.copyProperties(user,userr);
			userr.setRoleName(getRoleNameById(user.getUserType()));
		}
		return userr;
	}


	/* (非 Javadoc) 
	* Title: updateUser
	* TODO(修改员工)
	* @param user 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#updateUser(com.cpiinfo.iisp.manager.entity.User)
	*/
	@Override
	public Result updateUser(User user) {
		user.setUsername(user.getUsername().toLowerCase());
		if(StringUtils.isEmpty(user.getUserpwd())){
			user.setUserpwd(null);
		}else {
			user.setUserpwd(encodeUserPwd(user.getUserpwd()));
			user.setMd5Pwd(DESEncrypt.desEncode(user.getUserpwd()));
		}
		user.setUpdateTime(DateUtils.now());
		user.setState("1");
		userMapper.updateUser(user);
		//修改用户角色表
		addUserAndRole(user.getId(),user.getUserType());
		return new Result<String>().ok("修改成功!");
	}

	/* (非 Javadoc) 
	* Title: delUserById
	* TODO(删除员工)
	* @param id 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#delUserById(java.lang.String)
	*/
	@Override
	public void delUserById(String id) {
		String ids[] = id.split(",");
		for (String userId : ids) {
			userMapper.delUserById(userId);
			userMapper.delUserAndRoleByUserId(userId);
			//刪除用戶发kafka消息
			Map<String,Object> map = new HashMap<>();
			map.put("id", userId);
			notifySysUserChange(SysUserChangeListener.CHANGE_TYPE_DELETE_USER, map);
		}
	}

	@Override
	public Result modifyUserPwd(String userId, String newPwd, String oldPwd) {
		//验证密码复杂度
		if(!newPwd.matches(PW_PATTERN)){
			return new Result().error( "请输入8位以上由数字或字母开头至少为数字、大小写字母、特殊字符两种或两种以上的混合组合组成的密码!");
		}
		User user = queryUserById1(userId);
		if(user.getUsername().equals(newPwd)){
			return new Result().error( "用户名与密码禁止相同");
		}
		String userPwd = "";
		if(user!=null){
			userPwd=user.getUserpwd();
		}
		boolean b = verifyPwd(oldPwd,userPwd);
		if(b){
			String newPwdEncode =encodeUserPwd(newPwd);
			//md5密码和厂商密码一起修改
			String MD5Pwd = DESEncrypt.desEncode(newPwd);
			String  makerPwd= Base64.encode(DigestUtil.sha256(newPwd));
			int count = modPwd1(userId, newPwdEncode,MD5Pwd,makerPwd);
			if(count >0){
				//修改成后发kafka消息
				Map<String,Object> map = new HashMap<>();
				map.put("id", user.getId());
				map.put("userName", user.getUsername());
				map.put("oldMakerPassword", oldPwd);
				notifySysUserChange(SysUserChangeListener.CHANGE_TYPE_UPDATE_PASSWORD, map);
				return new Result().ok( "修改成功");
			}else{
				return new Result().ok("修改失败");
			}
		}else{
			return new Result().error( "旧密码不正确！");
		}
	}

	public int modPwd1(String userId, String newPwd,String MD5Pwd,String makerPwd) {
		Map<String,String> paramsMap = Maps.newHashMap();
		paramsMap.put("userId", userId);
		paramsMap.put("pwd", newPwd);
		paramsMap.put("MD5Pwd", MD5Pwd);
		paramsMap.put("makerPwd", makerPwd);
		int count  = userMapper.initPwd1(paramsMap);
		return count;
	}

	protected void notifySysUserChange(Integer changeType, Map<String, Object> params) {
		if(ctx != null) {
			Map<String, SysUserChangeListener> beans = ctx.getBeansOfType(SysUserChangeListener.class);
			if(beans != null && beans.size() > 0){
				for(Map.Entry<String, SysUserChangeListener> entry : beans.entrySet()){
					entry.getValue().sysUserChanged(changeType, params);
				}
			}
		}
	}

	private User queryUserById1(String userId) {
		User user = userMapper.queryUserById1(userId);
		return user;
	}

	/* (非 Javadoc) 
	* Title: disableUserByid
	* TODO(改变员工状态   禁用)
	* @param id 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#disableUserByid(java.lang.String)
	*/
	@Override
	public void disableUserByid(String id) {
		userMapper.disableUserById(id);
	}

	/* (非 Javadoc) 
	* Title: enableUserByid
	* TODO(改变员工状态   正常)
	* @param id 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#enableUserByid(java.lang.String)
	*/
	@Override
	public void enableUserByid(String id) {
		userMapper.enableUserById(id);	
	}

	/* (非 Javadoc) 
	* Title: userList
	* TODO(根据单位编号查询员工列表)
	* @param unitCode
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#userList(java.lang.String)
	*/
	@Override
	public List<User> userList(String unitCode, String currentPage, 
			String showCount, String username) {
		Map<String, Object> queryMap = parseMap(unitCode, currentPage, showCount, username);
		PageRequest pageRequest = new PageRequest(Integer.parseInt(currentPage), Integer.parseInt(showCount));
		if(StringUtils.isEmpty(username)){
			return userMapper.userList(queryMap, pageRequest);
		}else{
			return userMapper.userListForSearch(queryMap, pageRequest);
		}
	}
	
	/** 
	* @Title: parseMap 
	* @Description: TODO(封装分页map参数) 
	* @param unitCode
	* @param currentPage
	* @param showCount
	* @return
	*/
	private Map<String, Object> parseMap(String unitCode, String currentPage, 
			String showCount, String username){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("unitCode", unitCode);
		int beginCount = (Integer.parseInt(currentPage) -1) * Integer.parseInt(showCount);
		paramsMap.put("beginCount", beginCount);
		paramsMap.put("showCount", Integer.parseInt(showCount)+beginCount);
		if(!StringUtils.isEmpty(username)){
			paramsMap.put("username", username);
		}else{
			paramsMap.put("username", "");
		}
		return paramsMap;
	}

	/* (非 Javadoc) 
	* Title: addUserAndRole
	* TODO(给用户赋角色)
	* @param userId
	* @param roleId 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#addUserAndRole(java.lang.String, java.lang.String)
	*/
	@Override
	public void addUserAndRole(String userId, String roleId) {
		userMapper.delUserAndRoleByUserId(userId);
		if(roleId != null && !"".equals(roleId)){
			String roleIds[] = roleId.split(",");
			for (String role_id : roleIds) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("id", UUID.randomUUID().toString().replace("-", ""));
				paramMap.put("roleId", role_id);
				paramMap.put("userId", userId);
				userMapper.addUserAndRole(paramMap);
			}
		}
	}

	/* (非 Javadoc) 
	* Title: queryUserListByRoleId
	* TODO(查询角色下的用户列表)
	* @param roleId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#queryUserListByRoleId(java.lang.String)
	*/
	@Override
	public List<User> queryUserListByRoleId(String roleId, String currentPage, 
			String showCount, String userName) {
		Map<String, Object> paramsMap = parseRoleUserMap(roleId, currentPage, showCount, userName);
		return userMapper.queryUserListByRoleId(paramsMap, new PageRequest(Integer.parseInt(currentPage), Integer.parseInt(showCount)));
	}
	
	/** 
	* @Title: parseRoleUserMap 
	* @Description: TODO(封装map参数) 
	* @param currentPage
	* @param showCount
	* @return
	*/
	private Map<String, Object> parseRoleUserMap(String roleId, String currentPage, 
			String showCount, String userName){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("roleId", roleId);
		int beginCount = (Integer.parseInt(currentPage) -1) * Integer.parseInt(showCount);
		paramsMap.put("beginCount", beginCount);
		paramsMap.put("showCount", Integer.parseInt(showCount)+beginCount);
		if(!StringUtils.isEmpty(userName)){
			paramsMap.put("userName", userName);
		}else{
			paramsMap.put("userName", "");
		}
		return paramsMap;
	}

	/* (非 Javadoc) 
	* Title: getUserCountByOrgCode
	* TODO(根据单位code查询该单位下的员工总数)
	* @param orgCode
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#getUserCountByOrgCode(java.lang.String)
	*/
	@Override
	public int getUserCountByOrgCode(String orgCode, String username) {
		Map<String, String> queryMap = Maps.newHashMap();
		queryMap.put("orgCode", orgCode);
		int  count =0;
		if(!StringUtils.isEmpty(username)){
			queryMap.put("username", username);
			count = userMapper.getUserCountByOrgCodeForSearch(queryMap);
		}else{
			queryMap.put("username", "");
			count = userMapper.getUserCountByOrgCode(queryMap);
		}
		
		return count;
		
	}

	/* (非 Javadoc) 
	* Title: queryUserByEmail
	* TODO(根据email查询员工)
	* @param email
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#queryUserByEmail(java.lang.String)
	*/
	@Override
	public User queryUserByEmail(String email) {
		User user = userMapper.queryUserByEmail(email.toLowerCase());
		return user;
	}

	/* (非 Javadoc) 
	* Title: batchAddUser
	* TODO(批量添加用户) 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#batchAddUser()
	*/
	@Override
	public void batchAddUser() {
		try {
			for(int i=1; i<=200; i++){
				User user = new User();
				user.setUserCode("dlfzce"+i);
				user.setId(UUID.randomUUID().toString().replace("-", ""));
				user.setUsername("dlfzce"+i);
				user.setUserpwd("123456");
				user.setUserpwd(encodeUserPwd(user.getUserpwd()));
				user.setEmail("dlfzce"+i+"@jyjt.lc");
				user.setSex("0");
				user.setPhone("13298765431");
				user.setUserType("测试用户");
				user.setDuties("测试用户");
				user.setUnit("国家电投");
				user.setUnitCode("`cpiinfo`");
				user.setUnitId("1");
				userMapper.addUser(user);
			}
		}catch(Exception ex) {
			throw new ServiceException("batchAddUser failured.", ex);
		}
	}

	/* (非 Javadoc) 
	* Title: getUserCountByRoleId
	* TODO(根据角色id查询该角色下的员工总数量)
	* @param roleId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#getUserCountByRoleId(java.lang.String)
	*/
	@Override
	public int getUserCountByRoleId(String roleId, String userName) {
		Map<String, String> queryMap = Maps.newHashMap();
		queryMap.put("roleId", roleId);
		if(!StringUtils.isEmpty(userName)){
			queryMap.put("userName", userName);
		}else{
			queryMap.put("userName", "");
		}
		int count = userMapper.getUserCountByRoleId(queryMap);
		return count;
	}

	/* (非 Javadoc) 
	* Title: initPwd
	* TODO(初始化密码)
	* @param userId 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#initPwd(java.lang.String)
	*/
	@Override
	public void initPwd(String userId) {
		try {
			String pwd = encodeUserPwd("123456");
			Map<String, String> paramMap = Maps.newHashMap();
			paramMap.put("userId", userId);
			paramMap.put("pwd", pwd);
			userMapper.initPwd(paramMap);
		}catch(Exception ex) {
			throw new ServiceException("initPwd failured.", ex);
		}
	}

	/* (非 Javadoc) 
	* Title: delUsersRole
	* TODO(单个或批量从角色中删除用户)
	* @param userId
	* @param roleId 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#delUsersRole(java.lang.String, java.lang.String)
	*/
	@Override
	public void delUsersRole(String userId, String roleId) {
		String[] userIds = userId.split(",");
		for (String uId : userIds) {
			Map<String, String> paramsMap = Maps.newHashMap();
			paramsMap.put("userId", uId);
			paramsMap.put("roleId",roleId);
			userMapper.delUsersRole(paramsMap);
		}
	}

	/* (非 Javadoc) 
	* Title: rolesUserListByPage
	* TODO(给角色添加用户的用户列表--分布显示)
	* @param unitCode
	* @param currentPage
	* @param showCount
	* @param username
	* @param roldId 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#rolesUserListByPage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	*/
	@Override
	public List<User> rolesUserListByPage(String unitCode, String currentPage, 
			String showCount, String username, String roleId) {
		Map<String, Object> paramsMap = pareseMap(unitCode, currentPage, showCount, username, roleId);
		PageRequest pageRequest = new PageRequest(Integer.parseInt(currentPage), Integer.parseInt(showCount));
		if(StringUtils.isEmpty(username)){
			return userMapper.rolesUserListByPage(paramsMap, pageRequest);
		}else{
			return userMapper.rolesUserListByPageForSearch(paramsMap, pageRequest);
		}
	}
	
	
	/** 
	* @Title: pareseMap 
	* @Description: TODO(封装map参数) 
	* @param unitCode
	* @param currentPage
	* @param showCount
	* @param username
	* @param roleId
	* @return
	*/
	private Map<String, Object> pareseMap(String unitCode, String currentPage, 
			String showCount, String username, String roleId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("unitCode", unitCode);
		int beginCount = (Integer.parseInt(currentPage) -1) * Integer.parseInt(showCount);
		paramsMap.put("beginCount", beginCount);
		paramsMap.put("showCount", Integer.parseInt(showCount)+beginCount);
		if(!StringUtils.isEmpty(username)){
			paramsMap.put("username", username);
		}else{
			paramsMap.put("username", "");
		}
		paramsMap.put("roleId", roleId);
		return paramsMap;
	}

	/* (非 Javadoc) 
	* Title: getRolesUserListCount
	* TODO(给角色添加用户的用户总数量)
	* @param unitCode
	* @param username
	* @param roldId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#getRolesUserListCount(java.lang.String, java.lang.String, java.lang.String)
	*/
	@Override
	public int getRolesUserListCount(String unitCode, String username, String roldId) {
		Map<String, String> paramsMap =Maps.newHashMap();
		paramsMap.put("unitCode", unitCode);
		paramsMap.put("roldId", roldId);
		int count = 0;
		if(!StringUtils.isEmpty(username)){
			paramsMap.put("username", username);
			count = userMapper.getRolesUserListCountForSearch(paramsMap);
		}else{
			paramsMap.put("username", "");
			count = userMapper.getRolesUserListCount(paramsMap);
		}
		
		return count;
	}

	/* (非 Javadoc) 
	* Title: addRoleAndUser
	* TODO(批量给角色添加用户)
	* @param userId
	* @param roleId 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#addRoleAndUser(java.lang.String, java.lang.String)
	*/
	@Override
	public void plAddRoleAndUser(String userId, String roleId) {
		String userIds[] = userId.split(",");
		for (String uId : userIds) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("id", UUID.randomUUID().toString().replace("-", ""));
			paramMap.put("roleId", roleId);
			paramMap.put("userId", uId);
			userMapper.addUserAndRole(paramMap);
		}
	}

	/* (非 Javadoc) 
	* Title: modPwd
	* TODO(修改密码)
	* @param userId
	* @param oldPwd
	* @param newPwd
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#modPwd(java.lang.String, java.lang.String, java.lang.String)
	*/
	@Override
	public int modPwd(String userId, String newPwd) {
		Map<String,String> paramsMap = Maps.newHashMap();
		paramsMap.put("userId", userId);
		paramsMap.put("pwd", newPwd);
		int count  = userMapper.initPwd(paramsMap);
		return count;
	}





	/* (非 Javadoc) 
	* Title: importSave
	* TODO(导入用户)
	* @param path
	* @param fileName
	* @param uploadFile
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IUserService#importSave(java.lang.String, java.lang.String, org.springframework.web.multipart.commons.CommonsMultipartFile)
	*/
	@Override
	public int importSaveUser(String path, 
			MultipartFile uploadFile, 
			String orgCode) {
		try {
			File outFile = File.createTempFile("pre", ".tmp");
			outFile.deleteOnExit();
			uploadFile.transferTo(outFile);
			//导入全部用户部门方法
			int count = this.importSaveUser1(outFile.getAbsolutePath());
			//用户操作批量导入用户方法
			//this.importSaveUser2(path+"//"+fileName,orgCode);
			outFile.delete();
			return count;
		}catch(IOException ex) {
			throw new ServiceException("importSaveUser failured.", ex);
		}
	}
	
	@Override
	public List<User> queryuserby(Map<String,Object> map){
		List<User> l = this.userMapper.queryuserby( map);
		return l;
	}

	/* (非 Javadoc) 
	* <p>Title: userListByDepart</p> 
	* <p>Description: </p> 
	* @param unitCode
	* @param currentPage
	* @param showCount
	* @param username
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IUserService#userListByDepart(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	*/
	@Override
	public List<User> userListByDepart(String unitCode, String currentPage, String showCount, String username) {
		Map<String, Object> queryMap = parseMap(unitCode, currentPage, showCount, username);
		return userMapper.userListByDepart(queryMap, new PageRequest(Integer.parseInt(currentPage), Integer.parseInt(showCount)));
	}

	/* (非 Javadoc) 
	* <p>Title: getUserCountByOrgCodeDepart</p> 
	* <p>Description: </p> 
	* @param unitCode
	* @param username
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IUserService#getUserCountByOrgCodeDepart(java.lang.String, java.lang.String)
	*/
	@Override
	public int getUserCountByOrgCodeDepart(String unitCode, String username) {
		Map<String, String> queryMap = Maps.newHashMap();
		queryMap.put("orgCode", unitCode);
		int  count =0;
		queryMap.put("username", username);
		count = userMapper.getUserCountByOrgCodeDepart(queryMap);
		return count;
	}

	/* (非 Javadoc) 
	* <p>Title: userListByOrg</p> 
	* <p>Description: </p> 
	* @param unitCode
	* @param currentPage
	* @param showCount
	* @param username
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IUserService#userListByOrg(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	*/
	@Override
	public List<User> userListByOrg(String unitCode, String currentPage, String showCount, String username) {
		Map<String, Object> queryMap = parseMap(unitCode, currentPage, showCount, username);
		return userMapper.userListByOrg(queryMap, new PageRequest(Integer.parseInt(currentPage), Integer.parseInt(showCount)));
	}

	/* (非 Javadoc) 
	* <p>Title: getUserCountByOrgCodeOrg</p> 
	* <p>Description: </p> 
	* @param unitCode
	* @param username
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IUserService#getUserCountByOrgCodeOrg(java.lang.String, java.lang.String)
	*/
	@Override
	public int getUserCountByOrgCodeOrg(String unitCode, String username) {
		Map<String, String> queryMap = Maps.newHashMap();
		queryMap.put("orgCode", unitCode);
		queryMap.put("username", username);
		int count = userMapper.getUserCountByOrgCodeOrg(queryMap);
		return count;
	}

	/* (导入全部用户部门方法) 
	* <p>Title: importSaveUser1</p> 
	* <p>Description: </p> 
	* @param path
	* @param uploadFile
	* @param orgCode
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IUserService#importSaveUser1(java.lang.String, org.springframework.web.multipart.commons.CommonsMultipartFile, java.lang.String)
	*/
	public int importSaveUser1(String path) {
		int count = 0;
//		try {
//			List<List<List<String>>> list = ExcelUtil.readExcelWithoutTitle(path);
//			for (List<List<String>> list2 : list) {
//				for (List<String> list3 : list2) {
//					Depart depart = new Depart();
//					depart.setDepartName(list3.get(2));
//					depart.setOrgCode(list3.get(0));
//					//判断部门是否存在
//					Depart depart1 = iDepartService.queryDepartByName(depart);
//					if(depart1 == null){
//						iDepartService.addDepart(list3.get(0),list3.get(1), list3.get(2));
//					}
//					Depart depart2 = iDepartService.queryDepartByName(depart);
//					//初始化用户实体
//					User user = new User();
//					user.setUsername(list3.get(3));
//					if(list3.get(8) == null){
//						user.setEmail(" ");
//					}else{
//						user.setEmail(list3.get(8));
//					}
//
//					if(list3.get(5) == null){
//						user.setDuties(" ");
//					}else{
//						user.setDuties(list3.get(5));
//					}
//
//					if(list3.get(6) == null){
//						user.setPhone(" ");
//					}else{
//						user.setPhone(list3.get(6).toString());
//					}
//					if(list3.get(7) == null){
//						user.setSex(" ");
//					}else{
//						user.setSex(list3.get(7));
//					}
//
//					user.setUserCode(list3.get(4));
//					String pwd = "123456";
//					String userId  = UUID.randomUUID().toString().replace("-", "");
//					user.setId(userId);
//					user.setUserpwd(encodeUserPwd(pwd));
//					//初始化角色用户关联实体
//					DepartUser departUser = new DepartUser();
//					departUser.setId(UUID.randomUUID().toString().replace("-", ""));
//					departUser.setDepartId(depart2.getId());
//					departUser.setUserId(userId);
//					//检查用户是否存在
//					User user1 = userMapper.queryUserByCode(list3.get(4));
//					if(user1 == null){
//						//添加用户
//						userMapper.addUser(user);
//						//添加用户角色关联
//						count += userMapper.addDepartUser(departUser);
//					}else{
//						departUser.setUserId(user1.getId());
//						DepartUser i = userMapper.selectDepartUserRelation(departUser);
//						if(i == null){
//							count += userMapper.addDepartUser(departUser);
//						}
//					}
//				}
//			}
//		}catch(Exception ex) {
//			throw new ServiceException("importSaveUser failured.", ex);
//		}
		return count;
	}

	/* (用户操作批量导入用户方法) 
	* <p>Title: importSaveUser1</p> 
	* <p>Description: </p> 
	* @param path
	* @param uploadFile
	* @param orgCode
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IUserService#importSaveUser1(java.lang.String, org.springframework.web.multipart.commons.CommonsMultipartFile, java.lang.String)
	*/
	public int importSaveUser2(String path,String orgCode) {
		int count = 0;
		try{
			List<List<List<String>>> list = ExcelUtil.readExcelWithoutTitle(path);
			for (List<List<String>> list2 : list) {
				for (List<String> list3 : list2) {
					//初始化用户实体
					User user = new User();
					user.setUsername(list3.get(4));
					user.setEmail(list3.get(8));
					user.setDuties(list3.get(7));
					user.setPhone(list3.get(6));
					user.setSex(list3.get(5));
					user.setUserCode(list3.get(3));
					user.setUserType(list3.get(9));
					String pwd = "123456";
					String userId  = UUID.randomUUID().toString().replace("-", "");
					user.setId(userId);
					user.setUserpwd(encodeUserPwd(pwd));
					//初始化角色用户关联实体
					DepartUser departUser = new DepartUser();
					departUser.setDepartId(orgCode);
					departUser.setUserId(userId);
					//检查用户是否存在
					User user1 = userMapper.queryUserByCode(list3.get(3));
					if(user1 == null){
						//添加用户
						userMapper.addUser(user);
						//添加用户部门关联
						count = userMapper.addDepartUser(departUser);
					}
				}
			}
		}catch(Exception e){
			throw new ServiceException("importSaveUser2 failured.", e);
		}
		return count;
	}

	/* (非 Javadoc) 
	* <p>Title: queryUserByCodeAndPass</p> 
	* <p>Description: </p> 
	* @param userCode
	* @param password
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IUserService#queryUserByCodeAndPass(java.lang.String, java.lang.String)
	*/
	@Override
	public User queryUserByCodeAndPass(String userCode, String password) {
		User user =new User();
		try{
			User user1 =new User();
			user1.setUserCode(userCode);
			user1.setUserpwd(encodeUserPwd(password));
			user = userMapper.queryUserByCodeAndPass(user1);
		}catch(Exception e){
			throw new ServiceException("queryUserByCodeAndPass", e);
		}
		return user;
	}

	@Override
	public Result pageUserList(pageUserListDTO dto) {
		List<User> user =userMapper.pageUserList(dto);
		int page = dto.getPage();
		int limit = dto.getLimit();
		//分页
		List<User> users=new ArrayList<>();
		List<UserListDTO> list = new ArrayList<>();
		if(user!=null&&user.size()>0){
			 users = pageByCurrentList(user, limit, page);
			for (User user1 : users) {
				UserListDTO u = new UserListDTO();
				BeanUtils.copyProperties(user1,u);
				u.setRoleName(getRoleNameById(u.getUserType()));
				u.setUnitName(getOrgNameByCode(u.getUnitCode()));
				u.setDepartName(getDepartNameByCode(u.getDepartId()));
				list.add(u);
			}
		}

		return new Result<>().ok(new PageData<UserListDTO>(list,user.size()));
	}

	private String getDepartNameByCode(String departId) {
		if(departId!=null&&!"".equals(departId)){
			String name = userMapper.queryDepartNameById(departId);
			if(name != null && !"".equals(name)){
				return name;
			}
		}
		return "";
	}


	private String getOrgNameByCode(String unitCode) {
		if(unitCode!=null){
			OrganizationExample organizationExample = new OrganizationExample();
			organizationExample.or().andOrgCodeEqualTo(unitCode);
			List<Organization> organizations = organizationMapper.selectByExample(organizationExample);
			if(organizations!=null&&organizations.size()>0){
				return organizations.get(0).getOrgName();
			}
		}
		return "";
	}

	private String getRoleNameById(String userType) {
		if(userType!=null&&!"".equals(userType)){
			Role role = roleMapper.queryRoleById(userType);
			if(role!=null){
				return role.getRoleName();
			}
		}
		return "";
	}

	private List<User> pageByCurrentList(List list, int pagesize, int currentPage) {
		int totalcount = list.size();
		int pagecount = 0;
		List<User> subList;
		int m = totalcount % pagesize;
		if (m > 0) {
			pagecount = totalcount / pagesize + 1;
		} else {
			pagecount = totalcount / pagesize;
		}
		if (m == 0) {
			subList = list.subList((currentPage - 1) * pagesize, pagesize * (currentPage));
		} else {
			if (currentPage == pagecount) {
				subList = list.subList((currentPage - 1) * pagesize, totalcount);
			} else {
				subList = list.subList((currentPage - 1) * pagesize, pagesize * (currentPage));
			}
		}
		return subList;
	}



	private PasswordEncoder pwdEncoder = new BCryptPasswordEncoder(); 
	private String encodeUserPwd(String userPwd) {
		return pwdEncoder.encode(userPwd);
	}
	private boolean verifyPwd(String oldPwd,String newPwd){
		return pwdEncoder.matches(oldPwd,newPwd);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}
}
