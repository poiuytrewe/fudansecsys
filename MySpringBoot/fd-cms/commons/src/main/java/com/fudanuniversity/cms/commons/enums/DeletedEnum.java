package com.fudanuniversity.cms.commons.enums;

/**
 * Created by Xinyue.Tang at 2020-05-26 15:42:24
 */
public enum DeletedEnum {
    Normal(0, "有效"),
    Deleted(1, "删除"),
    ;

    private final Integer code;

    private final String desc;

    DeletedEnum(Integer code, String desc) {
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
