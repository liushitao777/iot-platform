package com.cpiinfo.iot.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import com.cpiinfo.commons.tools.exception.user.SecurityUser;
//import com.cpiinfo.commons.tools.exception.user.UserDetail;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 公共字段，自动填充值
 *
 * @author liwenjie
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_AT = "createAt";
    //private final static String CREATOR = "creator";
    private final static String UPDATE_AT = "updateAt";
    //private final static String UPDATER = "updater";
    //private final static String DEL_FLAG = "delFlag";
    //private final static String DEPT_ID = "deptId";

    @Override
    public void insertFill(MetaObject metaObject) {
        //这里需要取到当前登录用户，需要修改一下
//        UserDetail user = SecurityUser.getUser();
//        if(user == null){
//            return;
//        }
        Date date = new Date();

        //创建者
        //setInsertFieldValByName(CREATOR, user.getId(), metaObject);
        //创建时间
        setInsertFieldValByName(CREATE_AT, date, metaObject);

        //创建者所属部门
        //setInsertFieldValByName(DEPT_ID, user.getDeptId(), metaObject);

        //更新者
        //setInsertFieldValByName(UPDATER, user.getId(), metaObject);
        //更新时间
        setInsertFieldValByName(UPDATE_AT, date, metaObject);

        //删除标识
        //setInsertFieldValByName(DEL_FLAG, DelFlagEnum.NORMAL.value(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新者
        //setUpdateFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
        //更新时间
        setUpdateFieldValByName(UPDATE_AT, new Date(), metaObject);
    }
}