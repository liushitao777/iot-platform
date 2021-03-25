package com.cpiinfo.sysmgt.controller;

import com.cpiinfo.iot.model.ServiceResponse;
import com.cpiinfo.sysmgt.entity.Organization;
import com.cpiinfo.sysmgt.service.OrgUnitInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/OrgUnitInfo")
@Api(value="sys-mgt:单位配置UnitInfo",tags={"sys-mgt:单位配置UnitInfo"})
public class OrgUnitInfoController {

	@Autowired
    OrgUnitInfoService orgUnitInfoService;

	@RequestMapping(value=("/updateUnitInfo"), method=RequestMethod.POST)
	@ResponseBody
	public ServiceResponse updateUnitInfo(HttpServletRequest req, HttpServletResponse resp , @RequestParam Map<String, Object> paramMap) throws ParseException{
		String message = "修改成功";
		Organization org = null;
		if(null != paramMap){
			org = new Organization();
			String orgCode = (String)paramMap.get("orgCode");
			String shortName = (String)paramMap.get("shortName");
			org.setShortName(shortName);
			String orgName = (String)paramMap.get("orgName");
			org.setOrgName(orgName);
			String province = (String)paramMap.get("province");
			org.setProvince(province);
			String linkman = (String)paramMap.get("linkman");
			org.setLinkman(linkman);
			String telephone = (String)paramMap.get("telephone");
			org.setTelephone(telephone);
			String location = (String)paramMap.get("location");
			org.setLocation(location);
			String coordinateSys = (String)paramMap.get("coordinateSys");
			org.setCoordinateSys(coordinateSys);
			String longitude = (String)paramMap.get("longitude");
			org.setLongitude(("".equals(longitude) || longitude == null) ? null : BigDecimal
					.valueOf(Double.valueOf(longitude)));
			String assetsRatio = (String)paramMap.get("assetsRatio");
			org.setAssetsRatio(("".equals(assetsRatio) || assetsRatio ==null ) ? null : BigDecimal
					.valueOf(Double.valueOf(assetsRatio)));
			String isNormal = (String)paramMap.get("isNormal");
			org.setIsNormal(isNormal);
			String accessSituation = (String)paramMap.get("accessSituation");
			org.setAccessSituation(accessSituation);
			String capacity = (String)paramMap.get("capacity");
			BigDecimal newCap = BigDecimal.ZERO;
			if(!StringUtils.isEmpty(capacity)){
				newCap = new BigDecimal(capacity);// Float.parseFloat(capacity);
			}
			org.setCapacity(("".equals(capacity) || capacity == null) ? null :newCap);
			org.setOrgCode(orgCode);
			String mainIndustry = (String)paramMap.get("mainIndustry");
			org.setMainIndustry(mainIndustry);
			//获取parent下面除开当前机构的装机容量的和
			//查询该结构上面的所有机构
			String oldCapacity = (String)paramMap.get("oldCapacity");
			BigDecimal oldCap = BigDecimal.ZERO;
			if(!StringUtils.isEmpty(oldCapacity)){
				oldCap = new BigDecimal(oldCapacity);// Float.parseFloat(oldCapacity);
			}
			BigDecimal sub = newCap.subtract(oldCap);// newCap - oldCap;
			try {
			    List<Organization> orgList = this.orgUnitInfoService.getAllParent(orgCode);
				this.orgUnitInfoService.updateOrgUnitInfo(org,orgList,sub);
			} catch (Exception e) {
				message = "修改失败";
			}
		}else{
			message = "修改失败";
		}
		return new ServiceResponse(true, message, 0, org);
	}
	
	/**
	 * selectUnitInfo:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO查询对应的机构基本信息
	 * @author liyuexing 
	 * @param req
	 * @param resp
	 * @param paramMap
	 * @return
	 * @throws ParseException 
	 * Date:2017年8月14日上午10:37:14
	 */
	@RequestMapping(value=("/selectUnitInfo"), method=RequestMethod.GET)
	@ResponseBody
	public ServiceResponse selectUnitInfo(HttpServletRequest req, HttpServletResponse resp ,@RequestParam Map<String, Object> paramMap) throws ParseException{
		String message = "";
		if(null != paramMap){
			String orgCode = (String)paramMap.get("orgCode");//获取对应的单位code
			String leveln = (String)paramMap.get("leveln");//获取对应单位的层级
			if(StringUtils.isEmpty(orgCode)){
				message = "操作失败";
			}
			
			Organization org = this.orgUnitInfoService.selectUnitInfo(orgCode);
			//当为二级单位的时候，查询所有板块的装机容量
			if("2".equals(leveln)){
				List<Organization> orgList = this.orgUnitInfoService.getPlateCapacity();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("cap", orgList);
				map.put("org", org);
				return new ServiceResponse(true, null, 0, map); 
			}
			return new ServiceResponse(true, null, 0, org);
		}else{
			message = "操作失败";
		}
		return new ServiceResponse(true, message, 0, null);
	}


}
