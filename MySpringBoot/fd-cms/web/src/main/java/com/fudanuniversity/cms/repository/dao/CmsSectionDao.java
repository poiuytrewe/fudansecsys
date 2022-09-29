package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsSection;
import com.fudanuniversity.cms.repository.query.CmsSectionQuery;

import java.util.Date;
import java.util.List;

public interface CmsSectionDao {
    Long selectCountByParam(CmsSectionQuery query);

    List<CmsSection> selectListByParam(CmsSectionQuery query);

    int updateById(CmsSection cmsSection);

    CmsSection queryDownloadSectionFile(Long id);

    void saveSection(CmsSection cmsSection);

    void deleteById(Long id);

    CmsSection selectByDate(Date date);
}
