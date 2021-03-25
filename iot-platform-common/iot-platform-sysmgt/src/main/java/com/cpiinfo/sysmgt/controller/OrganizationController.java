package com.cpiinfo.sysmgt.controller;

import com.cpiinfo.sysmgt.entity.Organization;
import com.cpiinfo.sysmgt.service.OrganizationService;
import com.cpiinfo.sysmgt.utils.Result;
import com.cpiinfo.iot.commons.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Title: OrganizationController.java
 * @Package com.cpiinfo.iisp.manager.controller
 * @Description: TODO(定义单位Controller)
 * @author 牛棚
 * @date 2016年8月30日 下午1:19:33
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/org")
@Api(value="sys-mgt:单位配置",tags={"sys-mgt:单位配置"})
public class OrganizationController {
	@Value("${iot-platform.temp-file-path:}")
	private String tempFilePath;
	@Autowired
    OrganizationService orgService;


	/**
	 * @Title: PageOrgList
	 * @Description: TODO(分页显示单位列表)
	 * @param
	 * @return
	 */
	@ApiOperation(value = "查询单位列表树形", notes = "查询单位列表树形")
	@RequestMapping(value = "/getOrgTree", method=RequestMethod.GET)
	@ResponseBody
	public Result getOrgTree() {
		return orgService.getOrgTree();
	}

	/**
	 * @Title: insertOrg
	 * @Description: TODO(添加单位)
	 * @param org
	 * @return
	 */
	@ApiOperation(value = "添加单位", notes = "添加单位")
	@RequestMapping(value = "/insertOrg", method=RequestMethod.POST)
	@ResponseBody
	public Result insertOrg(@RequestBody Organization org) {

		return orgService.insertOrg(org);
	}


	/**
	 * @Title: deleteById
	 * @Description: TODO(删除单位)
	 * @param
	 * @return
	 */
	@ApiOperation(value = "删除单位", notes = "删除单位")
	@RequestMapping(value = "/deleteById", method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteById(String id) {

		return orgService.deleteById(id);
	}

	/**
	 * @Title: updateOrg
	 * @Description: TODO(修改单位)
	 * @param
	 * @param org
	 * @return
	 */
	@ApiOperation(value = "修改单位", notes = "修改单位")
	@RequestMapping(value = "/updateOrg", method=RequestMethod.PUT)
	@ResponseBody
	public Result updateOrg( @RequestBody Organization org) {

		return orgService.updateOrg(org);
	}

	/**
	 * @Title: userList
	 * @Description: TODO(根据id查询)
	 * @param
	 * @return
	 */
	@ApiOperation(value = "根据id查询", notes = "根据id查询")
	@RequestMapping(value="/queryOrgById", method=RequestMethod.GET)
	@ResponseBody
	public Result queryOrgById(@ApiParam(value = "id" ,name = "id") @RequestParam String id){
		return orgService.queryOrgById(id);
	}


	@ApiOperation(value = "批量导入--组织结构", notes = "批量导入设备--组织结构")
	@PostMapping(value = "/bulkImportOrganization")
	@ResponseBody
	public Result bulkImportOrganization(@RequestParam(value = "mfile", required = true) MultipartFile mfile) throws IOException {
		return orgService.bulkImportOrganization(mfile);
	}

	@GetMapping(value = "/downloadTemplate")
	@ApiOperation(value = "下载模板")
	@ResponseBody
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String fileName = "organization.xlsx";
		String path = tempFilePath + File.separator;
		FileUtils.downLoad(response, fileName, path);
	}











}
