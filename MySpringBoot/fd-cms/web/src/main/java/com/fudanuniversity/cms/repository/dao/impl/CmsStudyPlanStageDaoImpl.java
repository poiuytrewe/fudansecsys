package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsStudyPlanStageDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.mapper.CmsStudyPlanStageMapper;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanStageQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanStageDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 19:58:05
 */
@Repository
public class CmsStudyPlanStageDaoImpl implements CmsStudyPlanStageDao {

    @Resource
    private CmsStudyPlanStageMapper cmsStudyPlanStageMapper;

    @Override
    public int insert(CmsStudyPlanStage cmsStudyPlanStage) {
        Assert.notNull(cmsStudyPlanStage, "保存对象不能为空");

        validateEntity(cmsStudyPlanStage);

        return cmsStudyPlanStageMapper.insert(cmsStudyPlanStage);
    }

    @Override
    public int bulkUpsert(List<CmsStudyPlanStage> cmsStudyPlanStageList) {
        Assert.notEmpty(cmsStudyPlanStageList, "保存对象列表不能为空");

        for (CmsStudyPlanStage cmsStudyPlanStage : cmsStudyPlanStageList) {
            validateEntity(cmsStudyPlanStage);
        }

        return cmsStudyPlanStageMapper.bulkUpsert(cmsStudyPlanStageList);
    }

    private void validateEntity(CmsStudyPlanStage cmsStudyPlanStage) {
        Assert.notNull(cmsStudyPlanStage.getPlanId(), "培养方案id不能为空");
        Assert.notNull(cmsStudyPlanStage.getTerm(), "学期不能为空");
        Assert.notNull(cmsStudyPlanStage.getIndex(), "阶段序号不能为空");
        Assert.notNull(cmsStudyPlanStage.getEndDate(), "节点日期不能为空");
        Assert.notNull(cmsStudyPlanStage.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsStudyPlanStage cmsStudyPlanStage) {
        Assert.notNull(cmsStudyPlanStage, "更新对象不能为空");
        Assert.notNull(cmsStudyPlanStage.getId(), "更新对象id不能为空");
        Assert.notNull(cmsStudyPlanStage.getModifyTime(), "更新时间不能为空");

        return cmsStudyPlanStageMapper.updateById(cmsStudyPlanStage);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsStudyPlanStageMapper.deleteById(id);
    }

    @Override
    public int deleteByPlanId(Long planId) {
        Assert.notNull(planId, "planId不能为空");

        return cmsStudyPlanStageMapper.deleteByPlanId(planId);
    }

    @Override
    public List<CmsStudyPlanStage> selectListByParam(CmsStudyPlanStageQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanStageMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsStudyPlanStageQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanStageMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsStudyPlanStageQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getPlanId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}