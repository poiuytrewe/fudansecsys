package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsProcessTinyCategoryService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsProcessTinyCategoryVo;
import com.fudanuniversity.cms.repository.dao.CmsProcessTinyCategoryDao;
import com.fudanuniversity.cms.repository.entity.CmsProcessTinyCategory;
import com.fudanuniversity.cms.repository.query.CmsProcessTinyCategoryQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CmsProcessTinyCategoryServiceImpl implements CmsProcessTinyCategoryService {
    @Resource
    CmsProcessTinyCategoryDao cmsProcessTinyCategoryDao;

    @Override
    public List<CmsProcessTinyCategory> getTinyCategory(Integer type, Integer studyType){
        CmsProcessTinyCategoryQuery query=CmsProcessTinyCategoryQuery.listQuery();
        query.setType(type);
        query.setStudyType(studyType);
        List<CmsProcessTinyCategory> categoryList=cmsProcessTinyCategoryDao.getTinyCategory(query);
        return categoryList;
    }

    @Override
    public void addTinyCategory(CmsProcessTinyCategoryVo categoryVo){
        CmsProcessTinyCategory category=new CmsProcessTinyCategory();
        category.setType(categoryVo.getType());
        category.setStudyType(categoryVo.getStudyType());
        category.setProcessCategory(categoryVo.getProcessCategory());
        cmsProcessTinyCategoryDao.addTinyCategory(category);
    }

}
