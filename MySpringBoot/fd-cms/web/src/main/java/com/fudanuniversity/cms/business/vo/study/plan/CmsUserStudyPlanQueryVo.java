package com.fudanuniversity.cms.business.vo.study.plan;

import jakarta.validation.constraints.NotNull;
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
public class CmsUserStudyPlanQueryVo {

    private static final long serialVersionUID = 1L;

    /**
     * planId
     */
    @NotNull
    private Long planId;

    /**
     * id
     */
    private Long userId;

    /**
     * 模糊搜索学号和姓名，学号为精确搜索，姓名模糊搜索
     */
    private String kw;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 用户名
     */
    private String name;

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
}