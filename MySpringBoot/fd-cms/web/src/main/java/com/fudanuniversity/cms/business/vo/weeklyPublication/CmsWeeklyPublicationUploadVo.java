package com.fudanuniversity.cms.business.vo.weeklyPublication;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsWeeklyPublicationUploadVo {
    private static final long serialVersionUID = 1L;

    private String stuName;

    private String stuId;

    private String target;

    private String outlinedAchievements;

    private String achievements;

    private String plan;

    private String week;

    private Integer isRead;

    private Date date;
}
