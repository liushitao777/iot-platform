package com.cpiinfo.sysmgt.dao;

import com.cpiinfo.iot.mybatis.dto.PageRequest;
import com.cpiinfo.sysmgt.dto.pageUserListDTO;
import com.cpiinfo.sysmgt.entity.DepartUser;
import com.cpiinfo.sysmgt.entity.User;
import com.cpiinfo.sysmgt.entity.UserForIMDG;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface UserMapper  {
	/** 
	* @Title: addUser 
	* @Description: TODO(添加员工) 
	* @param user
	*/
	public void addUser(User user);
	
	
	/** 
	* @Title: queryUserById 
	* @Description: TODO(根据id查询员工信息) 
	* @param id
	* @return
	*/
	public User queryUserById(String id);
	
	/** 
	* @Title: queryUserByCode 
	* @Description: TODO(根据code查询员工信息) 
	* @param userCode
	* @return
	*/
	public User queryUserByCode(String userCode);
	
	
	/** 
	* @Title: updateUser 
	* @Description: TODO(修改员工信息) 
	* @param user
	*/
	public void updateUser(User user);
	
	
	/** 
	* @Title: delUserById 
	* @Description: TODO(根据id删除员工) 
	* @param id
	*/
	public void delUserById(String id);
	
	
	/** 
	* @Title: disableUserByid 
	* @Description: TODO(根据id改变员工状态----禁用) 
	* @param id
	*/
	public void disableUserById(String id);
	
	/** 
	* @Title: enableUserByid 
	* @Description: TODO(根据id改变员工状态----正常 ) 
	* @param id
	*/
	public void enableUserById(String id);
	
	/** 
	* @Title: userList 
	* @Description: TODO(分页员工列表) 
	* @param unitCode
	* @return
	*/
	public List<User> userList(Map<String, Object> paramsMap, PageRequest pageRequest);
	
	
	/**
	 * 
	* @Title: userListForIMDG 
	* @Description: TODO(为存入IMDG封装的user对象) 
	* @return
	 */
	public List<UserForIMDG> userListForIMDG();
	/** 
	* @Title: userListForSearch 
	* @Description: TODO(搜索时的分页查询员工列表) 
	* @param paramsMap
	* @return
	*/
	public List<User> userListForSearch(Map<String, Object> paramsMap, PageRequest pageRequest);
	
	/** 
	* @Title: delUserAndRoleByUserId 
	* @Description: TODO(删除员工角色) 
	* @param userId
	*/
	public void delUserAndRoleByUserId(String userId);
	
	/** 
	* @Title: addUserAndRole 
	* @Description: TODO(给员工赋角色) 
	* @param paramsMap
	*/
	public void addUserAndRole(Map<String,String> paramsMap);
	
	/** 
	* @Title: queryUserListByRoleId 
	* @Description: TODO(查询角色下的用户列表) 
	* @param roleId
	* @return
	*/
	public List<User> queryUserListByRoleId(Map<String, Object> paramsMap, PageRequest pageRequest);
	
	/** 
	* @Title: orgsAllUserList 
	* @Description: TODO(查询单位下的所有用户) 
	* @param unitCode
	* @return
	*/
	public List<User> orgsAllUserList(String unitCode);
	
	
	/** 
	* @Title: delUserByOrgCode 
	* @Description: TODO(根据单位编码删除此单位下的所有用户) 
	* @param orgCode
	*/
	public void delUserByOrgCode(String orgCode);

	/**
	 * 
	* @Title: findByUserCode 
	* @Description: TODO(根据用户code查询用户信息) 
	* @param userId
	* @return
	 */
	public User findByUserCode(String userId);
	
	/**
	 * 
	* @Title: allUserList 
	* @Description: TODO(查询全部用户信息) 
	* @return
	 */
	public List<User> allUserList();
	
	/** 
	* @Title: getUserCountByOrgCode 
	* @Description: TODO(根据单位code查询该单位下的员工总数) 
	* @param orgCode
	* @return
	*/
	public int getUserCountByOrgCode(Map<String, String> queryMap);
	
	/** 
	* @Title: getUserCountByOrgCodeForSearch 
	* @Description: TODO(分页搜索用户时的总条数) 
	* @param queryMap
	* @return
	*/
	public int getUserCountByOrgCodeForSearch(Map<String, String> queryMap);
	
	/** 
	* @Title: queryUserByEmail 
	* @Description: TODO(根据email查询员工) 
	* @param email
	* @return
	*/
	public User queryUserByEmail(String email);
	/**
	 * 
	* @Title: queryUserForIMDGByEmail 
	* @Description: TODO(根据email查询员工,包括机构和权限) 
	* @param email
	* @return
	 */
	public UserForIMDG queryUserForIMDGByEmail(String email);
	/** 
	* @Title: getUserCountByRoleId 
	* @Description: TODO(根据角色id查询该角色下的员工总数量) 
	* @param roleId
	* @return
	*/
	public int getUserCountByRoleId(Map<String, String> queryMap);
	
	/** 
	* @Title: initPwd 
	* @Description: TODO(初始化/修改密码) 
	* @param userId
	*/
	public int initPwd(Map<String, String> paramsMap);
	
	/** 
	* @Title: delUsersRole 
	* @Description: TODO(从角色中删除用户) 
	* @param paramsMap
	*/
	public void delUsersRole(Map<String, String> paramsMap);
	
	/** 
	* @Title: rolesUserListByPage 
	* @Description: TODO(给角色加添用户时的用户列表) 
	* @param paramsMap
	* @return
	*/
	public List<User> rolesUserListByPage(Map<String, Object> paramsMap, PageRequest pageRequest);
	
	/** 
	* @Title: rolesUserListByPageForSearch 
	* @Description: TODO(搜索时的给角色加添用户时的用户列表) 
	* @param paramsMap
	* @return
	*/
	public List<User> rolesUserListByPageForSearch(Map<String, Object> paramsMap, PageRequest pageRequest);
	
	/** 
	* @Title: getRolesUserListCount 
	* @Description: TODO(给角色加添用户时的用户总数量) 
	* @param paramsMap
	* @return
	*/
	public int getRolesUserListCount(Map<String, String> paramsMap);
	
	/** 
	* @Title: getRolesUserListCountForSearch 
	* @Description: TODO(搜索时给角色加添用户时的用户总数量) 
	* @param paramsMap
	* @return
	*/
	public int getRolesUserListCountForSearch(Map<String, String> paramsMap);
	
	/** 
	* @Title: importSave 
	* @Description: TODO(批量导入用户) 
	* @param userList
	*/
	public int importSaveUser(List<User> userList);
	
	
	public List<User> queryuserby(Map<String,Object> map);


	/** 
	* @Title: userListByDepart 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param queryMap
	* @param @return    设定文件 
	* @return List<User>    返回类型 
	* @throws 
	*/
	public List<User> userListByDepart(Map<String, Object> queryMap, PageRequest pageRequest);


	/** 
	* @Title: userListForSearchByDepart 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param queryMap
	* @param @return    设定文件 
	* @return List<User>    返回类型 
	* @throws 
	*/
	public List<User> userListForSearchByDepart(Map<String, Object> queryMap, PageRequest pageRequest);


	/** 
	* @Title: getUserCountByOrgCodeDepart 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param queryMap
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int getUserCountByOrgCodeDepart(Map<String, String> queryMap);


	/** 
	* @Title: userListByOrg 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param queryMap
	* @param @return    设定文件 
	* @return List<User>    返回类型 
	* @throws 
	*/
	public List<User> userListByOrg(Map<String, Object> queryMap, PageRequest pageRequest);


	/** 
	* @Title: getUserCountByOrgCodeOrg 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param queryMap
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int getUserCountByOrgCodeOrg(Map<String, String> queryMap);


	/**
	 * @return  
	* @Title: addDepartUser 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param departUser    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public int addDepartUser(DepartUser departUser);


	/** 
	* @Title: queryUserForIMDGByUserCode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userCode
	* @param @return    设定文件 
	* @return UserForIMDG    返回类型 
	* @throws 
	*/
	public List<UserForIMDG> queryUserForIMDGByUserCode(String userCode);


	/**
	 * @return  
	* @Title: selectDepartUserRelation 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param departUser    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public DepartUser selectDepartUserRelation(DepartUser departUser);


	/** 
	* @Title: delUserAndDepartById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userId    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void delUserAndDepartById(String userId);


	/** 
	* @Title: queryUserByCodeAndPass 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param user1
	* @param @return    设定文件 
	* @return User    返回类型 
	* @throws 
	*/
	public User queryUserByCodeAndPass(User user1);

	List<User> pageUserList(@Param("dto") pageUserListDTO dto);

	String getStationNameByCode(@Param("stationCode")String stationCode);

    User queryUserById1(String userId);

	int initPwd1(Map<String, String> paramsMap);

    User queryMobileUserByuserName(String userName);

	void reloadFailsCount();

	void updateMobileUserLoginTime(String userCode, Long totalLoginTime, Date lastLoginTime);

    String queryDepartNameById(@Param("departId")String departId);
}