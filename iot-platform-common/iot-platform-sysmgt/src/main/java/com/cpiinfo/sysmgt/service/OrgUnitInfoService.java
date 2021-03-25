package com.cpiinfo.sysmgt.service;

import java.math.BigDecimal;
import java.util.List;

import com.cpiinfo.sysmgt.entity.Organization;

public interface OrgUnitInfoService {
	public int updateOrgUnitInfo(Organization orgInfo, List<Organization> orgList, BigDecimal sub);
	public Organization selectUnitInfo(String orgCode);
	public List<Organization> getAllParent(String orgCode);
	public List<Organization> getPlateCapacity();
}
