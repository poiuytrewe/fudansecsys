package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsLabPublishment;

import java.util.List;

public interface CmsLabPublishmentDao {

    List<CmsLabPublishment> queryLabData();

    void deleteLabData(Long id);
}
