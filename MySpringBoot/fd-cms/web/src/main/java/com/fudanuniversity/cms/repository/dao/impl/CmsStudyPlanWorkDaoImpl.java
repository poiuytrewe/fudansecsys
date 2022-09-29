package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsStudyPlanWorkDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanWork;
import com.fudanuniversity.cms.repository.mapper.CmsStudyPlanWorkMapper;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanWorkQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanWorkDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 19:58:05
 */
@Repository
public class CmsStudyPlanWorkDaoImpl implements CmsStudyPlanWorkDao {

    @Resource
    private CmsStudyPlanWorkMapper cmsStudyPlanWorkMapper;

    @Override
    public int insert(CmsStudyPlanWork cmsStudyPlanWork) {
        Assert.notNull(cmsStudyPlanWork, "保存对象不能为空");

        validateEntity(cmsStudyPlanWork);

        return cmsStudyPlanWorkMapper.insert(cmsStudyPlanWork);
    }

    @Override
    public int bulkUpsert(List<CmsStudyPlanWork> cmsStudyPlanWorkList) {
        Assert.notEmpty(cmsStudyPlanWorkList, "保存对象列表不能为空");

        for (CmsStudyPlanWork cmsStudyPlanWork : cmsStudyPlanWorkList) {
            validateEntity(cmsStudyPlanWork);
        }

        return cmsStudyPlanWorkMapper.bulkUpsert(cmsStudyPlanWorkList);
    }

    private void validateEntity(CmsStudyPlanWork cmsStudyPlanWork) {
        Assert.notNull(cmsStudyPlanWork.getPlanId(), "培养方案id不能为空");
        Assert.notNull(cmsStudyPlanWork.getPlanStageId(), "培养方案阶段id不能为空");
        Assert.notNull(cmsStudyPlanWork.getWorkType(), "任务类型不能为空");
        Assert.notNull(cmsStudyPlanWork.getIndex(), "任务序号不能为空");
        Assert.notNull(cmsStudyPlanWork.getName(), "名称不能为空");
        Assert.notNull(cmsStudyPlanWork.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsStudyPlanWork cmsStudyPlanWork) {
        Assert.notNull(cmsStudyPlanWork, "更新对象不能为空");
        Assert.notNull(cmsStudyPlanWork.getId(), "更新对象id不能为空");
        Assert.notNull(cmsStudyPlanWork.getModifyTime(), "更新时间不能为空");

        return cmsStudyPlanWorkMapper.updateById(cmsStudyPlanWork);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsStudyPlanWorkMapper.deleteById(id);
    }

    @Override
    public int deleteByPlanId(Long planId) {
        Assert.notNull(planId, "planId不能为空");

        return cmsStudyPlanWorkMapper.deleteByPlanId(planId);
    }

    @Override
    public List<CmsStudyPlanWork> selectListByParam(CmsStudyPlanWorkQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanWorkMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsStudyPlanWorkQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanWorkMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsStudyPlanWorkQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getPlanStageId() == null
               && query.getPlanId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}