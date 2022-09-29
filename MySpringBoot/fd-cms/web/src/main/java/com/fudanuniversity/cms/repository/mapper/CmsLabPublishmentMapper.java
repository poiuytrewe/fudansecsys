package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsLabPublishment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmsLabPublishmentMapper {

    List<CmsLabPublishment> queryLabData();

    void deleteLabData(Long id);
}
