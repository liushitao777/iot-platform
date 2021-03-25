package com.cpiinfo.iot.commons.enums;

/**
 * @ClassName AlarmObjectEnum
 * @Deacription TODO 所有报警对象的枚举值，存在redis中的key值
 * @Author liwenjie
 * @Date 2020/7/20 8:56
 * @Version 1.0
 **/
public enum AlarmObjectEnum {
    //特殊key,车辆道闸,未存入数据库,用于前端匹配名称
    CLDZ_YYSP("cldz.yysp"),
    //特殊key,现场安全,未存入数据库,用于前端匹配名称
    XCAQ_All("xcaq_all"),
    //特殊key,基础设施,未存入数据库,用于前端匹配名称
    JCSS_ALL("jcss_all"),
    //特殊key,围栏,未存入数据库,用于前端匹配名称
    WL_ALL("wl_all"),


    /**
     * 光伏组件
     */
    GFZD_COMPONENT("alarm.object.gfsb"),
    /**
     * 光伏支架
     */
    GFZD_BRACKET("alarm.object.zj"),
    /**
     * 电缆测温
     */
    ALARM_DLCW("alarm.object.dlcw"),

    /**
     * 火情监控
     */
    ALARM_HQ("alarm.object.hq"),
    /**
     * 门禁
     */
    ALARM_MJ("alarm.object.mj"),
    /**
     * 电子围栏
     */
    ALARM_DZWL("alarm.object.dzwl"),
    /**
     * 虚拟围栏
     */
    ALARM_XNWL("alarm.object.xnwl"),

    /***************现场安全 begin*********************/
    /**
     * 场区围栏
     */
    ALARM_CQWL("alarm.object.cqwl"),
    /**
     * 指示牌
     */
    ALARM_ZSP("alarm.object.zsp"),
    /**
     * 大门
     */
    ALARM_DM("alarm.object.dm"),
    /***************现场安全 end*********************/

    /***************基础设施 begin*********************/
    /**
     * 道路
     */
    ALARM_DL("alarm.object.dl"),
    /**
     * 排水沟
     */
    ALARM_PSG("alarm.object.psg"),
    /**
     * 光伏场地面
     */
    ALARM_GFCDM("alarm.object.gfcdm"),
    /***************基础设施 end*********************/

    /***************人员安全 begin*********************/
    ALARM_RYAN("alarm.object.ryaq"),
    /***************人员安全 end*********************/
        //升压站设备
    ALARM_SYZ( "alarm.object.syzsb")
    ;

    private String value;

    AlarmObjectEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static void main(String[] args) {
        System.out.println(AlarmObjectEnum.ALARM_DLCW.value);
    }
}
