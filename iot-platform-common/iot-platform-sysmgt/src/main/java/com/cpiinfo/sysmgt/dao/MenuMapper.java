package com.cpiinfo.sysmgt.dao;

import com.cpiinfo.iot.mybatis.dto.PageRequest;
import com.cpiinfo.sysmgt.api.model.SysResource;
import com.cpiinfo.sysmgt.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MenuMapper {
	/**
	 * 
	* @Title: insert 
	* @Description: TODO(插入资源记录) 
	* @param record
	* @return
	 */
    int insert(Menu record);
    
    /**
     * 
    * @Title: insertSelective 
    * @Description: TODO(插入资源记录) 
    * @param record
    * @return
     */
    int insertSelective(Menu record);
    /**
     * 
    * @Title: listMenus 
    * @Description: TODO(查询资源列表) 
    * @return
     */
    List<Menu> listMenus();
    
    /**
     * 
    * @Title: listMenusByPgenation 
    * @Description: TODO(查询资源列表-分页查询) 
    * @param para
    * @return
     */
    List<Menu> listMenusByPgenation(Map<String, Object> para, PageRequest pageRequest);
    
    /**
     * 
    * @Title: listMenusByPid 
    * @Description: TODO(根据资源父ID查询资源列表) 
    * @param pId
    * @return
     */
    List<Menu> listMenusByPid(String pId);
    
    /**
     * 
    * @Title: listMenusOper 
    * @Description: TODO(查询所有操作列表) 
    * @return
     */
    List<Oper> listMenusOper();
    
    /** 
    * @Title: queryOptListByResourceId 
    * @Description: TODO(根据资源id查询对应的操作列表) 
    * @param resourceId
    * @return
    */
    List<Oper> queryOptListByResourceId(String resourceId);
    
    /**
     * 
    * @Title: addMenuOpers 
    * @Description: TODO(批量插入资源和操作关联表数据) 
    * @param opers
    * @return
     */
    int  addMenuOpers(List<MenuOper> opers);
    
    /**
     * 
    * @Title: detaillistMenus 
    * @Description: TODO(通过资源ID获取资源详情信息) 
    * @param menuId
    * @return
     */
    Menu detaillistMenus(String menuId);
    
    /**
     * 
     * 根据父单位id 查询父单位的资源路径
     * @param parentId
     * @return 
     * Date:2018年8月3日上午9:26:44
     */
    Menu queryResPath(String parentId);
    /**
     * 
    * @Title: updateResourceById 
    * @Description: TODO(通过资源Id更新资源) 
    * @param record
    * @return
     */
    int updateResourceById(Menu record);
    
    /**
     * 
    * @Title: deleteReOper 
    * @Description: TODO(通过资源ID删除资源和操作关系表) 
    * @param menuId
    * @return
     */
    int deleteReOper(String menuId);
    
    /**
     * 
    * @Title: deleteResourceById 
    * @Description: TODO(通过资源ID删除资源，此处为逻辑删除，即state状态 0删除 1正常) 
    * @param menuId
    * @return
     */
    int deleteResourceById(String menuId);
    
    /**
     * 
    * @Title: queryDistinctResourceByRole 
    * @Description: TODO(根据角色去重查询资源 ) 
    * @param userId
    * @return
     */
    List<Menu>  queryDistinctResourceByRole(String userId);
    
    /**
     * 
    * @Title: queryOperListByRoleAndResourceId 
    * @Description: TODO(根据资源ID和角色ID查询操作列表) 
    * @param quryMap
    * @return
     */
    List<Oper> queryOperListByRoleAndResourceId(Map<String, String> quryMap);
    
    /**
     * 
    * @Title: querySourceOpt 
    * @Description: TODO(查询主能源类型列表 ) 
    * @return
     */
    List<Dictionary> querySourceOpt();
    
    /** 
	* @Title: listMenusIdByRoleId 
	* @Description: TODO(根据角色id查询该角色所拥有的资源id) 
	* @param roleId
	* @return
	*/
	public List<String> listMenusIdByRoleId(String roleId);
	
	/** 
	* @Title: listMenusByRoleId 
	* @Description: TODO(根据角色id查询该角色所拥有的资源) 
	* @param adminId
	* @return
	*/
	public List<Menu> listMenusByRoleId(String adminId);
	
	/**
	 * 
	* @Title: detailAlllistMenus 
	* @Description: TODO(查询全部资源信息) 
	* @return
	 */
	List<Menu> detailAlllistMenus();
	
	/**
	 * 
	* @Title: detaillistUserMenusByUserId 
	* @Description: TODO(根据用户ID数组查询这些用户拥有的资源) 
	* @param userIds
	* @return
	 */
	List<UserMenu> detaillistUserMenusByUserId(List<String> userIds);

	/** 
	 * @Title: detaillistMenusByUserId 
	 * @Description: TODO(根据用户ID查询用户拥有的资源) 
	 * @param id
	 * @return
	 */
	List<Menu> detaillistMenusByUserId(String id);

	/**
	 * 
	* @Title: listMenusByPidPgenation 
	* @Description: TODO(根据资源父ID查询资源列表-分页查询) 
	* @param paras
	* @return
	 */
	List<Menu> listMenusByPidPgenation(Map<String, Object> paras, PageRequest pageRequeset);
	
	List<ResourceVo> getbasefun();
	List<ResourceVo> getbasefunBypid(String id);
	ResourceVo getbasefunByid(String id);
	
	
	List<ResourceVo> getbasefunbyroleuser(Map<String, Object> paras);
	List<ResourceVo> getbasefunBypiduserid(Map<String, Object> paras);
	ResourceVo getbasefunByiduser(Map<String, Object> paras);
	
	ResourceVo getBaseFunByIdUserId(Map<String, Object> paras);
	List<ResourceVo> getBaseFunByPidUserId(Map<String, Object> paras);
	List<ResourceVo> findallmenu();
	List<ResourceVo> findusermenubyuserId(Map<String, Object> paras);

	/** 
	* @Title: queryRecursionResource 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param menuId
	* @param @return    设定文件 
	* @return List<Menu>    返回类型 
	* @throws 
	*/
	List<Menu> queryRecursionResource(String menuId);

	/** 
	* @Title: finddeptbyusercode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param userCode
	* @param @return    设定文件 
	* @return List<String>    返回类型 
	* @throws 
	*/
	List<String> finddeptbyusercode(String userCode);
	List<String> findParentIfTwoLevel(String userId);
	

	/** 
	* @Title: findOrgtreebycodes 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param map
	* @param @return    设定文件 
	* @return List<ZNode>    返回类型 
	* @throws 
	*/
	List<ZNode> findOrgtreebycodes(Map<String, Object> map);
	
	/**
	 * 依据资源url查询菜单资源
	 * @param
	 * @return
	 */
	List<Menu> queryResourceByUrl(@Param("id")String id);
	
	List<SysResource> queryResourceByUserCode(@Param("userCode")String userCode,
			@Param("systemCodes") String[] systemCodes,
			@Param("includeButton")String includeButton);

    String getNameById(@Param("id")String id);

    List<Menu> select1();

	List<Menu> selectChildren(@Param("id")String id);

    List<SysResource> getButtonByMenusUrl(@Param("id")String id);
}