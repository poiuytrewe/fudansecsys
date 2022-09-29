package com.fudanuniversity.cms.commons.enums;

import java.util.Objects;

/**
 * Created by Xinyue.Tang at 2020-05-26 15:42:24
 */
public enum BooleanEnum {
    False(0, "否"),
    True(1, "是"),
    ;

    private final Integer code;

    private final String desc;

    BooleanEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean isTrue(Integer code) {
        return Objects.equals(code, True.getCode());
    }
}
