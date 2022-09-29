package com.fudanuniversity.cms.commons.enums;

/**
 * Created by Xinyue.Tang at 2021-05-02 16:53:32
 */
public enum DeviceAllocationStatusEnum {

    InUse(10, "使用中"),
    Returned(20, "已归还"),
    ;

    private final Integer code;

    private final String desc;

    DeviceAllocationStatusEnum(Integer code, String desc) {
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
