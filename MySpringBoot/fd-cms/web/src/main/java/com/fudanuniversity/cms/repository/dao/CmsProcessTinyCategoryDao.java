package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsProcessTinyCategory;
import com.fudanuniversity.cms.repository.query.CmsProcessTinyCategoryQuery;

import java.util.List;

public interface CmsProcessTinyCategoryDao {
    List<CmsProcessTinyCategory> getTinyCategory(CmsProcessTinyCategoryQuery query);

    void addTinyCategory(CmsProcessTinyCategory category);

    void resetProcessCategory(CmsProcessTinyCategory tinyCategory);
}
