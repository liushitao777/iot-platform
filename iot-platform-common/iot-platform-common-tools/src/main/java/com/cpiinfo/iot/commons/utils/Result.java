

package com.cpiinfo.iot.commons.utils;

import com.cpiinfo.iot.commons.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应数据
 * @author liwenjie
 * @date 2020-05-06
 */
@ApiModel(value = "响应")
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编码：0表示成功，其他值表示失败
     */
    @ApiModelProperty(value = "编码：0表示成功，其他值表示失败")
    private int code = 0;
    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String msg = "success";
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Result<T> ok(T data) {
        if(data!=null&&"java.lang.String".equals(data.getClass().getTypeName())){
            this.msg=data.toString();
        }else {
            this.setData(data);
        }
        return this;
    }

    public Result<T> ok(String msg,T data) {
        this.setData(data);
        this.msg = msg;
        return this;
    }

    public boolean success(){
        return code == 0 ? true : false;
    }

    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result<T> error(String msg) {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
        return this;
    }

}