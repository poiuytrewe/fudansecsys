package com.fudanuniversity.cms.commons.enums;

import java.util.Objects;

/**
 * Created by Xinyue.Tang at 2021-05-06 02:18:34
 */
public enum StudyPlanAllocationStatusEnum {

    Undefined(0, "未分配"),
    Normal(10, "已分配"),
    Expired(20, "已过期"),
    ;

    private final Integer code;

    private final String desc;

    StudyPlanAllocationStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static StudyPlanAllocationStatusEnum eval(Long planVersion, Long allocationVersion) {
        if (Objects.equals(planVersion, allocationVersion)) {
            return Normal;
        } else {
            return Expired;
        }
    }
}
