package com.fudanuniversity.cms.business.vo.study.plan;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanGroupUpdateVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private CmsStudyPlanProcess list;//从哪个任务开始，所有的都向后延期

    @NotNull
    private Integer delayTime;//整体延后的月份

    @NotNull
    private String stuId;
}
