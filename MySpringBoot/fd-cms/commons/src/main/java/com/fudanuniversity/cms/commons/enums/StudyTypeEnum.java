package com.fudanuniversity.cms.commons.enums;

/**
 * Created by Xinyue.Tang at 2021-05-02 02:29:36
 */
public enum StudyTypeEnum {

    Undefined(0, "未定义"),

    Academic(10, "学术型"),
    Synthesizing(20, "结合型"),
    Technology(30, "技术型"),
    ;

    private final Integer code;

    private final String desc;

    StudyTypeEnum(Integer code, String desc) {
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
