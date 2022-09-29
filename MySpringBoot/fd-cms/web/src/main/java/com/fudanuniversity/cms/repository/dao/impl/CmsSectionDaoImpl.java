package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsSectionDao;
import com.fudanuniversity.cms.repository.entity.CmsSection;
import com.fudanuniversity.cms.repository.mapper.CmsSectionMapper;
import com.fudanuniversity.cms.repository.query.CmsSectionQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository //将Dao层的类标识为一个Bean
public class CmsSectionDaoImpl implements CmsSectionDao {

    @Resource
    CmsSectionMapper cmsSectionMapper;

    @Override
    public Long selectCountByParam(CmsSectionQuery query){
        return cmsSectionMapper.selectCountByParam(query);
    }

    @Override
    public List<CmsSection> selectListByParam(CmsSectionQuery query){
        return cmsSectionMapper.selectListByParam(query);
    }

    @Override
    public int updateById(CmsSection cmsSection){
        AssertUtils.notNull(cmsSection.getId(),"跟新的id不能为空");
        return cmsSectionMapper.updateById(cmsSection);
    }

    @Override
    public CmsSection queryDownloadSectionFile(Long id){
        AssertUtils.notNull(id,"查找的id不能为空");
        return cmsSectionMapper.queryDownloadSectionFile(id);
    }

    @Override
    public void saveSection(CmsSection cmsSection){
        AssertUtils.notNull(cmsSection,"保存对象不能为空");
        cmsSectionMapper.saveSection(cmsSection);
    }

    @Override
    public void deleteById(Long id){
        AssertUtils.notNull(id,"id不能为空");
        cmsSectionMapper.deleteById(id);
    }

    @Override
    public CmsSection selectByDate(Date date){
        AssertUtils.notNull(date,"演讲日期不能为空");
        return cmsSectionMapper.selectByDate(date);
    }
}
