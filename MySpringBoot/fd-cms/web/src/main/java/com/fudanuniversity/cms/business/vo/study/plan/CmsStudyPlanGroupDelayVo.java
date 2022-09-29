package com.fudanuniversity.cms.business.vo.study.plan;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanGroupDelayVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CmsStudyPlanProcess> groupList;

    private Integer delayTime;
}
