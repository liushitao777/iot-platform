package com.cpiinfo.sysmgt.service.impl;

import com.cpiinfo.sysmgt.dao.DepartMapper;
import com.cpiinfo.sysmgt.dao.OrganizationMapper;
import com.cpiinfo.sysmgt.dto.OrganizationExcelDTO;
import com.cpiinfo.sysmgt.dto.OrganizationTreeDTO;
import com.cpiinfo.sysmgt.entity.*;
import com.cpiinfo.sysmgt.service.OrganizationService;
import com.cpiinfo.sysmgt.utils.Result;
import com.cpiinfo.iot.commons.utils.ConvertUtils;
import com.cpiinfo.iot.commons.utils.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Title: OrganizationServiceImpl.java
 * @Package com.cpiinfo.iisp.manager.service
 * @Description: TODO(定义单位的service实现层)
 * @author wuchangjiang
 * @date 2020年05月08日 上午9:34:08
 * @version V1.07
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	@Autowired
    OrganizationMapper orgMapper;
	@Autowired
    DepartMapper departMapper;


	@Override
	public Result getOrgTree() {
		OrganizationExample example = new OrganizationExample();
		example.or().andParentCodeEqualTo("0");
		List<Organization> organizations = orgMapper.selectByExample(example);
			//转为树形结构
		List<OrganizationTreeDTO> res = new ArrayList<>();
		if(organizations!=null && organizations.size()>0){
			res= ConvertUtils.sourceToTarget(organizations,OrganizationTreeDTO.class);
			for (OrganizationTreeDTO re : res) {
				List<OrganizationTreeDTO> category = orgMapper.getCategory(re.getId());
				if(category!=null){
					re.setChildren(category);
				}
			}
		}
		return new Result().ok(res);
	}

	@Override
	public Result insertOrg(Organization org) {
		OrganizationExample example = new OrganizationExample();
		example.or().andOrgCodeEqualTo(org.getOrgCode());
		long orgCode = orgMapper.countByExample(example);
		if(orgCode >= 1){
			return new Result().error("组织机构编码重复了!");
		}
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		org.setId(uuid);
		org.setsState("1");
		org.setCreateAt(new Date());
		org.setUpdateAt(new Date());
		int insert = orgMapper.insert(org);
		if(insert >= 1){
			return new Result().ok("新增成功!");
		}else {
			return new Result().error("新增失败!");
		}
	}
	@Override
	public Result deleteById(String id) {
		OrganizationExample example = new OrganizationExample();
		example.or().andParentCodeEqualTo(id);
		long count = orgMapper.countByExample(example);
		if(count >= 1){
			return new Result().error("存在下级节点,删除失败!");
		}else {
			OrganizationExample org= new OrganizationExample();
			org.or().andIdEqualTo(id);
			int delete = orgMapper.deleteByExample(org);
			if(delete >= 1){
				return new Result().ok("删除成功!");
			}else {
				return new Result().error("删除失败!");
			}
		}
	}

	@Override
	public Result updateOrg(Organization org) {
		//修改部门表编码
		OrganizationExample example = new OrganizationExample();
		example.or().andOrgCodeEqualTo(org.getOrgCode());
		long orgCode = orgMapper.countByExample(example);
		if(orgCode == 0){
			Organization organization = orgMapper.selectByPrimaryKey(org.getId());
			DepartExample departExample = new DepartExample();
			departExample.or().andOrgCodeEqualTo(organization.getOrgCode());
			Depart depart = new Depart();
			depart.setOrgCode(org.getOrgCode());
			departMapper.updateByExampleSelective(depart,departExample);
		}

		int update = orgMapper.updateByPrimaryKeySelective(org);
		if(update >= 1){
						return new Result().ok("修改成功!");
		}else {
			return new Result().error("修改失败!");
		}
	}

	@Override
	public List<ZNode> queryUserAllOrg(String userCode) {
		return null;
	}

	@Override
	public Result queryOrgById(String id) {
		Organization organization = orgMapper.selectByPrimaryKey(id);
		return new Result().ok(organization);
	}

	@Override
	public Result bulkImportOrganization(MultipartFile mfile) {
		List<OrganizationExcelDTO> excleList= FileUtils.importExcel(mfile, 0, 1, OrganizationExcelDTO.class);
		List<Organization> organizationList = new ArrayList<>();
		if(excleList != null && excleList.size()>0){
			organizationList = ConvertUtils.sourceToTarget(excleList, Organization.class);
		}
		List<String> orgCodeAdd = new ArrayList<>();
		organizationList.forEach(e -> orgCodeAdd.add(e.getOrgCode()));
		//查重
		OrganizationExample example = new OrganizationExample();
		example.or().andOrgCodeIn(orgCodeAdd);
		List<Organization> organizations = orgMapper.selectByExample(example);
		if(organizations != null  && organizations.size()>0 ){
			StringBuilder sb= new StringBuilder();
			organizations.forEach(e -> sb.append(e.getOrgCode()).append(" "));
			return new Result().error(sb.toString() +"组织机构编码重复了!");
		}
		for (Organization organization : organizationList) {
			if (StringUtils.isNotEmpty(organization.getOrgCode())) {
				organization.setId(organization.getOrgCode());
				organization.setsState("1");
				organization.setCreateAt(new Date());
				organization.setUpdateAt(new Date());
				orgMapper.insert(organization);
			}
		}
		return new Result().ok("导入成功!");
	}
}
