package com.fudanuniversity.cms.repository.dao.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanProcessDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import com.fudanuniversity.cms.repository.mapper.CmsStudyPlanProcessMapper;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanProcessQuery;
import org.checkerframework.common.util.report.qual.ReportOverride;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CmsStudyPlanProcessDaoImpl implements CmsStudyPlanProcessDao {

    @Resource
    CmsStudyPlanProcessMapper cmsStudyPlanProcessMapper;

    @Override
    public Long selectCountByParam(CmsStudyPlanProcessQuery query){
        Assert.notNull(query,"输入参数不能为空");
        return cmsStudyPlanProcessMapper.selectCountByParam(query);
    }

    @Override
    public List<CmsStudyPlanProcess> selectListByParam(CmsStudyPlanProcessQuery query){
        Assert.notNull(query,"参数不能为空");
        return cmsStudyPlanProcessMapper.selectListByParam(query);
    }

    @Override
    public List<CmsStudyPlanProcess> selectLateList(CmsStudyPlanProcessQuery query){
        return cmsStudyPlanProcessMapper.selectLateList(query);
    }

    @Override
    public void insert(CmsStudyPlanProcess cmsStudyPlanProcess){
        Assert.notNull(cmsStudyPlanProcess,"参数不能为空");
        cmsStudyPlanProcessMapper.insert(cmsStudyPlanProcess);
    }

    @Override
    public void updateById(CmsStudyPlanProcess process){
        cmsStudyPlanProcessMapper.updateById(process);
    }

    @Override
    public void deleteById(Long id){
        Assert.notNull(id,"id不能为空");
        cmsStudyPlanProcessMapper.deleteById(id);
    }

    @Override
    public void groupUpdateById(List<Long> idList){
        if(idList.size()==0){
            return;
        }
        cmsStudyPlanProcessMapper.groupUpdateById(idList);
    }

    @Override
    public Integer selectApplyCount(String stuId){
        return cmsStudyPlanProcessMapper.selectApplyCount(stuId);
    }

    @Override
    public void groupUpdateList(List<CmsStudyPlanProcess> idList){
        if(idList.size()==0){
            return;
        }
        cmsStudyPlanProcessMapper.groupUpdateList(idList);
    }

    @Override
    public Integer selectMissionStatus(String stuId){
        return cmsStudyPlanProcessMapper.selectMissionStatus(stuId);
    }

    @Override
    public List<CmsStudyPlanProcess> getAllApprove(String stuId){
        return cmsStudyPlanProcessMapper.getAllApprove(stuId);
    }

    @Override
    public List<CmsStudyPlanProcess> getAllDelay(String stuId){
        return cmsStudyPlanProcessMapper.getAllDelay(stuId);
    }

}
