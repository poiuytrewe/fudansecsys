package com.fudanuniversity.cms.commons.enums;

/**
 * Created by Xinyue.Tang at 2021-05-03 22:12:54
 */
public enum UserTypeEnum {

    Undefined(0, "未定义"),
    Student(10, "学生"),
    ;

    private final Integer code;

    private final String desc;

    UserTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
