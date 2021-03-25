package com.cpiinfo.sysmgt.service.impl;

import com.cpiinfo.iot.mybatis.dto.PageRequest;
import com.cpiinfo.iot.utility.DateUtils;
import com.cpiinfo.sysmgt.dao.MenuMapper;
import com.cpiinfo.sysmgt.dao.RoleMapper;
import com.cpiinfo.sysmgt.dao.UserMapper;
import com.cpiinfo.sysmgt.dto.RoleAndResourceDTO;
import com.cpiinfo.sysmgt.dto.RoleDTO;
import com.cpiinfo.sysmgt.entity.*;
import com.cpiinfo.sysmgt.service.RoleService;
import com.cpiinfo.sysmgt.utils.PageData;
import com.cpiinfo.sysmgt.utils.Result;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**   
* @Title: RoleServiceImpl.java 
* @Package com.cpiinfo.iisp.manager.service
* @Description: TODO(角色的ServiecImpl类) 
* @author 牛棚
* @date 2016年9月13日 上午9:24:20 
* @version V1.0   
*/
@Service
public class RoleServiceImpl implements RoleService {

	
	@Autowired
    RoleMapper roleMapper;
	
	@Autowired
    MenuMapper menuMapper;
	@Autowired
    UserMapper userMapper;

	/* (非 Javadoc) 
	* Title: listRoles
	* TODO(角色列表)
	* @param updateUser
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#listRoles(java.lang.String)
	*/
	@Override
	public List<Role> listRoles(String unitCode, String currentPage,
                                String showCount, String roleName) {
		Map<String, Object> params= parseMap(unitCode, currentPage, showCount, roleName);
		return roleMapper.listRoles(params, new PageRequest(Integer.parseInt(currentPage), Integer.parseInt(showCount)));
	}
	
	/** 
	* @Title: parseMap 
	* @Description: TODO(封装map参数) 
	* @param unitCode
	* @param currentPage
	* @param showCount
	* @return
	*/
	private Map<String, Object> parseMap(String unitCode, String currentPage, 
			String showCount, String roleName){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("unitCode", unitCode);
		int beginCount = (Integer.parseInt(currentPage) -1) * Integer.parseInt(showCount);
		paramsMap.put("beginCount", beginCount);
		paramsMap.put("showCount", Integer.parseInt(showCount)+beginCount);
		if(!StringUtils.isEmpty(roleName)){
			paramsMap.put("roleName", roleName);
		}else{
			paramsMap.put("roleName", "");
		}
		return paramsMap;
	}

	/* (非 Javadoc) 
	* Title: listMenusByRoleId
	* TODO(角色所拥有的资源)
	* @param adminId
	* @param roleId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#listMenusByRoleId(java.lang.String, java.lang.String)
	*/
	@Override
	public List<ZNode> listMenusByRoleId(String adminCode, String roleId, String userCode) {
		List<UserForIMDG> users = userMapper.queryUserForIMDGByUserCode(userCode);
		String userLevel = null;
		for(UserForIMDG user : users) {
			if("1".equals(user.getLevel())) {
				userLevel = "1";
			}
		}
		
		//用户的角色列表
		List<Role> usersRoleList = roleMapper.queryRolesByUserCode(adminCode);
		//所有的资源列表
		List<Menu> allMenuList = menuMapper.listMenus();
		
		List<ZNode> zNodes =new ArrayList<ZNode>();
		List<String> parentRoleMenuList =new ArrayList<String>();
		int level =0;
		for (Role role : usersRoleList) {
			Role userRole = roleMapper.queryRoleById(role.getRoleId());
			parentRoleMenuList.addAll(menuMapper.listMenusIdByRoleId(userRole.getRoleId()));
			if(Integer.parseInt(role.getRoot())==1){
				level = Integer.parseInt(role.getLevel());
			}
		}
		List<String> menusByRole = menuMapper.listMenusIdByRoleId(roleId);
		if(level == 1 || "1".equals(userLevel)){
			zNodes = transZNodeFromMunes(allMenuList, menusByRole);
		}else{
			zNodes = transSubZNodeFromMunes(allMenuList, parentRoleMenuList, menusByRole);
		}
		
		return zNodes;
	}


