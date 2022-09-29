package com.fudanuniversity.cms.business.vo.weeklyPublication;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsWeeklyPublicationManagerVo {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String stuName;

    private String stuId;

    private Integer type;

    private String target;

    private String outlinedAchievements;

    private String achievements;

    private String plan;

    private Date date;
}
