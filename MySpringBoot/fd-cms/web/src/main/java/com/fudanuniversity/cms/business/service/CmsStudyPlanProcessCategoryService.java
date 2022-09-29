package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanProcessCategoryVo;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcessCategory;

import java.util.List;

public interface CmsStudyPlanProcessCategoryService {
    List<CmsStudyPlanProcessCategory> getProcessCategory(Integer type,Integer studyType);

    void addProcessCategory(CmsStudyPlanProcessCategoryVo categoryVo);

    void deleteById(Long id);

    void updateProcessCategory(CmsStudyPlanProcessCategoryVo categoryVo);

    void resetProcessCategory(Integer type, Integer studyType);
}
