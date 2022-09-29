package com.fudanuniversity.cms.business.vo.study.plan;

import jakarta.validation.constraints.Min;
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
public class CmsStudyPlanUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    @Min(1L)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 基准日期
     */
    private Date referenceDate;
}

