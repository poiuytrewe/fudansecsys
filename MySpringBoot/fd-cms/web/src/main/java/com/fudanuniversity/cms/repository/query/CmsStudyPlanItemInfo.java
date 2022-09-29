package com.fudanuniversity.cms.repository.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Xinyue.Tang at 2021-05-06 13:34:10
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanItemInfo implements Serializable {

    private Long planId;

    private Long userId;

    private Long total;

    private Long unfinished;

    private Long regularUnfinished;

    private Long delayUnfinished;

    private Long overtimeUnfinished;

    private Long finished;

    private Long regularFinished;

    private Long delayFinished;

    private Long overtimeFinished;
}
