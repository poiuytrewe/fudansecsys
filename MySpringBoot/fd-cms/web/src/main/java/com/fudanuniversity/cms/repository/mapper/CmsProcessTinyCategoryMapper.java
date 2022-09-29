package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsProcessTinyCategory;
import com.fudanuniversity.cms.repository.query.CmsProcessTinyCategoryQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmsProcessTinyCategoryMapper {
    List<CmsProcessTinyCategory> getTinyCategory(CmsProcessTinyCategoryQuery query);

    void addTinyCategory(CmsProcessTinyCategory category);

    void resetProcessCategory(CmsProcessTinyCategory tinyCategory);
}
