package com.fudanuniversity.cms.business.vo.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 用户
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@ToString
public class CmsUserQueryVo { //groupId已改

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private String stuId;

    private Integer roleId;

    private Integer isSystemMng;
    private Integer isTalkMng;
    private Integer isLabMng;
    private Integer isPaperMng;
    private Integer isEducateMng;
    private Integer isDeviceMng;

    private String name;

    private Integer  groupId;

    private String telephone;

    private String email;

    private String mentor;

    private String leader;

    private Integer studyType;

    private Integer keshuo;

    private Integer enrollYear;

    private Date enrollDate;

    private String papers;

    private String patents;

    private String services;

    private String projects;

    private Integer status;

    private Integer deleted;
}