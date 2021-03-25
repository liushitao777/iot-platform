package com.cpiinfo.sysmgt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cpiinfo.iot.mybatis.dto.PageRequest;
import com.cpiinfo.sysmgt.entity.Role;


/**   
* @Title: RoleMapper.java 
* @Package com.cpiinfo.iisp.manager.dao
* @Description: TODO(定义角色的mapper类) 
* @author 牛棚
* @date 2016年9月13日 下午2:00:03 
* @version V1.0   
*/
public interface RoleMapper {
	/** 
	* @Title: listRoles 
	* @Description: TODO(角色列表) 
	* @param updateUser
	* @return
	*/
	public List<Role> listRoles(Map<String, Object> queryMap, PageRequest pageRequest);
	
	/** 
	* @Title: getRoleCount 
	* @Description: TODO(根据单位编码查询该单位下的角色总条数) 
	* @param queryMap
	* @return
	*/
	public int getRoleCount(Map<String, String> queryMap);
	
	/** 
	* @Title: queryRoleListByOrgCode 
	* @Description: TODO(根据单位code查询角色列表) 
	* @param orgCode
	* @return
	*/
	public List<Role> queryRoleListByOrgCode(String orgCode);
	
	/** 
	* @Title: queryRoleListByOrgCode 
	* @Description: TODO(根据单位code查询角色列表) 
	* @param orgCode
	* @return
	*/
	public List<Role> queryRoleList();

	/** 
	* @Title: deleteMenusByRoleId 
	* @Description: TODO(删除角色拥有的所有资源 ) 
	* @param roleid
	* @return
	*/
	public int deleteMenusByRoleId(String roleid);
	
	/** 
	* @Title: updateMenuByRoleId 
	* @Description: TODO(修改角色所拥有的资源) 
	* @param roleid
	* @param menus
	* @return
	*/
	public int updateMenuByRoleId(String roleid, List<Map<String, Object>> menus);

	/** 
	* @Title: queryRoleByRoleId 
	* @Description: TODO(查询角色) 
	* @param roleId
	* @return
	*/
	public Role queryRoleByRoleId(String roleId);
	
	/** 
	* @Title: queryRolesByUserId 
	* @Description: TODO(查询员工的所拥有的角色) 
	* @param userId
	* @return
	*/
	public List<Role> queryRolesByUserId(String userId);
	
	public List<Role> queryRolesByUserCode(String userCode);
	
	/** 
	* @Title: addRole 
	* @Description: TODO(创建角色) 
	* @param role
	*/
	public void addRole(Role role);
	
	/** 
	* @Title: updateRole 
	* @Description: TODO(修改角色 ) 
	* @param role
	*/
	public void updateRole(Role role);
	
	/** 
	* @Title: queryRoleById 
	* @Description: TODO(根据id查询角色) 
	* @param roleId
	* @return
	*/
	public Role queryRoleById(String roleId);
	
	/** 
	* @Title: delRoleById 
	* @Description: TODO(根据id删除角色 ) 
	* @param roleId
	*/
	public void delRoleById(String roleId);
	
	/** 
	* @Title: delRoleAndResourceByResourceId 
	* @Description: TODO(删除资源时，同时删除角色和资源中间表) 
	* @param resourceId
	*/
	public void delRoleAndResourceByResourceId(String resourceId);
	
	/** 
	* @Title: delUserAndRoleByRoleId 
	* @Description: TODO(删除角色时，同时删除角色和用户中间表) 
	* @param roleId
	*/
	public void delUserAndRoleByRoleId(String roleId);
	
	/** 
	* @Title: insertMenuByRoleId 
	* @Description: TODO(角色绑定资源, 角色和资源表  中插入数据 ) 
	* @param paramMap
	* @return
	*/
	public int insertMenuByRoleId(Map<String, String> paramMap);
	
	/** 
	* @Title: addRoleAndUser 
	* @Description: TODO(批量给角色添加用户) 
	* @param paramsMap
	*/
	public void addRoleAndUser(Map<String, String> paramsMap);

	/** 
	* @Title: listRolesByOrgCode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param params
	* @param @return    设定文件 
	* @return List<Role>    返回类型 
	* @throws 
	*/
	public List<Role> listRolesByOrgCode(Map<String, Object> params, PageRequest page);

	/** 
	* @Title: getRoleCountOrgCode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param paramsMap
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int getRoleCountOrgCode(Map<String, Object> paramsMap);
	
	/**
	 * 查询用户在指定资源中可进行操作
	 * @param userCode
	 * @param resId
	 * @return
	 */
	public List<String> queryUserResourceOperations(@Param("userCode")String userCode, @Param("resId")String resId);

	/**
	 * 新增角色具有的资源权限表
	 * @param replace
	 * @param roleId
	 * @param resourceId
	 */
	void insertRoleAndResource(@Param("replace")String replace, @Param("roleId")String roleId, @Param("resourceId")String resourceId);

	List<Role> pageRole(@Param("roleName")String roleName);

	List<String> getResourcesLsit(@Param("roleId")String roleId);

	int countExist(Map<String, String> paramMap);

	String getParentResource(@Param("resourceId")String resourceId);

    Integer findUserRole(@Param("rId")String rId);
}