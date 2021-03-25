package com.cpiinfo.sysmgt.api.listener;

import java.util.Map;

public interface SysUserChangeListener {
    /**
     * 变更类型 type 修改密码
     */
    public static final Integer CHANGE_TYPE_UPDATE_PASSWORD = 1;

    /**
     * 变更类型 type 删除
     */
    public static final Integer CHANGE_TYPE_DELETE_USER     = 2;

    /**
     * 系统用户变更通知
     * @param changeType - 变更类型，参见本类型常量定义
     * @param params - 变更相应的参数，依变更类型不同而不同
     * @return
     */
    public Object sysUserChanged(Integer changeType, Map<String, Object> params);
}
