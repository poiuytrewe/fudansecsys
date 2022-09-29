package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcessCategory;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanProcessCategoryQuery;

import java.util.List;

public interface CmsStudyPlanProcessCategoryDao {
    List<CmsStudyPlanProcessCategory> getProcessCategory(CmsStudyPlanProcessCategoryQuery query);

    void addProcessCategory(CmsStudyPlanProcessCategory category);

    void deleteById(Long id);

    void updateById(CmsStudyPlanProcessCategory category);

    void resetProcessCategory(CmsStudyPlanProcessCategory category);
}
