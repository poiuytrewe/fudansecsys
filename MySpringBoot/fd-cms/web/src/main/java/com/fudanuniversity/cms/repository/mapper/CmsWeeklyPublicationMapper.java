package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.dao.CmsWeeklyPublicationDao;
import com.fudanuniversity.cms.repository.entity.CmsWeeklyPublication;
import com.fudanuniversity.cms.repository.query.CmsWeeklyPublicationQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmsWeeklyPublicationMapper {

    Long selectCountByParam(String stuId);

    Long selectWeekCountByParam(CmsWeeklyPublicationQuery query);

    void deletePublication(Long id);

    List<CmsWeeklyPublication> selectListByParam(CmsWeeklyPublicationQuery query);

    CmsWeeklyPublication selectLastByParam(CmsWeeklyPublicationQuery query);

    void insertPublication(CmsWeeklyPublication weeklyPublication);

    CmsWeeklyPublication selectFile(Long id);

    void updateById(CmsWeeklyPublication publication);
}
