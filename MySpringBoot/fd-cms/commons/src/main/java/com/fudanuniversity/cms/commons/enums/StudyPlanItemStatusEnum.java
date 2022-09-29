package com.fudanuniversity.cms.commons.enums;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * Created by Xinyue.Tang at 2021-05-06 02:18:34
 */
public enum StudyPlanItemStatusEnum {

    regularUnfinished(10, "进行中"),
    DelayUnfinished(20, "延期未完成"),
    OvertimeUnfinished(30, "超期未完成"),

    regularFinished(40, "已完成"),
    DelayFinished(50, "延期完成"),
    OvertimeFinished(60, "超期完成"),
    ;

    private final Integer code;

    private final String desc;

    StudyPlanItemStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 判断培养任务状态
     *
     * @param planWorkEndDate 培养方案任务结束日期
     * @param planWorkDelay   培养方案任务延期天数
     * @param finished        是否完成
     * @param finishedDate    任务完成日期
     */
    public static StudyPlanItemStatusEnum eval(
            Date planWorkEndDate, Integer planWorkDelay, Integer finished, Date finishedDate) {
        Date current = new Date();
        Date delayDate = DateUtils.addDays(planWorkEndDate, planWorkDelay);
        if (BooleanEnum.isTrue(finished)) {
            if (finishedDate != null) {//一般不可能为空
                if (finishedDate.compareTo(planWorkEndDate) <= 0) {
                    return StudyPlanItemStatusEnum.regularFinished;
                } else if (finishedDate.compareTo(delayDate) <= 0) {
                    return DelayFinished;
                } else {
                    return OvertimeFinished;
                }
            } else {//一般不可能为空
                return StudyPlanItemStatusEnum.regularFinished;
            }
        } else {
            if (current.compareTo(planWorkEndDate) <= 0) {
                return regularUnfinished;
            } else if (current.compareTo(delayDate) <= 0) {
                return DelayUnfinished;
            } else {
                return OvertimeUnfinished;
            }
        }
    }
}
