package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsStudyPlanProcessCategoryService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanProcessCategoryVo;
import com.fudanuniversity.cms.repository.dao.CmsProcessTinyCategoryDao;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanProcessCategoryDao;
import com.fudanuniversity.cms.repository.entity.CmsProcessTinyCategory;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcessCategory;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanProcessCategoryQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CmsStudyPlanProcessCategoryServiceImpl implements CmsStudyPlanProcessCategoryService {
    @Resource
    CmsStudyPlanProcessCategoryDao cmsStudyPlanProcessCategoryDao;

    @Resource
    CmsProcessTinyCategoryDao cmsProcessTinyCategoryDao;

    @Override
    public List<CmsStudyPlanProcessCategory> getProcessCategory(Integer type, Integer studyType){
        CmsStudyPlanProcessCategoryQuery query=CmsStudyPlanProcessCategoryQuery.list();
        query.setType(type);
        query.setStudyType(studyType);
        List<CmsStudyPlanProcessCategory> categoryList=cmsStudyPlanProcessCategoryDao.getProcessCategory(query);
        return categoryList;
    }

    @Override
    public void addProcessCategory(CmsStudyPlanProcessCategoryVo categoryVo){
        CmsStudyPlanProcessCategory category=new CmsStudyPlanProcessCategory();
        category.setType(categoryVo.getType());
        category.setStudyType(categoryVo.getStudyType());
        category.setProcessCategory(categoryVo.getProcessCategory());
        category.setCategoryMission(categoryVo.getCategoryMission());
        category.setYear(categoryVo.getYear());
        category.setMonth(categoryVo.getMonth());
        category.setCreateTime(new Date());
        cmsStudyPlanProcessCategoryDao.addProcessCategory(category);
    }

    @Override
    public void deleteById(Long id){
        cmsStudyPlanProcessCategoryDao.deleteById(id);
    }

    @Override
    public void updateProcessCategory(CmsStudyPlanProcessCategoryVo categoryVo){
        CmsStudyPlanProcessCategory category=new CmsStudyPlanProcessCategory();
        category.setId(categoryVo.getId());
        category.setType(categoryVo.getType());
        category.setStudyType(categoryVo.getStudyType());
        category.setProcessCategory(categoryVo.getProcessCategory());
        category.setCategoryMission(categoryVo.getCategoryMission());
        category.setYear(categoryVo.getYear());
        category.setMonth(categoryVo.getMonth());
        cmsStudyPlanProcessCategoryDao.updateById(category);
    }

    @Override
    public void resetProcessCategory(Integer type, Integer studyType){
        CmsStudyPlanProcessCategory category=new CmsStudyPlanProcessCategory();
        CmsProcessTinyCategory tinyCategory=new CmsProcessTinyCategory();
        category.setType(type);
        category.setStudyType(studyType);
        tinyCategory.setType(type);
        tinyCategory.setStudyType(studyType);
        cmsStudyPlanProcessCategoryDao.resetProcessCategory(category);
        cmsProcessTinyCategoryDao.resetProcessCategory(tinyCategory);
    }
}
