package com.cpiinfo.sysmgt.dto;


/**
 * @author wuchangjiang
 * @version V2.0
 * @Title:
 * @Package com.cpiinfo.sysmgt.dto
 * @Description:
 * @date 2020年05月13日 9:58
 */
public class QueryUserByCodeDTO {
    private String userCode;  //用户编号

    private String username;  //用户姓名

    private String phone;  //手机

    private String roleName;  //员工类型

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
