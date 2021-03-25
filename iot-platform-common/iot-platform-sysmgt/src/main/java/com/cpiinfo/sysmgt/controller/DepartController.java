/**
 * 
 */
package com.cpiinfo.sysmgt.controller;

import com.cpiinfo.sysmgt.dto.DepartTreeDTO;
import com.cpiinfo.sysmgt.entity.Depart;
import com.cpiinfo.sysmgt.service.DepartService;
import com.cpiinfo.sysmgt.service.UserService;
import com.cpiinfo.sysmgt.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 
 * @Title: DepartController.java
 * @Package com.cpiinfo.jyspsc.sys.manager.controller
 * @Description: TODO()
 * @author 程志宏
 * @date 2017年7月13日下午2:22:04
 * @version V1.0
 */
@Controller
@RequestMapping(value="/depart")
@Api(value="sys-mgt:部门配置",tags={"sys-mgt:部门配置"})
public class DepartController {
	private static final Logger log = LoggerFactory.getLogger(DepartController.class);
	
	@Autowired
    DepartService departService;

	@Autowired
    UserService userService;
	
	/** 
	* @Title: userList 
	* @Description: TODO(查询部门列表)
	* @param
	* @return
	*/
	@ApiOperation(value = "查询部门列表树形", notes = "查询部门列表树形")
	@RequestMapping(value="/getDepartTreeByOrgCode", method=RequestMethod.GET)
	@ResponseBody
	public Result<List<DepartTreeDTO>> getDepartTreeByOrgCode(@RequestParam(value = "orgCode" ,required = true) String orgCode){
		List<DepartTreeDTO> list = departService.getDepartTreeByOrgCode(orgCode);
		return new Result<List<DepartTreeDTO>>().ok(list);
	}
	
	/** 
	* @Title: userList 
	* @Description: TODO(添加部门) 
	* @param
	* @return
	*/
	@ApiOperation(value = "添加部门", notes = "添加部门")
	@RequestMapping(value="/addDepart", method=RequestMethod.POST)
	@ResponseBody
	public Result addDepart(@RequestBody Depart depart){
		return departService.addDepart(depart);
	}
	
	/** 
	* @Title: userList 
	* @Description: TODO(删除部门)
	* @param
	* @return
	*/
	@ApiOperation(value = "删除部门", notes = "删除部门")
	@RequestMapping(value="/deleteDepartById", method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteDepartById(String id){
		return departService.deleteDepartById(id);
	}

	/**
	 * @Title: userList
	 * @Description: TODO(根据id查询)
	 * @param
	 * @return
	 */
	@ApiOperation(value = "根据id查询", notes = "根据id查询")
	@RequestMapping(value="/queryDepartById", method=RequestMethod.GET)
	@ResponseBody
	public Result queryDepartById(@ApiParam(value = "id" ,name = "id") @RequestParam String id){
		return departService.queryDepartById(id);
	}
	
	/** 
	* @Title: userList 
	* @Description: TODO(修改部门) 
	* @param
	* @return
	*/
	@ApiOperation(value = "修改部门", notes = "修改部门")
	@RequestMapping(value="/updDepartById", method=RequestMethod.PUT)
	@ResponseBody
	public Result updDepartById(@RequestBody Depart depart){
		return departService.updateDepart(depart);
	}
	


	
}

