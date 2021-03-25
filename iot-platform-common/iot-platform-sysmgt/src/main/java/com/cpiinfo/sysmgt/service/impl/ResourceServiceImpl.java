package com.cpiinfo.sysmgt.service.impl;

import com.cpiinfo.iot.mybatis.dto.PageRequest;
import com.cpiinfo.iot.utility.DateUtils;
import com.cpiinfo.sysmgt.dao.MenuMapper;
import com.cpiinfo.sysmgt.dao.RoleMapper;
import com.cpiinfo.sysmgt.entity.Dictionary;
import com.cpiinfo.sysmgt.entity.Menu;
import com.cpiinfo.sysmgt.entity.Oper;
import com.cpiinfo.sysmgt.entity.ResourceVo;
import com.cpiinfo.sysmgt.service.ResourceService;
import com.cpiinfo.sysmgt.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 * 
* @Title: ResourceServiceImpl.java 
* @Package com.cpiinfo.iisp.manager.service
* @Description: TODO(资源管理服务接口实现类) 
* @author 孙德
* @date 2016年8月31日 下午3:23:08 
* @version V1.0
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	
	
	//依赖注入数据访问层接口
	@Autowired
    RoleMapper roleMapper;
	
	//依赖注入数据访问层接口
	@Autowired
    MenuMapper menuMapper;
	
	/* (非 Javadoc) 
	* Title: queryResource
	* TODO(查询资源信息)
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#queryResource()
	*/
	@Override
	public Result queryResource() {
		List<Menu> list =menuMapper.select1();
		if(list!=null&&list.size()>0){
			for (Menu menu : list) {
				List<Menu> listChildren=menuMapper.selectChildren(menu.getId());
				if(listChildren!=null&&listChildren.size()>0)
				menu.setChildren(listChildren);
			}
		}

		return new Result<>().ok(list);
	}

	
	/* (非 Javadoc) 
	* Title: queryResourceByPid
	* TODO(通过父ID查询资源信息)
	* @param pId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#queryResourceByPid(java.lang.String)
	*/
	@Override
	public List<Menu> queryResourceByPid(Map<String, Object> paras) {
		List<Menu> menus = null;
		if (paras.get("currentPage")!=null&&paras.get("showCount")!=null) {
			menus = menuMapper.listMenusByPidPgenation(paras, createPageRequest(paras));
		 }else{
			 menus = menuMapper.listMenusByPid(paras.get("pId").toString());
		 }
		return menus;
	}


	private PageRequest createPageRequest(Map<String, Object> paras) {
		return new PageRequest(Integer.parseInt(paras.get("currentPage").toString()), 
				Integer.parseInt(paras.get("showCount").toString()));
	}


	/* (非 Javadoc) 
	* Title: queryResourceOper
	* TODO(查询资源操作字典信息)
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#queryResourceOper()
	*/
	@Override
	public List<Oper> queryResourceOper() {
		List<Oper> opers = menuMapper.listMenusOper();
		return opers;
	}

	
	/* (非 Javadoc) 
	* Title: addResource
	* TODO(添加资源数据)
	* @param menu
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#addResource(com.cpiinfo.iisp.manager.entity.Menu)
	*/
	@Override
	public String addResource(Menu menu) {
		
		String result = "";
		String id = UUID.randomUUID().toString().replace("-", "");
		menu.setId(id);
		//状态  0删除   1正常
		menu.setState("1");
		menu.setUpdateTime(DateUtils.toString(new java.util.Date()));
		//查询父单位的资源路径
		String parentId = menu.getParentId();
		if(!StringUtils.isEmpty(parentId)){
			Menu m = menuMapper.queryResPath(parentId);
			if(null != m){
				String resPath = m.getResPath();
				if(!StringUtils.isEmpty(resPath)){
					menu.setResPath(resPath+"/"+id);
				}
			}

		}
		for (int i = 0; i < menu.getMenuOpers().size(); i++) {
			menu.getMenuOpers().get(i).setResourceId(menu.getId());
			menu.getMenuOpers().get(i).setId(UUID.randomUUID().toString().replace("-", ""));
		}
			menuMapper.insertSelective(menu);
		 if (menu.getMenuOpers()!=null&&menu.getMenuOpers().size()!=0) {
			 menuMapper.addMenuOpers(menu.getMenuOpers());
		 }
		 result = "success";
		return result;
	}


	/* (非 Javadoc) 
	* Title: queryResourceById
	* TODO(通过资源ID查询资源信息)
	* @param menuId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#queryResourceById(java.lang.String)
	*/
	@Override
	public Menu queryResourceById(String menuId) {
		Menu result =  menuMapper.detaillistMenus(menuId);
		return result;
	}


	/* (非 Javadoc) 
	* Title: updateResource
	* TODO(资源添加接口实现)
	* @param menu
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#updateResource(com.cpiinfo.iisp.manager.entity.Menu)
	*/
	@Override
	public String updateResource(Menu menu) {
		String result = "";
		// 状态 0删除 1正常
		menu.setState("1");
		menu.setUpdateTime(DateUtils.toString(new java.util.Date()));
		for (int i = 0; i < menu.getMenuOpers().size(); i++) {
			menu.getMenuOpers().get(i).setId(UUID.randomUUID().toString().replace("-", ""));
		}
		menuMapper.updateResourceById(menu);
		menuMapper.deleteReOper(menu.getId());
		if (menu.getMenuOpers() != null && menu.getMenuOpers().size() != 0) {
			menuMapper.addMenuOpers(menu.getMenuOpers());
		}
		result = "success";
		return result;
	}

	/* (非 Javadoc) 
	* Title: deleResource
	* TODO(根据资源ID删除资源信息)
	* @param menuId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#deleResource(java.lang.String)
	*/
	@Override
	public String deleResource(String menuId) {
		String result = "";
		menuMapper.deleteResourceById(menuId);
		menuMapper.deleteReOper(menuId);
		roleMapper.delRoleAndResourceByResourceId(menuId);
		result = "success";
		return result;
	}


	/* (非 Javadoc) 
	* Title: querySourceOpt
	* TODO(添加单位时的主要能源选项)
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#querySourceOpt()
	*/
	@Override
	public List<Dictionary> querySourceOpt() {
		List<Dictionary> list = null;
		list = menuMapper.querySourceOpt();
		return list;
	}

	
	/* (非 Javadoc) 
	* Title: getResourceCount
	* TODO(查询全部资源总数)
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#getResourceCount()
	*/
	@Override
	public int getResourceCount() {
		List<Menu>	menus = menuMapper.listMenus();
		return menus.size();
	}
	
	
	/* (非 Javadoc) 
	* Title: getResourceCountByPid
	* TODO(根据父ID查询其下资源总数)
	* @param pId
	* @return 
	* @see com.cpiinfo.iisp.manager.intf.IResourceService#getResourceCountByPid(java.lang.String)
	*/
	@Override
	public int getResourceCountByPid(String pId) {
		List<Menu>	menus = menuMapper.listMenusByPid(pId);
		return menus.size();
	}

	@Override
	public List<ResourceVo> getbasefun(){
		List<ResourceVo> relist =new ArrayList<ResourceVo>();
		
		List<ResourceVo> list= this.menuMapper.getbasefun();
		if(list!=null && list.size()>0){
			for(ResourceVo v : list){
				ResourceVo nodes = this.getbasefunBypid(v.getCid());
				relist.add(nodes);
			}
		}
		
		return relist;
	}
	
	@Override
	public ResourceVo getbasefunBypid(String id){
		ResourceVo vo = this.menuMapper.getbasefunByid(id);
		List<ResourceVo> childTreeNodes = this.menuMapper.getbasefunBypid(id);
		//遍历子节点
		if(childTreeNodes!=null && childTreeNodes.size()>0){
			for(ResourceVo child : childTreeNodes){
				ResourceVo n = getbasefunBypid(child.getCid()); //递归
				vo.getNodes().add(n);
			}
		}
		return vo;
	}
	//------一般人员
	@Override
	public List<ResourceVo> getbasefunbyroleuser(Map<String, Object> paras){
        List<ResourceVo> relist =new ArrayList<ResourceVo>();
		
		List<ResourceVo> list= this.menuMapper.getbasefunbyroleuser(paras);
		if(list!=null && list.size()>0){
			for(ResourceVo v : list){
				paras.put("id", v.getCid());
				ResourceVo nodes = this.getbasefunBypiduserid(paras);
				relist.add(nodes);
			}
		}
		
		return relist;
	}
	@Override
	public ResourceVo getbasefunBypiduserid(Map<String, Object> paras){
		ResourceVo vo = this.menuMapper.getbasefunByiduser(paras);//具体的一级菜单
		List<ResourceVo> childTreeNodes = this.menuMapper.getbasefunBypiduserid(paras);//一级菜单对应的2级菜单
		//遍历子节点
		if(childTreeNodes!=null && childTreeNodes.size()>0){
			for(ResourceVo child : childTreeNodes){
				paras.put("id", child.getCid());
				ResourceVo n = getbasefunByPiduserd(paras); //递归
				vo.getNodes().add(n);
			}
		}
		return vo;
	}
	
	public ResourceVo getbasefunByPiduserd(Map<String, Object> paras){
		ResourceVo vo = this.menuMapper.getBaseFunByIdUserId( paras);
		List<ResourceVo> childTreeNodes = this.menuMapper.getBaseFunByPidUserId(paras);
		//遍历子节点
		if(childTreeNodes!=null && childTreeNodes.size()>0){
			for(ResourceVo child : childTreeNodes){
				paras.put("id", child.getCid());
				ResourceVo n = getbasefunByPiduserd(paras); //递归
				vo.getNodes().add(n);
			}
		}
		return vo;
	}
	
	@Override
	public List<ResourceVo> findallmenu(){
		List<ResourceVo> list = this.menuMapper.findallmenu();
		return treeMenuList(list, "0");
	}
	
	@Override
	public List<ResourceVo> findusermenubyuserId(Map<String, Object> paras){
		List<ResourceVo> list = this.menuMapper.findusermenubyuserId(paras);
		return treeMenuList(list, "0");
	}
	
	
	/**
	 * 菜单树形结构 
	 * @param list
	 * @param parentId
	 * @return
	 */
	public List<ResourceVo> treeMenuList(List<ResourceVo> list, String parentId) { 
		List<ResourceVo> listnode = new ArrayList<ResourceVo>();
		for (ResourceVo v : list) {

			String menuId = v.getCid();
			String pid = v.getPid();
			if (parentId.equals(pid)) {
				List<ResourceVo> nodes = treeMenuList(list, menuId);
				v.setNodes(nodes);
				listnode.add(v);
			}
		}
		return listnode; 
	}


	/* (非 Javadoc) 
	* <p>Title: deleAllResource</p> 
	* <p>Description: </p> 
	* @param menuId
	* @return 
	* @see com.cpiinfo.jyspsc.sys.manager.intf.IResourceService#deleAllResource(java.lang.String)
	*/
	@Override
	public String deleAllResource(String menuId) {
		List<Menu> menuList = menuMapper.queryRecursionResource(menuId);
		for (Menu menu : menuList) {
			menuId = menu.getId();
			menuMapper.deleteResourceById(menuId);
			menuMapper.deleteReOper(menuId);
			roleMapper.delRoleAndResourceByResourceId(menuId);
		}
		return "success";
	} 

}
