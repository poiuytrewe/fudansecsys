package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsProcessTinyCategoryVo;
import com.fudanuniversity.cms.repository.entity.CmsProcessTinyCategory;

import java.util.List;

public interface CmsProcessTinyCategoryService {
    List<CmsProcessTinyCategory> getTinyCategory(Integer type, Integer studyType);

    void addTinyCategory(CmsProcessTinyCategoryVo categoryVo);
}
