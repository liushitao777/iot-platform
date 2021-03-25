package com.cpiinfo.sysmgt.service.impl;

import com.cpiinfo.sysmgt.dao.OrgUnitInfoMapper;
import com.cpiinfo.sysmgt.entity.Organization;
import com.cpiinfo.sysmgt.service.OrgUnitInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrgUnitInfoServiceImpl implements OrgUnitInfoService {
	
	@Autowired
    OrgUnitInfoMapper orgUnitInfoMapper;
	
	@Override
	public int updateOrgUnitInfo(Organization orgInfo, List<Organization> orgList, BigDecimal sub){
		String orgCode = "";
		BigDecimal capcity = null ;
		Organization org = new Organization();
		if(null != orgList){
			for (Organization c : orgList) {
				orgCode = c.getOrgCode();
				if(null != c.getCapacity()){
					capcity = c.getCapacity().add(sub);
				}else{
					capcity = sub;
				}
				org.setOrgCode(orgCode);
				org.setCapacity(capcity);
				orgUnitInfoMapper.updateParentUnitInfo(org);
				
			}
		}
		return orgUnitInfoMapper.updateOrgUnitInfo(orgInfo);
	}
	
	@Override
	public Organization selectUnitInfo(String orgCode){
		return orgUnitInfoMapper.selectUnitInfo(orgCode);
	}
	
	@Override
	public List<Organization> getAllParent(String orgCode){
		return orgUnitInfoMapper.getAllParent(orgCode);
	}

	@Override
	public List<Organization> getPlateCapacity(){
		return orgUnitInfoMapper.getPlateCapacity();
	}

}
