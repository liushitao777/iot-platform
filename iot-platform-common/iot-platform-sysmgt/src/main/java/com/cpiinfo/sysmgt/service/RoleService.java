package com.cpiinfo.sysmgt.service;

import com.cpiinfo.sysmgt.dto.RoleDTO;
import com.cpiinfo.sysmgt.entity.Oper;
import com.cpiinfo.sysmgt.entity.Role;
import com.cpiinfo.sysmgt.entity.ZNode;
import com.cpiinfo.sysmgt.utils.Result;

import java.util.List;
import java.util.Map;

/**   
* @Title: IRoleService.java 
* @Package com.cpiinfo.iisp.manager.intf
* @Description: TODO(定义角色的service层) 
* @author 牛棚
* @date 2016年9月12日 下午7:00:14 
* @version V1.0   
*/
public interface RoleService {

	/** 
	* @Title: listRoles 
	* @Description: 根据管理员id查询角色列表 
	* @param
	* @return
	*/
	public List<Role> listRoles(String unitCode, String currentPage, String showCount, String roleName);
	
	/** 
	* @Title: getRoleCount 
	* @Description: TODO(根据单位编码查询该单位下的角色总条数) 
	* @param
	* @return
	*/
	public int getRoleCount(String unitCode, String roleName);
	
	
	/** 
	* @Title: queryRoleListByOrgCode 
	* @Description: TODO(根据单位id查询角色列表) 
	* @param orgCode
	* @return
	*/
	public List<Role> queryRoleListByOrgCode(String orgCode);
	
	/** 
	* @Title: queryRoleListByOrgCode 
	* @Description: TODO(查询所有角色列表) 
	* @param
	* @return
	*/
	public List<Role> queryRoleList();
	
	
	/** 
	* @Title: listMenusByRoleId 
	* @Description: 员工的资源列表 
	* @param adminCode
	* @param roleId
	 * @param
	* @return
	*/
	public List<ZNode> listMenusByRoleId(String adminCode, String roleId, String userCode);
	
	/** 
	* @Title: updateMenusByRoleId 
	* @Description: 修改角色所拥有的资源 
	* @param roleid
	* @param menus
	*/
	public void updateMenusByRoleId(String roleid, List<Map<String, Object>> menus);
	
	
	/** 
	* @Title: queryRoleByRoleId 
	* @Description: 查询角色 
	* @param roleId
	* @return
	*/
	public Role queryRoleByRoleId(String roleId); 
	
	
	/** 
	* @Title: queryRolesByUserId 
	* @Description: 查询用户的角色
	* @param userId
	* @return
	*/
	public List<ZNode> queryRolesByUserId(String userId);
	
	
	/** 
	* @Title: addRole 
	* @Description: 创建角色 
	* @param role
	*/
	public void addRole(RoleDTO role);
	
	
	/** 
	* @Title: updateRole 
	* @Description: 修改角色
	* @param role
	*/
	public void updateRole(Role role);
	
	
	/** 
	* @Title: queryRoleById 
	* @Description: 根据id查询角色 
	* @param roleId
	* @return
	*/
	public Role queryRoleById(String roleId);
	
	
	/** 
	* @Title: delRoleById 
	* @Description: 删除角色
	* @param roleId
	*/
	public Result delRoleById(String roleId);
	
	
	/** 
	* @Title: queryOperListByRoleAndResourceId 
	* @Description: TODO(根据角色id和资源id查询操作列表) 
	* @return
	*/
	public List<ZNode> queryOperListByRoleAndResourceId(String userCode, String roleId, String resourceId);
	
	/** 
	* @Title: queryResAndOptList 
	* @Description: TODO(根据角色id查询资源及资源下面的操作) 
	* @param roleId
	* @return
	*/
	public List<Map<String,Object>> queryResAndOptList(String roleId);

	/** 
	* @Title: listRolesByOrgCode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param list
	* @param @param currentPage
	* @param @param showCount
	* @param @param roleName
	* @param @return    设定文件 
	* @return List<Role>    返回类型 
	* @throws 
	*/
	public List<Role> listRolesByOrgCode(List<String> orgCode, String currentPage, String showCount, String roleName);

	/** 
	* @Title: getRoleCountByOrgCode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param orgCode
	* @param @param roleName
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int getRoleCountByOrgCode(List<String> orgCode, String roleName);

	/** 
	* @Title: queryOperatePermission 
	* @Description: 根据人员和菜单查询相应的操作按钮权限 
	* @return
	*/
	public List<Oper> queryOperatePermission(String userCode, String menuUrl);

	Result pageRole(String roleName, String page, String limit);

}
