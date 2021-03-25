package com.cpiinfo.iot.commons.enums;

/**
 * @ClassName MonitoringAareaEnum
 * @Deacription TODO 监控区域枚举
 * @Author liwenjie
 * @Date 2020/7/20 8:56
 * @Version 1.0
 **/
public enum MonitoringAareaEnum {

    MONITOR_AREA_1("dmrk", "大门入口"),
    MONITOR_AREA_2("wxq1", "灌木丛危险区"),
    MONITOR_AREA_3("gyq2", "高压电危险区");

    private String type;
    private String desc;

    MonitoringAareaEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getValue(String type) {
        MonitoringAareaEnum[] carTypeEnums = values();
        for (MonitoringAareaEnum carTypeEnum : carTypeEnums) {
            if (carTypeEnum.type().equals(type)) {
                return carTypeEnum.desc();
            }
        }
        return null;
    }

    public static String getType(String desc) {
        MonitoringAareaEnum[] carTypeEnums = values();
        for (MonitoringAareaEnum carTypeEnum : carTypeEnums) {
            if (carTypeEnum.desc().equals(desc)) {
                return carTypeEnum.type();
            }
        }
        return null;
    }

    private String type() {
        return this.type;
    }

    private String desc() {
        return this.desc;
    }

    public static void main(String[] args) {
        System.out.println(MonitoringAareaEnum.MONITOR_AREA_1.type());
    }

}
