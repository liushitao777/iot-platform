package com.cpiinfo.sysmgt.service.impl;

import com.cpiinfo.sysmgt.dao.DepartMapper;
import com.cpiinfo.sysmgt.dto.DepartTreeDTO;
import com.cpiinfo.sysmgt.entity.Depart;
import com.cpiinfo.sysmgt.entity.DepartExample;
import com.cpiinfo.sysmgt.utils.Result;
import com.cpiinfo.iot.commons.utils.ConvertUtils;
import com.cpiinfo.sysmgt.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DepartServiceImpl implements DepartService {
	
	@Autowired
    DepartMapper departMapper;


	@Override
	public List<DepartTreeDTO> getDepartTreeByOrgCode(String orgCode) {
		DepartExample departExample = new DepartExample();
		departExample.or().andOrgCodeEqualTo(orgCode).andParentIdEqualTo("0");
		List<Depart> departs = departMapper.selectByExample(departExample);
		//转为树形结构
		List<DepartTreeDTO> res = new ArrayList<>();
		if(departs!=null && departs.size()>0){
			res= ConvertUtils.sourceToTarget(departs,DepartTreeDTO.class);
			for (DepartTreeDTO re : res) {
				List<DepartTreeDTO> category = departMapper.getCategory(re.getId());
				if(category!=null){
					re.setChildren(category);
				}
			}
		}
		return res;
	}

	@Override
	public Result addDepart(Depart depart) {
		DepartExample example = new DepartExample();
		example.or().andOrgCodeEqualTo(depart.getOrgCode()).andDepartNameEqualTo(depart.getDepartName());
		long count = departMapper.countByExample(example);
		if(count >= 1){
			return  new Result().error("部门名称重复了!");
		}
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		depart.setId(uuid);
		depart.setDepartStatus("1");
		int insert = departMapper.insert(depart);
		if(insert >= 1){
			return new Result().ok("新增成功!");
		}else {
			return new Result().error("新增失败!");
		}
	}

	@Override
	public Result deleteDepartById(String id) {
		DepartExample example = new DepartExample();
		example.or().andParentIdEqualTo(id);
		long count = departMapper.countByExample(example);
		if(count >= 1){
			return new Result().error("存在下级节点,删除失败!");
		}else {
			DepartExample dep= new DepartExample();
			dep.or().andIdEqualTo(id);
			int delete = departMapper.deleteByExample(dep);
			if(delete >= 1){
				return new Result().ok("删除成功!");
			}else {
				return new Result().error("删除失败!");
			}
		}
	}

	@Override
	public Result updateDepart(Depart depart) {
		int update = departMapper.updateByPrimaryKeySelective(depart);
		if(update >= 1){
			return new Result().ok("修改成功!");
		}else {
			return new Result().error("修改失败!");
		}
	}

	@Override
	public Result queryDepartById(String id) {
		Depart depart = departMapper.selectByPrimaryKey(id);
		return new Result().ok(depart);
	}
}
