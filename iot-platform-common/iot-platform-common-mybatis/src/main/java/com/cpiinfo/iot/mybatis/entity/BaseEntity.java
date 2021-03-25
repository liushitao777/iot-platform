package com.cpiinfo.iot.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类，所有实体都需要继承
 *
 * @author liwenjie
 * @date 2020-05-11
 */
@Data
public abstract class BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 创建者
     */
    //@TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建者姓名
     */
    private String createByName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createAt;

//    /**
//     * 删除标识  0：未删除    1：删除
//     */
//    @TableField(fill = FieldFill.INSERT)
//    private Integer delFlag;


    /**
     * 修改者账号
     */
    //@TableField(fill = FieldFill.INSERT)
    private String updateBy;
    /**
     * 修改者姓名
     */
    private String updateByName;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateAt;

}
