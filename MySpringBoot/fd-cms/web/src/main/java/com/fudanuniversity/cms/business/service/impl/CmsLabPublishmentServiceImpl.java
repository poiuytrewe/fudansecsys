package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsLabPublishmentService;
import com.fudanuniversity.cms.business.service.CmsWeeklyPublicationService;
import com.fudanuniversity.cms.repository.dao.CmsLabPublishmentDao;
import com.fudanuniversity.cms.repository.entity.CmsLabPublishment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CmsLabPublishmentServiceImpl implements CmsLabPublishmentService {
    private static final Logger logger = LoggerFactory.getLogger(CmsLabPublishmentService.class);

    @Resource
    CmsLabPublishmentDao cmsLabPublishmentDao;

    @Override
    public List<CmsLabPublishment> queryLabData(){
        List<CmsLabPublishment> labData = cmsLabPublishmentDao.queryLabData();
        return labData;
    }

    @Override
    public void deleteLabData(Long id){
        cmsLabPublishmentDao.deleteLabData(id);
    }
}