package com.fudanuniversity.cms.business.vo.study.plan;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 培养方案
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 入学年份
     */
    @NotNull
    private Integer enrollYear;

    /**
     * 基准日期
     */
    private Date referenceDate;

    /**
     * 名称
     */
    @NotEmpty
    private String name;

    /**
     * 非0表示使用一个模板创建培养方案，目前前端只传 0 或 1
     */
    private Long templateId;
}

