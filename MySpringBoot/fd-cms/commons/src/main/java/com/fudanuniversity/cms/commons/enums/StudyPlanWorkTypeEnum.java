package com.fudanuniversity.cms.commons.enums;

import java.util.Objects;

/**
 * Created by Xinyue.Tang at 2021-05-05 18:01:11
 */
public enum StudyPlanWorkTypeEnum {

    Common(100, "公共"),

    Keshuo(200, "科硕"),

    Academic(310, "学术型"),

    Synthesizing(320, "结合型"),

    Technology(330, "技术型"),
    ;

    private final Integer code;

    private final String desc;

    StudyPlanWorkTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static StudyPlanWorkTypeEnum studyTypeOf(Integer studyType) {
        if (Objects.equals(StudyTypeEnum.Academic.getCode(), studyType)) {
            return Academic;
        } else if (Objects.equals(StudyTypeEnum.Synthesizing.getCode(), studyType)) {
            return Synthesizing;
        } else if (Objects.equals(StudyTypeEnum.Technology.getCode(), studyType)) {
            return Technology;
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
