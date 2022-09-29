package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsSection;
import com.fudanuniversity.cms.repository.query.CmsSectionQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CmsSectionMapper {

    Long selectCountByParam(CmsSectionQuery query);

    List<CmsSection> selectListByParam(CmsSectionQuery query);

    int updateById(CmsSection cmsSection);

    CmsSection queryDownloadSectionFile(Long id);

    void saveSection(CmsSection cmsSection);

    void deleteById(Long id);

    CmsSection selectByDate(Date date);
}
