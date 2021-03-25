package com.cpiinfo.iot.commons.enums.personnel;


/**
 * @ClassName ExpPushModuleEnum
 * @Deacription TODO 人员安全下外委人员/访客人员任务类别枚举
 * @Author liwenjie
 * @Date 2020/7/23
 * @Version 1.0
 **/
public enum PersonnelTaskTypeEnum {
    TASK_TYPE_GC(1, "割草任务"),
    TASK_TYPE_QJ(2, "清洁任务"),
    TASK_TYPE_FW(3, "访问任务"),
    ;

    private Integer taskTypeId;
    private String taskTypeName;

    PersonnelTaskTypeEnum(Integer taskTypeId, String taskTypeName) {
        this.taskTypeId = taskTypeId;
        this.taskTypeName = taskTypeName;
    }

    public static String getTaskTypeName(Integer taskTypeId) {
        PersonnelTaskTypeEnum[] carTypeEnums = values();
        for (PersonnelTaskTypeEnum carTypeEnum : carTypeEnums) {
            if (carTypeEnum.taskTypeId().equals(taskTypeId)) {
                return carTypeEnum.taskTypeName();
            }
        }
        return null;
    }

    public static Integer getTaskTypeId(String taskTypeName) {
        PersonnelTaskTypeEnum[] carTypeEnums = values();
        for (PersonnelTaskTypeEnum carTypeEnum : carTypeEnums) {
            if (carTypeEnum.taskTypeName().equals(taskTypeName)) {
                return carTypeEnum.taskTypeId;
            }
        }
        return null;
    }

    public Integer taskTypeId() {
        return this.taskTypeId;
    }

    public String taskTypeName() {
        return this.taskTypeName;
    }

    public static void main(String[] args) {
        System.out.println(PersonnelTaskTypeEnum.getTaskTypeId("割草任务"));
        System.out.println(PersonnelTaskTypeEnum.getTaskTypeName(1));



    }
}
