package com.fudanuniversity.cms.commons.model.web;

import com.fudanuniversity.cms.commons.validation.constraints.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Xinyue.Tang at 2021-05-01 22:41:49
 */
@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 学号
     */
    private String stuId;

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

    /**
     * 登录时间
     */
    private Integer status;

    private Date loginTime;
}
