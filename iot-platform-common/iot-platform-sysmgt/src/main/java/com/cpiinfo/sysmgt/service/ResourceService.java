package com.cpiinfo.sysmgt.service;

import com.cpiinfo.sysmgt.entity.Dictionary;
import com.cpiinfo.sysmgt.entity.Menu;
import com.cpiinfo.sysmgt.entity.Oper;
import com.cpiinfo.sysmgt.entity.ResourceVo;
import com.cpiinfo.sysmgt.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * 
* @Title: IResourceService.java 
* @Package com.cpiinfo.iisp.manager.intf
* @Description: TODO(资源管理服务层接口) 
* @author 孙德
* @date 2016年9月1日 下午4:59:37 
* @version V1.0
 */
public interface ResourceService {
	


	/**
	 * 
	* @Title: queryResourceByPid 
	* @Description: TODO(根据父ID查询其下的资源) 
	* @param paras
	* @return
	 */
	public List<Menu> queryResourceByPid(Map<String, Object> paras);

	/**
	 * 
	* @Title: queryResourceOper 
	* @Description: TODO(查询菜单挂载全部操作项字典数据) 
	* @return
	 */
	public List<Oper> queryResourceOper();
	
	/**
	 * 
	* @Title: addResource 
	* @Description: TODO(资源添加接口) 
	* @param menu
	* @return
	 */
	public String addResource(Menu menu);
	
	/**
	 * 
	* @Title: queryResourceById 
	* @Description: TODO(根据资源ID查询资源详情) 
	* @param menuId
	* @return
	 */
	public Menu queryResourceById(String menuId);
	
	/**
	 * 
	* @Title: updateResource 
	* @Description: TODO(资源更新接口) 
	* @param menu
	* @return
	 */
	public String updateResource(Menu menu);
	
	/**
	 * 
	* @Title: deleResource 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param menuId
	* @return
	 */
	public String deleResource(String menuId);

	
	/** 
	* @Title: querySourceOpt 
	* @Description: TODO(添加单位时的主要能源选项) 
	* @return
	*/
	public List<Dictionary> querySourceOpt();

	/**
	 * 
	* @Title: getResourceCount 
	* @Description: TODO(获取资源的总数) 
	* @return
	 */
	public int getResourceCount();

	/**
	 * 
	* @param pId 
	 * @Title: getResourceCountByPid 
	* @Description: TODO(根据父ID获取其下资源的总数) 
	* @return
	 */
	public int getResourceCountByPid(String pId);	
	
	public List<ResourceVo> getbasefun();
	public ResourceVo getbasefunBypid(String id);
	
	public List<ResourceVo> getbasefunbyroleuser(Map<String, Object> paras);
	public ResourceVo getbasefunBypiduserid(Map<String, Object> paras);
	public List<ResourceVo> findallmenu();
	public List<ResourceVo> findusermenubyuserId(Map<String, Object> paras);

	/** 
	* @Title: deleAllResource 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param menuId
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String deleAllResource(String menuId);


	Result queryResource();
}
