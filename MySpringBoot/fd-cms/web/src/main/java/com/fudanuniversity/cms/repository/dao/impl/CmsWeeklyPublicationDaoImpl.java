package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsWeeklyPublicationDao;
import com.fudanuniversity.cms.repository.entity.CmsWeeklyPublication;
import com.fudanuniversity.cms.repository.mapper.CmsWeeklyPublicationMapper;
import com.fudanuniversity.cms.repository.query.CmsWeeklyPublicationQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CmsWeeklyPublicationDaoImpl implements CmsWeeklyPublicationDao {

    @Resource
    CmsWeeklyPublicationMapper cmsWeeklyPublicationMapper;

    @Override
    public Long selectCountByParam(String stuId){
        Assert.notNull(stuId,"输入参数不能为空");
        return cmsWeeklyPublicationMapper.selectCountByParam(stuId);
    }

    @Override
    public Long selectWeekCountByParam(CmsWeeklyPublicationQuery query){
        Assert.notNull(query.getStuId(),"学号不能为空");
        Assert.notNull(query.getWeek(),"周数不能为空");
        return cmsWeeklyPublicationMapper.selectWeekCountByParam(query);
    }

    @Override
    public void deletePublication(Long id){
        cmsWeeklyPublicationMapper.deletePublication(id);
    }

    @Override
    public List<CmsWeeklyPublication> selectListByParam(CmsWeeklyPublicationQuery query){
        Assert.notNull(query,"输入参数不能为空");
        return cmsWeeklyPublicationMapper.selectListByParam(query);
    }

    @Override
    public CmsWeeklyPublication selectLastByParam(CmsWeeklyPublicationQuery query){
        Assert.notNull(query,"输入参数不能为空");
        return cmsWeeklyPublicationMapper.selectLastByParam(query);
    }

    @Override
    public void insertPublication(CmsWeeklyPublication weeklyPublication){
        Assert.notNull(weeklyPublication.getStuId(),"学生姓名不能为空");
        cmsWeeklyPublicationMapper.insertPublication(weeklyPublication);
    }

    @Override
    public CmsWeeklyPublication selectFile(Long id){
        Assert.notNull(id,"id不能为空");
        return cmsWeeklyPublicationMapper.selectFile(id);
    }

    @Override
    public void updateById(CmsWeeklyPublication publication){
        Assert.notNull(publication.getId(),"id不能为空");
        cmsWeeklyPublicationMapper.updateById(publication);
    }
}
