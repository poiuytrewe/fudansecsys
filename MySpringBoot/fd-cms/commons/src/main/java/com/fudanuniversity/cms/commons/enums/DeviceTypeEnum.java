package com.fudanuniversity.cms.commons.enums;

/**
 * 设备类型
 * <p>
 * Created by Xinyue.Tang at 2021-05-02 16:46:38
 */
public enum DeviceTypeEnum {

    PC(0, "台式设备"),
    Mobile(1, "移动设备"),
    Server(2, "服务器"),
    Other(3, "其他"),
    ;

    private final Integer code;

    private final String desc;

    DeviceTypeEnum(Integer code, String desc) {
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
