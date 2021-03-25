package com.cpiinfo.sysmgt.service;

import com.cpiinfo.sysmgt.dto.QueryUserByCodeDTO;
import com.cpiinfo.sysmgt.dto.pageUserListDTO;
import com.cpiinfo.sysmgt.entity.User;
import com.cpiinfo.sysmgt.utils.Result;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**   
* @Title: IUserService.java 
* @Package com.cpiinfo.iisp.manager.intf
* @Description: TODO(用户的UserService层) 
* @author 牛棚
* @date 2016年10月14日 下午8:30:04 
* @version V1.0   
*/
public interface UserService {

	/** 
	* @Title: addUser 
	* @Description: TODO(添加员工) 
	* @param user
	* @throws NoSuchAlgorithmException
	*/
	public String addUser(User user);
	
	/** 
	* @Title: queryUserById 
	* @Description: TODO(根据id查询员工) 
	* @param id
	* @return
	*/
	public User queryUserById(String id);
	
	/** 
	* @Title: queryUserByCode 
	* @Description: TODO(根据code查询员工 ) 
	* @param userCode
	* @return
	*/
	public QueryUserByCodeDTO queryUserByCode(String userCode);
	
	/** 
	* @Title: updateUser 
	* @Description: TODO(修改员工) 
	* @param user
	*/
	public Result updateUser(User user);
	
	/** 
	* @Title: delUserById 
	* @Description: TODO(删除员工) 
	* @param id
	*/
	public void delUserById(String id);
	
	/** 
	* @Title: disableUserByid 
	* @Description: TODO(改变员工状态-- 禁用) 
	* @param id
	*/
	public void disableUserByid(String id);
	
	/** 
	* @Title: enableUserByid 
	* @Description: TODO(改变员工状态 --正常 ) 
	* @param id
	*/
	public void enableUserByid(String id);
	
	
	/** 
	* @Title: userList 
	* @Description: TODO(根据单位编码查询员工列表) 
	* @param unitCode
	* @return
	*/
	public List<User> userList(String unitCode, String currentPage, String showCount, String username);
	
	/** 
	* @Title: addUserAndRole 
	* @Description: TODO(给员工赋角色) 
	* @param userId
	* @param roleId
	*/
	public void addUserAndRole(String userId, String roleId);
	
	/** 
	* @Title: queryUserListByRoleId 
	* @Description: TODO(查询角色下的用户列表 ) 
	* @param roleId
	* @return
	*/
	public List<User> queryUserListByRoleId(String roleId, String currentPage, String showCount, String userName);
	
	/** 
	* @Title: getUserCountByOrgCode 
	* @Description: TODO(根据单位code查询该单位下的员工总数) 
	* @param orgCode
	* @return
	*/
	public int getUserCountByOrgCode(String orgCode, String username);
	
	/** 
	* @Title: queryUserByEmail 
	* @Description: TODO(根据email查询员工) 
	* @param email
	* @return
	*/
	public User queryUserByEmail(String email);
	
	/** 
	* @Title: getUserCountByRoleId 
	* @Description: TODO(根据角色id查询该角色下的员工总数量) 
	* @param roleId
	* @return
	*/
	public int getUserCountByRoleId(String roleId, String userName);
	
	/** 
	* @Title: initPwd 
	* @Description: TODO(初始化密码) 
	* @param userId
	*/
	public void initPwd(String userId);
	
	/** 
	* @Title: delUsersRole 
	* @Description: TODO(单个或批量从角色中删除用户) 
	* @param userId
	* @param roleId
	*/
	public void delUsersRole(String userId, String roleId);
	
	/** 
	* @Title: rolesUserListByPage 
	* @Description: TODO(给角色添加用户的员工列表) 
	* @param unitCode
	* @param currentPage
	* @param showCount
	* @param username
	* @param roldId
	*/
	public List<User> rolesUserListByPage(String unitCode, String currentPage, 
			String showCount, String username, String roldId);
	
	/** 
	* @Title: getRolesUserListCount 
	* @Description: TODO(给角色添加用户的员工总数量) 
	* @param unitCode
	* @param username
	* @param roldId
	* @return
	*/
	public int getRolesUserListCount(String unitCode, String username, String roldId);
	
	/** 
	* @Title: addRoleAndUser 
	* @Description: TODO(批量给角色添加用户) 
	* @param userId
	* @param roleId
	*/
	public void plAddRoleAndUser(String userId, String roleId);
	
	/** 
	* @Title: modPwd 
	* @Description: TODO(修改密码) 
	* @param userId
	* @param newPwd
	* @return
	*/
	public int modPwd(String userId, String newPwd);
	
	public Result modifyUserPwd(String userId, String newPwd, String oldPwd);
	
	/** 
	* @Title: importSave 
	* @Description: TODO(导入用户) 
	* @param path
	* @return
	*/
	public int importSaveUser(String path, MultipartFile uploadFile, String orgCode);
	
	/** 
	* @Title: batchAddUser 
	* @Description: TODO(批量添加用户) 
	*/
	public void batchAddUser();
	
	
	public List<User> queryuserby(Map<String,Object> map);

	/** 
	* @Title: userListByDepart 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param unitCode
	* @param @param currentPage
	* @param @param showCount
	* @param @param username
	* @param @return    设定文件 
	* @return List<User>    返回类型 
	* @throws 
	*/
	public List<User> userListByDepart(String unitCode, String currentPage, String showCount, String username);

	/** 
	* @Title: getUserCountByOrgCodeDepart 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param unitCode
	* @param @param username
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int getUserCountByOrgCodeDepart(String unitCode, String username);

	/** 
	* @Title: userListByOrg 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param unitCode
	* @param @param currentPage
	* @param @param showCount
	* @param @param username
	* @param @return    设定文件 
	* @return List<User>    返回类型 
	* @throws 
	*/
	public List<User> userListByOrg(String unitCode, String currentPage, String showCount, String username);

	/** 
	* @Title: getUserCountByOrgCodeOrg 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param unitCode
	* @param @param username
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int getUserCountByOrgCodeOrg(String unitCode, String username);

	/** 
	* @Title: queryUserByCodeAndPass 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userCode
	* @param @param password
	* @param @return    设定文件 
	* @return UserInfoBeanIMDG    返回类型 
	* @throws 
	*/
	public User queryUserByCodeAndPass(String userCode, String password);


	Result pageUserList(pageUserListDTO dto);

}
