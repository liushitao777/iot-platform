package com.cpiinfo.iot.mybatis.enums;

/**
 * 删除标识枚举类
 *
 * @author liwenjie
 * @date 2020-05-11
 */
public enum DelFlagEnum {
    NORMAL(0),
    DEL(1);

    private int value;

    DelFlagEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
