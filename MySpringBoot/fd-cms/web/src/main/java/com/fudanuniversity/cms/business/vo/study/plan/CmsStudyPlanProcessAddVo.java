package com.fudanuniversity.cms.business.vo.study.plan;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanProcessAddVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String stuName;

    private String stuId;

    private List<CmsStudyPlanProcessCategoryVo> advCategoryList;

    private List<CmsStudyPlanProcessCategoryVo> baseCategoryList;

    private Integer enrollYear;
}
