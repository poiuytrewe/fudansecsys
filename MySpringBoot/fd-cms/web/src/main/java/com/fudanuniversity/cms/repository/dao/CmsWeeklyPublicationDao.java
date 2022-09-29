package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsWeeklyPublication;
import com.fudanuniversity.cms.repository.query.CmsWeeklyPublicationQuery;

import java.util.List;

public interface CmsWeeklyPublicationDao {
    Long selectCountByParam(String stuId);

    Long selectWeekCountByParam(CmsWeeklyPublicationQuery query);

    void deletePublication(Long id);

    List<CmsWeeklyPublication> selectListByParam(CmsWeeklyPublicationQuery query);

    CmsWeeklyPublication selectLastByParam(CmsWeeklyPublicationQuery query);

    void insertPublication(CmsWeeklyPublication weeklyPublication);

    CmsWeeklyPublication selectFile(Long id);

    void updateById(CmsWeeklyPublication publication);
}
