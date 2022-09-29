package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsStudyPlanProcessCategoryDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcessCategory;
import com.fudanuniversity.cms.repository.mapper.CmsStudyPlanProcessCategoryMapper;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanProcessCategoryQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Repository
public class CmsStudyPlanProcessCategoryDaoImpl implements CmsStudyPlanProcessCategoryDao {
    @Resource
    CmsStudyPlanProcessCategoryMapper cmsStudyPlanProcessCategoryMapper;

    @Override
    public List<CmsStudyPlanProcessCategory> getProcessCategory(CmsStudyPlanProcessCategoryQuery query){
        return cmsStudyPlanProcessCategoryMapper.getProcessCategory(query);
    }

    @Override
    public void addProcessCategory(CmsStudyPlanProcessCategory category){
        cmsStudyPlanProcessCategoryMapper.addProcessCategory(category);
    }

    @Override
    public void deleteById(Long id){
        cmsStudyPlanProcessCategoryMapper.deleteById(id);
    }

    @Override
    public void updateById(CmsStudyPlanProcessCategory category){
        cmsStudyPlanProcessCategoryMapper.updateById(category);
    }

    @Override
    public void resetProcessCategory(CmsStudyPlanProcessCategory category){
        cmsStudyPlanProcessCategoryMapper.resetProcessCategory(category);
    }
}
