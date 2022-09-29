package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsStudyPlanAllocationDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanAllocation;
import com.fudanuniversity.cms.repository.mapper.CmsStudyPlanAllocationMapper;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanAllocationQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * CmsStudyPlanAllocationDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
@Repository
public class CmsStudyPlanAllocationDaoImpl implements CmsStudyPlanAllocationDao {

    @Resource
    private CmsStudyPlanAllocationMapper cmsStudyPlanAllocationMapper;

    @Override
    public int insert(CmsStudyPlanAllocation cmsStudyPlanAllocation) {
        Assert.notNull(cmsStudyPlanAllocation, "保存对象不能为空");

        validateEntity(cmsStudyPlanAllocation);

        return cmsStudyPlanAllocationMapper.insert(cmsStudyPlanAllocation);
    }

    @Override
    public int bulkUpsert(List<CmsStudyPlanAllocation> cmsStudyPlanAllocationList) {
        Assert.notEmpty(cmsStudyPlanAllocationList, "保存对象列表不能为空");

        for (CmsStudyPlanAllocation cmsStudyPlanAllocation : cmsStudyPlanAllocationList) {
            validateEntity(cmsStudyPlanAllocation);
        }

        return cmsStudyPlanAllocationMapper.bulkUpsert(cmsStudyPlanAllocationList);
    }

    private void validateEntity(CmsStudyPlanAllocation cmsStudyPlanAllocation) {
        Assert.notNull(cmsStudyPlanAllocation.getUserId(), "用户id不能为空");
        Assert.notNull(cmsStudyPlanAllocation.getPlanId(), "培养方案id不能为空");
        Assert.notNull(cmsStudyPlanAllocation.getPlanVersion(), "培养方案版本不能为空");
        Assert.notNull(cmsStudyPlanAllocation.getDeleted(), "删除标记不能为空");
        Assert.notNull(cmsStudyPlanAllocation.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsStudyPlanAllocation cmsStudyPlanAllocation) {
        Assert.notNull(cmsStudyPlanAllocation, "更新对象不能为空");
        Assert.notNull(cmsStudyPlanAllocation.getId(), "更新对象id不能为空");
        Assert.notNull(cmsStudyPlanAllocation.getModifyTime(), "更新时间不能为空");

        return cmsStudyPlanAllocationMapper.updateById(cmsStudyPlanAllocation);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsStudyPlanAllocationMapper.deleteById(id);
    }

    @Override
    public int deleteByPlanId(Long planId) {
        Assert.notNull(planId, "planId不能为空");

        CmsStudyPlanAllocationQuery query = CmsStudyPlanAllocationQuery.listQuery();
        query.setPlanId(planId);
        query.setModifyTime(new Date());
        return cmsStudyPlanAllocationMapper.deleteByQuery(query);
    }

    @Override
    public int deleteByPlanAndUserId(Long planId, Long userId) {
        Assert.notNull(planId, "planId不能为空");
        Assert.notNull(userId, "userId不能为空");

        CmsStudyPlanAllocationQuery query = CmsStudyPlanAllocationQuery.listQuery();
        query.setPlanId(planId);
        query.setUserId(userId);
        query.setModifyTime(new Date());
        return cmsStudyPlanAllocationMapper.deleteByQuery(query);
    }

    @Override
    public List<CmsStudyPlanAllocation> selectListByParam(CmsStudyPlanAllocationQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanAllocationMapper.selectListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsStudyPlanAllocationQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanAllocationMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsStudyPlanAllocationQuery query) {
        query.validateBaseArgument();

        if (query.getId() == null
                && query.getGtId() == null
                && query.getUserId() == null
                && query.getPlanId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }
    }
}