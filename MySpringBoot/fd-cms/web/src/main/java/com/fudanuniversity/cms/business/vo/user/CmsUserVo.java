package com.fudanuniversity.cms.business.vo.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@ToString
public class CmsUserVo implements Serializable { //groupId已改

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 权限身份
     */
    private Integer roleId;

    private Integer isSystemMng;

    private Integer isTalkMng;

    private Integer isLabMng;

    private Integer isPaperMng;

    private Integer isEducateMng;

    private Integer isDeviceMng;

    /**
     * 用户名
     */
    private String name;

    private Integer groupId;

    /**
     * 手机
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 导师
     */
    private String mentor;
    /**
     * 汇报人
     */
    private String leader;

    /**
     * 就读类型
     */
    private Integer studyType;

    /**
     * 是否科硕
     */
    private Integer keshuo;

    /**
     * 入学年份
     */
    private Integer enrollYear;

    /**
     * 入学时间
     */
    private Date enrollDate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 删除状态
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}

