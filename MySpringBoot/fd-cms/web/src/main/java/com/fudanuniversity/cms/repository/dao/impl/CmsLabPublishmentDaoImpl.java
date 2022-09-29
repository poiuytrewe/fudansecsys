package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.business.service.CmsLabPublishmentService;
import com.fudanuniversity.cms.repository.dao.CmsLabPublishmentDao;
import com.fudanuniversity.cms.repository.entity.CmsLabPublishment;
import com.fudanuniversity.cms.repository.mapper.CmsLabPublishmentMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CmsLabPublishmentDaoImpl implements CmsLabPublishmentDao {

    @Resource
    CmsLabPublishmentMapper cmsLabPublishmentMapper;

    @Override
    public List<CmsLabPublishment> queryLabData(){
        return cmsLabPublishmentMapper.queryLabData();
    }

    @Override
    public void deleteLabData(Long id){
        cmsLabPublishmentMapper.deleteLabData(id);
    }
}
