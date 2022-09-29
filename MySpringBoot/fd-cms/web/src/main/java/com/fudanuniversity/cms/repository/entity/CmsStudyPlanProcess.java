package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String stuName;

    private String stuId;

    private Integer type;

    private Integer missionStatus;

    private String category;

    private String tinyCategory;

    private Integer isDelay;

    private Integer year;

    private Integer month;

    private Integer wishYear;

    private Integer wishMonth;

    private Integer alert;

    private Date createTime;
}
