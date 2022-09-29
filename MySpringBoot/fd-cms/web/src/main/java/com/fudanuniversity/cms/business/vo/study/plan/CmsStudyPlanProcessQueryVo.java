package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanProcessQueryVo {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String stuName;

    private String stuId;

    private String missionName;

    private String missionDetail;

    private Integer missionStatus;

    private Integer count;

    private Integer year;

    private Integer month;

    private Date createTime;
}
