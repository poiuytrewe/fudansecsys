package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.repository.entity.CmsLabPublishment;

import java.util.List;

public interface CmsLabPublishmentService {

    List<CmsLabPublishment> queryLabData();

    void deleteLabData(Long id);

}
