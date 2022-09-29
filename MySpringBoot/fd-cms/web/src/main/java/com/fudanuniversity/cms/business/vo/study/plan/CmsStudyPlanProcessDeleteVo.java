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
public class CmsStudyPlanProcessDeleteVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CmsStudyPlanProcess> missionList;

    @NotNull
    private Integer count;

    @NotNull
    private Long id;
}
