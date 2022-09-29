package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsStudyPlanItemDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanItem;
import com.fudanuniversity.cms.repository.mapper.CmsStudyPlanItemMapper;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemInfo;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * CmsStudyPlanAllocationDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 20:47:04
 */
@Repository
public class CmsStudyPlanItemDaoImpl implements CmsStudyPlanItemDao {

    @Resource
    private CmsStudyPlanItemMapper cmsStudyPlanItemMapper;

    @Override
    public int insert(CmsStudyPlanItem cmsStudyPlanItem) {
        Assert.notNull(cmsStudyPlanItem, "保存对象不能为空");

        validateEntity(cmsStudyPlanItem);

        return cmsStudyPlanItemMapper.insert(cmsStudyPlanItem);
    }

    @Override
    public int bulkUpsert(List<CmsStudyPlanItem> cmsStudyPlanItemList) {
        Assert.notEmpty(cmsStudyPlanItemList, "保存对象列表不能为空");

        for (CmsStudyPlanItem cmsStudyPlanItem : cmsStudyPlanItemList) {
            validateEntity(cmsStudyPlanItem);
        }

        return cmsStudyPlanItemMapper.bulkUpsert(cmsStudyPlanItemList);
    }

    private void validateEntity(CmsStudyPlanItem cmsStudyPlanItem) {
        Assert.notNull(cmsStudyPlanItem.getUserId(), "用户id不能为空");
        Assert.notNull(cmsStudyPlanItem.getPlanId(), "培养方案id不能为空");
        Assert.notNull(cmsStudyPlanItem.getPlanStageId(), "培养方案阶段id不能为空");
        Assert.notNull(cmsStudyPlanItem.getPlanWorkId(), "培养方案任务id不能为空");
        Assert.notNull(cmsStudyPlanItem.getPlanWorkStartDate(), "培养方案任务开始日期不能为空");
        Assert.notNull(cmsStudyPlanItem.getPlanWorkEndDate(), "培养方案任务结束日期不能为空");
        Assert.notNull(cmsStudyPlanItem.getPlanWorkDelay(), "培养方案任务延期天数不能为空");
        Assert.notNull(cmsStudyPlanItem.getFinished(), "是否完成不能为空");
        Assert.notNull(cmsStudyPlanItem.getDeleted(), "删除标记不能为空");
        Assert.notNull(cmsStudyPlanItem.getCreateTime(), "创建时间不能为空");
    }

    @Override
    public int updateById(CmsStudyPlanItem cmsStudyPlanItem) {
        Assert.notNull(cmsStudyPlanItem, "更新对象不能为空");
        Assert.notNull(cmsStudyPlanItem.getId(), "更新对象id不能为空");
        Assert.notNull(cmsStudyPlanItem.getModifyTime(), "更新时间不能为空");

        return cmsStudyPlanItemMapper.updateById(cmsStudyPlanItem);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsStudyPlanItemMapper.deleteById(id);
    }

    @Override
    public int deleteByIds(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return 0;
        }

        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.listQuery();
        query.setIdList(idList);
        query.setModifyTime(new Date());
        return cmsStudyPlanItemMapper.deleteByQuery(query);
    }

    @Override
    public int deleteByPlanId(Long planId) {
        Assert.notNull(planId, "planId不能为空");

        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.listQuery();
        query.setPlanId(planId);
        query.setModifyTime(new Date());
        return cmsStudyPlanItemMapper.deleteByQuery(query);
    }

    @Override
    public int deleteByPlanId(Long planId, Long userId) {
        Assert.notNull(planId, "planId不能为空");
        Assert.notNull(userId, "userId不能为空");

        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.listQuery();
        query.setPlanId(planId);
        query.setUserId(userId);
        query.setModifyTime(new Date());
        return cmsStudyPlanItemMapper.deleteByQuery(query);
    }


    @Override
    public List<CmsStudyPlanItem> selectListByParam(CmsStudyPlanItemQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanItemMapper.selectListByParam(query);
    }

    @Override
    public List<CmsStudyPlanItemInfo> selectInfoByParam(CmsStudyPlanItemQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanItemMapper.selectInfoByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsStudyPlanItemQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        validateQueryParameter(query);

        return cmsStudyPlanItemMapper.selectCountByParam(query);
    }

    private void validateQueryParameter(CmsStudyPlanItemQuery query) {
        query.validateBaseArgument();

        /*if (query.getId() == null
                && query.getGtId() == null
               && query.getUserId() == null
               && query.getPlanId() == null) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }*/
    }
}