	/* (非 Javadoc) 
	* Title: updateMenusByRoleId
	* TODO(修改角色所拥有的资源)
	* @param roleid
	* @param menus 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#updateMenusByRoleId(java.lang.String, java.util.List)
	*/
	@SuppressWarnings("unchecked")
	@Override
	public void updateMenusByRoleId(String roleid, List<Map<String, Object>> menus) {
		roleMapper.deleteMenusByRoleId(roleid);
		for (Map<String, Object> menu : menus) {
			String resourceId = menu.get("ziyuan").toString();
			List<String> opts = (List<String>) menu.get("opts");
			if(opts != null){
				for (String opt : opts) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("id", UUID.randomUUID().toString().replace("-", ""));
					paramMap.put("role_id", roleid);
					paramMap.put("menu_id", resourceId);
					paramMap.put("opt_id", opt);
					int count =roleMapper.countExist(paramMap);
					if(count==0){
						roleMapper.insertMenuByRoleId(paramMap);
					}

				}
			}else{
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("id", UUID.randomUUID().toString().replace("-", ""));
				paramMap.put("role_id", roleid);
				paramMap.put("menu_id", resourceId);
				paramMap.put("opt_id", "");
				int count =roleMapper.countExist(paramMap);
				if(count==0){
					roleMapper.insertMenuByRoleId(paramMap);
				}
			}
		}
	}

	/** 
	* @Title: transZNodeFromMunes 
	* @Description: TODO(封装superroot的znode) 
	* @param allMenuList
	* @param menusByRole
	* @return
	*/
	private List<ZNode> transZNodeFromMunes(List<Menu> allMenuList, List<String> menusByRole) {
		List<ZNode> zNodes = new ArrayList<>();
		for (Menu menu : allMenuList) {
			ZNode zNode = new ZNode();
			zNode.setId(menu.getId());
			zNode.setName(menu.getRname());
			zNode.setpId(menu.getParentId());
			zNode.setOpen(menusByRole.contains(menu.getId()));
			zNode.setChecked(menusByRole.contains(menu.getId()));
			zNodes.add(zNode);
		}
		return zNodes;
	}
	
	
	/** 
	* @Title: transSubZNodeFromMunes 
	* @Description: TODO(封装非superroot的znode) 
	* @param allMenuList
	* @param parentRoleMenuList
	* @param menusByRole
	* @return
	*/
	private List<ZNode> transSubZNodeFromMunes(List<Menu> allMenuList, List<String> parentRoleMenuList, List<String> menusByRole) {
		List<ZNode> zNodes = new ArrayList<>();
		for (Menu menu : allMenuList) {
			ZNode zNode = new ZNode();
			zNode.setId(menu.getId());
			zNode.setName(menu.getRname());
			zNode.setpId(menu.getParentId());
			zNode.setOpen(menusByRole.contains(menu.getId()));
			zNode.setChecked(menusByRole.contains(menu.getId()));
			//zNode.setChkDisabled(! parentRoleMenuList.contains(menu.getId()));
			zNodes.add(zNode);
			if(! parentRoleMenuList.contains(menu.getId())){
				zNodes.remove(zNode);
			}
		}
		return zNodes;
	}

	/* (非 Javadoc) 
	* Title: queryRoleByRoleId
	* TODO(根据id查询角色)
	* @param roleId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#queryRoleByRoleId(java.lang.String)
	*/
	@Override
	public Role queryRoleByRoleId(String roleId) {
		Role role = roleMapper.queryRoleByRoleId(roleId);
		return role;
	}

	/* (非 Javadoc) 
	* Title: queryRolesByUserId
	* TODO(用户所拥有的角色)
	* @param userId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#queryRolesByUserId(java.lang.String)
	*/
	@Override
	public List<ZNode> queryRolesByUserId(String userId) {
		List<Role> list = roleMapper.queryRolesByUserId(userId);
		if(list.size()>0){
			List<ZNode> zNodes= new ArrayList<ZNode>();
			for (Role role : list) {
				ZNode zNode = new ZNode();
				zNode.setId(role.getRoleId());
				zNode.setChecked(true);
				zNodes.add(zNode);
			}
			return zNodes;
		}else{
			return null;
		}
	}

	/* (非 Javadoc) 
	* Title: addRole
	* TODO(创建角色)
	* @param role 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#addRole(com.cpiinfo.iisp.manager.entity.Role)
	*/
	@Override
	public void addRole(RoleDTO role) {
		Role r = new Role();
		String roleId="";
		r.setUpdateTime(DateUtils.now());
		if(!StringUtils.isEmpty(role.getRoleDesc()))
		r.setRoleDesc(role.getRoleDesc());
		r.setState("1");
		r.setRoleName(role.getRoleName());
		if(!StringUtils.isEmpty(role.getId())){
			roleId=role.getId();
			r.setRoleId(roleId);
			roleMapper.updateRole(r);
		}else {
			//新增角色信息
			roleId = UUID.randomUUID().toString().replace("-", "");
			r.setRoleId(roleId);
			roleMapper.addRole(r);
		}
		//修改角色的权限
		String[] resourceIds = role.getResourceIds();
		List<Map<String,Object>> mapList = new ArrayList<>();
		for (String resourceId : resourceIds) {
			Map<String,Object> params = new HashMap<>();
		    params.put("ziyuan",resourceId);
			mapList.add(params);
		    //判断如果存在上级菜单,就把上级菜单的id加进去
			String parent = getParentResource(resourceId);
			if(!StringUtils.isEmpty(parent)&&!"0".equals(parent)){
				Map<String,Object> params1 = new HashMap<>();
				params1.put("ziyuan",parent);
				mapList.add(params1);
			}
		}
		updateMenusByRoleId(roleId,mapList);
	}

	private String getParentResource(String resourceId) {
		return roleMapper.getParentResource(resourceId);
	}

	/* (非 Javadoc) 
	* Title: updateRole
	* TODO(修改角色)
	* @param role 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#updateRole(com.cpiinfo.iisp.manager.entity.Role)
	*/
	@Override
	public void updateRole(Role role) {
		role = parseRole(role);
		role.setUpdateTime(DateUtils.now());
		roleMapper.updateRole(role);
	}

	/* (非 Javadoc) 
	* Title: queryRoleById
	* TODO(根据id查询角色)
	* @param roleId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#queryRoleById(java.lang.String)
	*/
	@Override
	public Role queryRoleById(String roleId) {
		Role role = roleMapper.queryRoleById(roleId);
		return role;
	}

	/* (非 Javadoc) 
	* Title: delRoleById
	* TODO(删除角色)
	* @param roleId 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#delRoleById(java.lang.String)
	*/
	@Override
	public Result delRoleById(String roleId) {
		String[] roleIds = roleId.split(",");
		for (String rId : roleIds) {
			Integer i=roleMapper.findUserRole(rId);
			if(i>0){
				return new Result().error("所删除角色下还存在关联的用户,请删除用户或修改用户角色后再删除角色!");
			}
			if("888950a1f87e4165a4f6270b07212840".equals(rId)){
				return new Result().error("不允许删除系统管理员的角色!");
			}
		}
		for (String rId : roleIds) {
			roleMapper.delRoleById(rId);
			roleMapper.deleteMenusByRoleId(rId);
		}
		return new Result().ok("删除成功!");
	}
	
	/** 
	* @Title: parseRole 
	* @Description: TODO(在创建和修改角色时，对是否 是管理员和几级管理员做绑定) 
	* @param role
	* @return
	*/
	private Role parseRole(Role role){
		int isAdmin = Integer.parseInt(role.getRoot());
		if(isAdmin == 0 ){
			role.setLevel("0");
		}else{
			if(role.getUnit().equals("100000")){
				role.setLevel("1");
			}else{
				String currentUserId = role.getUpdateUser();
				List<Role> roleList = roleMapper.queryRolesByUserId(currentUserId);
				int currentUsersRoleLevel =0;
				for (Role parentAdminRole : roleList) {
					if(Integer.parseInt(parentAdminRole.getRoot()) == 1){
						currentUsersRoleLevel = Integer.parseInt(parentAdminRole.getLevel());
					}
				}
				role.setLevel(String.valueOf(++currentUsersRoleLevel));
			}
			
		}
		return role;
	}

	/* (非 Javadoc) 
	* Title: queryRoleListByOrgCode
	* TODO(根据单位code查询角色列表)
	* @param orgCode
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#queryRoleListByOrgCode(java.lang.String)
	*/
	@Override
	public List<Role> queryRoleListByOrgCode(String orgCode) {
		List<Role> list = roleMapper.queryRoleListByOrgCode(orgCode);
		return list;
	}
	
	/* (非 Javadoc) 
	* Title: queryRoleListByOrgCode
	* TODO(查询所有角色列表)
	* @param orgCode
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#queryRoleList
	*/
	@Override
	public List<Role> queryRoleList() {
		List<Role> list = roleMapper.queryRoleList();
		return list;
	}

	/* (非 Javadoc) 
	* Title: queryOperListByRoleAndResourceId
	* TODO(根据角色id和资源id查询操作列表)
	* @param roleId
	* @param resourceId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#queryOperListByRoleAndResourceId(java.lang.String, java.lang.String)
	*/
	@Override
	public List<ZNode> queryOperListByRoleAndResourceId(String userCode, String roleId, String resourceId) {
		//资源对应的操作列表
		List<Oper> allOperList = menuMapper.queryOptListByResourceId(resourceId);
		List<ZNode> zNodes =new ArrayList<ZNode>();
		//用户的角色列表
		List<Role> usersRoleList = roleMapper.queryRolesByUserCode(userCode);
		Role adminRole = new Role();
		for (Role role : usersRoleList) {
			if(Integer.parseInt(role.getRoot()) == 1){
				adminRole = role;
			}
		}
		int level  = Integer.parseInt(adminRole.getLevel());
		Map<String, String> paramMap = parseMap(adminRole.getRoleId(), resourceId);
		//根据管理员角色id和资源id查询某个角色拥有某个资源的操作列表
		List<Oper> adminOperList = menuMapper.queryOperListByRoleAndResourceId(paramMap);
		
		Map<String, String> queryMap = parseMap(roleId, resourceId);
		List<Oper> roleOperList = menuMapper.queryOperListByRoleAndResourceId(queryMap);
		if(level == 1){
			zNodes = parseSuperRootOptZNodeList(allOperList, roleOperList);
		}else{
			zNodes = parseRootOptZNodeList(allOperList, adminOperList, roleOperList);
		}
		return zNodes;	
	}
	
	/** 
	* @Title: parseMap 
	* @Description: TODO(把参数封装成map) 
	* @param roleId
	* @param resourceId
	* @return
	*/
	private Map<String, String> parseMap(String roleId, String resourceId){
		Map<String, String> paramsMap= new HashMap<String, String>();
		paramsMap.put("roleId", roleId);
		paramsMap.put("resourceId", resourceId);
		return paramsMap;
	}
	
	/** 
	* @Title: parseOptZNodeList 
	* @Description: TODO(转换超级管理员的操作列表) 
	* @param allOperList
	* @param roleOperList
	* @return
	*/
	private List<ZNode> parseSuperRootOptZNodeList(List<Oper> allOperList, List<Oper> roleOperList){
		List<ZNode> zNodes = new ArrayList<>();
		for (Oper oper : allOperList) {
			ZNode zNode = new ZNode();
			zNode.setId(oper.getId());
			zNode.setName(oper.getName());
			zNode.setChecked(false);
			for (Oper roleOper : roleOperList) {
				if(roleOper.getId().equals(oper.getId())){
					zNode.setChecked(true);
				}
			}
			zNodes.add(zNode);
		}
		return zNodes;
	}
	
	/** 
	* @Title: parseRootOptZNodeList 
	* @Description: TODO(封装非超级管理员下的操作列表) 
	* @param allOperList
	* @param adminOperList
	* @param roleOperList
	* @return
	*/
	private List<ZNode> parseRootOptZNodeList(List<Oper> allOperList, List<Oper> adminOperList, List<Oper> roleOperList){
		List<ZNode> zNodes = new ArrayList<>();
		for (Oper oper : allOperList) {
			ZNode zNode = new ZNode();
			zNode.setId(oper.getId());
			zNode.setName(oper.getName());
			zNode.setChecked(false);
			for (Oper roleOpt: roleOperList) {
				if(oper.getId().equals(roleOpt.getId())){
					zNode.setChecked(true);
				}
			}
			zNode.setChkDisabled(true);
			for (Oper userOpt : adminOperList) {
				if(oper.getId().equals(userOpt.getId())){
					zNode.setChkDisabled(false);
				}
			}
			zNodes.add(zNode);
		}
		return zNodes;
	}

	/* (非 Javadoc) 
	* Title: queryResAndOptList
	* TODO(根据角色id查询资源及资源下面的操作)
	* @param roleId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#queryResAndOptList(java.lang.String)
	*/
	@Override
	public List<Map<String,Object>> queryResAndOptList(String roleId) {
		List<String> menusByRole = menuMapper.listMenusIdByRoleId(roleId);
		List<Map<String,Object>> list =parseResAndOpt(menusByRole,  roleId);
		return list;
	}
	
	/** 
	* @Title: parseResAndOpt 
	* @Description: TODO(封装资源及资源下面的操作) 
	* @param menusByRole
	* @param roleId
	* @return
	*/
	private List<Map<String,Object>> parseResAndOpt(List<String> menusByRole, String roleId){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (String resourceId : menusByRole) {
			Map<String, String> queryMap = parseMap(roleId, resourceId);
			List<Oper> roleOperList = menuMapper.queryOperListByRoleAndResourceId(queryMap);
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ziyuan", resourceId);
			paramMap.put("opts", roleOperList);
			list.add(paramMap);
		}
		return list;
	}

	/* (非 Javadoc) 
	* Title: getRoleCount
	* TODO(根据单位编码查询该单位下的角色总条数)
	* @param queryMap
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IRoleService#getRoleCount(java.util.Map)
	*/
	@Override
	public int getRoleCount(String unitCode, String roleName) {
		Map<String, String> paramsMap = Maps.newHashMap();
		paramsMap.put("unitCode", unitCode);
		if(!StringUtils.isEmpty(roleName)){
			paramsMap.put("roleName", roleName);
		}else{
			paramsMap.put("roleName", "");
		}
		int count = roleMapper.getRoleCount(paramsMap);
		return count;
	}

	/* (非 Javadoc) 
	* <p>Title: listRolesByOrgCode</p> 
	* <p>Description: </p> 
	* @param list
	* @param currentPage
	* @param showCount
	* @param roleName
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IRoleService#listRolesByOrgCode(java.util.List, java.lang.String, java.lang.String, java.lang.String)
	*/
	@Override
	public List<Role> listRolesByOrgCode(List<String> list, String currentPage, String showCount, String roleName) {
		Map<String, Object> params= parseMap1(list, currentPage, showCount, roleName);
		return roleMapper.listRolesByOrgCode(params, new PageRequest(Integer.parseInt(currentPage), Integer.parseInt(showCount)));
	}

	/** 
	* @Title: parseMap1 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param list
	* @param @param currentPage
	* @param @param showCount
	* @param @param roleName
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	private Map<String, Object> parseMap1(List<String> list, String currentPage, String showCount, String roleName) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("unitCode", list);
		int beginCount = (Integer.parseInt(currentPage) -1) * Integer.parseInt(showCount);
		paramsMap.put("beginCount", beginCount);
		paramsMap.put("showCount", Integer.parseInt(showCount)+beginCount);
		if(!StringUtils.isEmpty(roleName)){
			paramsMap.put("roleName", roleName);
		}else{
			paramsMap.put("roleName", "");
		}
		return paramsMap;
	}

	/* (非 Javadoc) 
	* <p>Title: getRoleCountByOrgCode</p> 
	* <p>Description: </p> 
	* @param orgCode
	* @param roleName
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IRoleService#getRoleCountByOrgCode(java.util.List, java.lang.String)
	*/
	@Override
	public int getRoleCountByOrgCode(List<String> orgCode, String roleName) {
		Map<String, Object> paramsMap = Maps.newHashMap();
		paramsMap.put("unitCode", orgCode);
		if(!StringUtils.isEmpty(roleName)){
			paramsMap.put("roleName", roleName);
		}else{
			paramsMap.put("roleName", "");
		}
		int count = roleMapper.getRoleCountOrgCode(paramsMap);
		return count;
	};
	
	/** 
	* @Title: queryOperatePermission 
	* @Description: 根据人员和菜单查询相应的操作按钮权限 
	* @return
	*/
	@Override
	public List<Oper> queryOperatePermission(String userCode, String menuUrl){
		List<Menu> menus = menuMapper.queryResourceByUrl(menuUrl);
		
		//集成到信息导航时，访问地址是全路径（含协议,ip,port等）
		if((menus == null || menus.size() == 0) && menuUrl != null && menuUrl.startsWith("http")) {
			int ipos = menuUrl.indexOf("/", 10);
			if(ipos > 0) {
				menuUrl = menuUrl.substring(ipos);
				menus = menuMapper.queryResourceByUrl(menuUrl);
			}
		}
		Menu menu = menus == null || menus.size() == 0 ? null : menus.get(0);
		if(menu != null) {
			List<Oper> opers = menuMapper.queryOptListByResourceId(menu.getId());
			List<String> userOpers = roleMapper.queryUserResourceOperations(userCode, menu.getId());
			for(Oper op : opers) {
				op.setEnabled(userOpers.contains(op.getId()));
			}
			return opers;
		}
		else {
			return Collections.emptyList();
		}
	}

	@Override
	public Result pageRole(String roleName, String page, String limit) {
		List<Role> roles = roleMapper.pageRole(roleName);
		List<Role> role= new ArrayList<>();
		if(!StringUtils.isEmpty(page)&&!StringUtils.isEmpty(limit)){
			//分页
			if(roles!=null&&roles.size()>0)
			role = pageByCurrentList(roles, Integer.parseInt(limit), Integer.parseInt(page));
		}
		role=roles;
		List<RoleAndResourceDTO> list = new ArrayList<>();
		for (Role role1 : role) {
			RoleAndResourceDTO r = new RoleAndResourceDTO();
			BeanUtils.copyProperties(role1,r);
			r.setResourcesLsit(getResourcesLsit(r.getRoleId()));
			list.add(r);
		}

		return new Result<>().ok(new PageData<RoleAndResourceDTO>(list,roles.size()));
	}

	/**
	 * 获取角色具有的权限菜单
	 * @param roleId
	 * @return
	 */
	private List<String> getResourcesLsit(String roleId) {
		List<String > resourcesIds = roleMapper.getResourcesLsit(roleId);
		List<String> names = new ArrayList<>();
		for (String id : resourcesIds) {
			String name =menuMapper.getNameById(id);
			if(name!=null)
			names.add(name);
		}
		return names;
	}

	private List<Role> pageByCurrentList(List list, int pagesize, int currentPage) {
		int totalcount = list.size();
		int pagecount = 0;
		List<Role> subList;
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

}
