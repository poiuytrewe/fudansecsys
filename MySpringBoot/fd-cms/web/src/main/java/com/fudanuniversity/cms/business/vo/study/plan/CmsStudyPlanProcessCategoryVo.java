package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanProcessCategoryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private Integer studyType;

    private String processCategory;

    private String categoryMission;

    private Integer year;

    private Integer month;

    private Date createTime;
}
