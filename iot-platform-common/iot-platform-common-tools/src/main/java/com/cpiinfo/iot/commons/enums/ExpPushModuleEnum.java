package com.cpiinfo.iot.commons.enums;


/**
 * @ClassName ExpPushModuleEnum
 * @Deacription TODO websocket推送前端滚动异常所在模块
 * @Author liwenjie
 * @Date 2020/7/23
 * @Version 1.0
 **/
public enum ExpPushModuleEnum {
    EXP_PUSH_MODULE_ZL("aqgk_zl", "安全管控总览"),
    EXP_PUSH_MODULE_DLCW("aqgk_dlcw", "电缆测温"),
    EXP_PUSH_MODULE_MJAQ("aqgk_mjaq", "门禁安全"),
    EXP_PUSH_MODULE_WLAQ("aqgk_wlaq", "围栏安全"),
    EXP_PUSH_MODULE_XCAQ("aqgk_xcaq", "现场安全"),
    EXP_PUSH_MODULE_JCSS("aqgk_jcss", "基础设施"),
    EXP_PUSH_MODULE_RYAQ("aqgk_ryaq", "人员安全"),
    ;

    private String moduleKey;
    private String moduleName;

    ExpPushModuleEnum(String moduleKey, String moduleName) {
        this.moduleKey = moduleKey;
        this.moduleName = moduleName;
    }

    public static String getValue(String moduleKey) {
        ExpPushModuleEnum[] carTypeEnums = values();
        for (ExpPushModuleEnum carTypeEnum : carTypeEnums) {
            if (carTypeEnum.moduleKey().equals(moduleKey)) {
                return carTypeEnum.moduleName();
            }
        }
        return null;
    }

    public static String getKey(String moduleName) {
        ExpPushModuleEnum[] carTypeEnums = values();
        for (ExpPushModuleEnum carTypeEnum : carTypeEnums) {
            if (carTypeEnum.moduleName().equals(moduleName)) {
                return carTypeEnum.moduleKey;
            }
        }
        return null;
    }

    public String moduleKey() {
        return this.moduleKey;
    }

    public String moduleName() {
        return this.moduleName;
    }

    public static void main(String[] args) {
        System.out.println(ExpPushModuleEnum.getValue("aqgk_zl"));
        System.out.println(ExpPushModuleEnum.getKey("安全管控总览"));
        String moduleName = "aaaa1";
//        switch (moduleName){
//            case "123":
//                System.out.println("123");
//                break;
//            case "111":
//                System.out.println("111");
//                break;
//            case "aaaa":
//                System.out.println("aaaa");
//                break;
//            default:
//                System.out.println("default");
//        }

        System.out.println(ExpPushModuleEnum.EXP_PUSH_MODULE_ZL.moduleKey());


    }
}
