package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcessCategory;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanProcessCategoryQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmsStudyPlanProcessCategoryMapper {
    List<CmsStudyPlanProcessCategory> getProcessCategory(CmsStudyPlanProcessCategoryQuery query);

    void addProcessCategory(CmsStudyPlanProcessCategory category);

    void deleteById(Long id);

    void updateById(CmsStudyPlanProcessCategory category);

    void resetProcessCategory(CmsStudyPlanProcessCategory category);
}
