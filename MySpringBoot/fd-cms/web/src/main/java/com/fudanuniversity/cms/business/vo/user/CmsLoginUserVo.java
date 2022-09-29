package com.fudanuniversity.cms.business.vo.user;

import com.fudanuniversity.cms.commons.validation.constraints.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xinyue.Tang at 2021-05-03 19:07:15
 */
@Data
@NoArgsConstructor
@ToString
public class CmsLoginUserVo implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 用户名
     */
    private String name;

    private Integer type;

    /**
     *
     */
    private Integer roleId;

    private Integer isSystemMng;
    private Integer isTalkMng;
    private Integer isLabMng;
    private Integer isPaperMng;
    private Integer isEducateMng;
    private Integer isDeviceMng;

    private Integer groupId;

    private Integer status;


    /**
     * 登录时间
     */
    private Date loginTime;
}
