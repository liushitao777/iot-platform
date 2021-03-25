package com.cpiinfo.iot.commons.enums;

/**
 * @ClassName AlgorithmModelEnum
 * @Deacription TODO 所有算法模型key值，也是某一个监控对象的异常类别
 * @Author liwenjie
 * @Date 2020/7/20 8:56
 * @Version 1.0
 **/
public enum AlgorithmModelEnum {
    /**
     * 光伏组件(热斑)
     */
    COMPONENT_REBAN("security.equipment.pv.exception.rb"),
    /**
     * 光伏组件(隐裂)
     */
    COMPONENT_YINLIE("security.equipment.pv.exception.yl"),
    /**
     * 光伏支架(转向异常)
     */
    BRACKET_ZXYC("security.equipment.stents.exception.yc"),
    /**v
     * 电缆测温-温度异常
     */
    DLCW_TEMPERATURE("security.equipment.cable.exception.temperature"),
    /**v
     * 火情监控-烟火异常
     */
    HQ_YHYC("security.equipment.hq.exception.yhyc"),
    /**v
     * 火情监控-环境异常
     */
    HQ_HJYC("security.equipment.hq.exception.hjyc"),
    /**v
     * 门禁-异常门禁
     */
    MJ_YCMJ("security.equipment.mj.exception.ycmj"),


    /***************围栏 begin*********************/
    /**
     * 电子围栏-异常闯入
     */
    DZWL_YCCR("security.equipment.dzwl.exception.yccr"),
    /**
     * 虚拟围栏-异常闯入
     */
    XNWL_YCCR("security.equipment.xnwl.exception.yccr"),
    /***************围栏 end*********************/


    /***************现场安全 begin*********************/
    /**
     * 场区围栏-围栏倾倒
     */
    CQWL_WLQD("security.equipment.cqwl.exception.wlqd"),
    /**v
     * 场区围栏-围栏破损
     */
    CQWL_WLPS("security.equipment.cqwl.exception.wlps"),
    /**v
     * 指示牌-指示牌脱落
     */
    ZSP_TL("security.equipment.zsp.exception.tl"),
    /**
     * 大门-大门异常
     */
    DM_DMYC("security.equipment.dm.exception.dmyc"),
    /***************现场安全 end*********************/

    /***************基础设施 begin*********************/
    /**
     * 道路-道路积水
     */
    DL_DLJS("security.equipment.dl.exception.dljs"),
    /**
     * 道路-道路塌方
     */
    DL_DLTF("security.equipment.dl.exception.dltf"),
    /**
     * 道路-道路损坏
     */
    DL_DLSH("security.equipment.dl.exception.dlsh"),
    /**
     * 排水沟-异常
     */
    PSG_PSGYC("security.equipment.psg.exception.psgyc"),
    /**
     * 光伏场地面-水涝
     */
    GFCDM_SL("security.equipment.gfcdm.exception.sl"),
    /***************基础设施 end*********************/

    /***************人员安全 begin*********************/
    /**
     * 未带安全帽
     */
    RYAN_WDAQM("security.equipment.ryaq.exception.wdaqm"),
    /**
     * 抽烟
     */
    RYAN_CY("security.equipment.ryaq.exception.cy"),
    /**
     * 工作服规范
     */
    RYAN_GZFGF("security.equipment.ryaq.exception.gzfgf"),
    /**
     * 行为规范
     */
    RYAN_XWGF("security.equipment.ryaq.exception.xwgf")
    /***************人员安全 end*********************/

    ;

    private String value;

    AlgorithmModelEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
