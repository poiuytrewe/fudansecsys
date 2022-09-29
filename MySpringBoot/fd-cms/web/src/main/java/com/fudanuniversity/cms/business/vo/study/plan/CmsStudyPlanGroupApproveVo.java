package com.fudanuniversity.cms.business.vo.study.plan;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanGroupApproveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CmsStudyPlanProcess> list;

    private Integer status;

    private Integer isDelay;
}
