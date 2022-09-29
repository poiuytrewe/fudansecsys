package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsProcessTinyCategoryDao;
import com.fudanuniversity.cms.repository.entity.CmsProcessTinyCategory;
import com.fudanuniversity.cms.repository.mapper.CmsProcessTinyCategoryMapper;
import com.fudanuniversity.cms.repository.query.CmsProcessTinyCategoryQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CmsProcessTinyCategoryDaoImpl implements CmsProcessTinyCategoryDao {
    @Resource
    CmsProcessTinyCategoryMapper cmsProcessTinyCategoryMapper;

    @Override
    public List<CmsProcessTinyCategory> getTinyCategory(CmsProcessTinyCategoryQuery query){
        return cmsProcessTinyCategoryMapper.getTinyCategory(query);
    }

    @Override
    public void addTinyCategory(CmsProcessTinyCategory category){
        cmsProcessTinyCategoryMapper.addTinyCategory(category);
    }

    @Override
    public void resetProcessCategory(CmsProcessTinyCategory tinyCategory){
        cmsProcessTinyCategoryMapper.resetProcessCategory(tinyCategory);
    }
}
