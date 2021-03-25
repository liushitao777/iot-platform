package com.cpiinfo.iot.commons.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ShuPF
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 用户编码
     */
    @TableField("user_code")
    private String userCode;
    /**
     * 姓名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 密码
     */
    @TableField("user_pwd")
    private String userPwd;
    /**
     * 性别 1:男 0女
     */
    @TableField("sex")
    private String sex;
    /**
     * 电话
     */
    @TableField("phone")
    private String phone;
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 系统角色类型
     */
    @TableField("user_type")
    private String userType;
    /**
     * 岗位
     */
    @TableField("duties")
    private String duties;
    /**
     * 状态
     */
    @TableField("s_state")
    private String sState;
    /**
     * 组织名称
     */
    @TableField("unit")
    private String unit;
    /**
     * 组织id
     */
    @TableField("unit_id")
    private String unitId;
    /**
     * 组织编码
     */
    @TableField("unit_code")
    private String unitCode;
    /**
     * 备注
     */
    @TableField("postscript")
    private String postscript;
    /**
     * 部门id
     */
    @TableField("depart_id")
    private String departId;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 头像地址
     */
    @TableField("head_path")
    private String headPath;


    public static final String ID = "id";

    public static final String USER_CODE = "user_code";

    public static final String USER_NAME = "user_name";

    public static final String USER_PWD = "user_pwd";

    public static final String SEX = "sex";

    public static final String PHONE = "phone";

    public static final String EMAIL = "email";

    public static final String USER_TYPE = "user_type";

    public static final String DUTIES = "duties";

    public static final String S_STATE = "s_state";

    public static final String UNIT = "unit";

    public static final String UNIT_ID = "unit_id";

    public static final String UNIT_CODE = "unit_code";

    public static final String POSTSCRIPT = "postscript";

    public static final String DEPART_ID = "depart_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String HEAD_PATH = "head_path";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
