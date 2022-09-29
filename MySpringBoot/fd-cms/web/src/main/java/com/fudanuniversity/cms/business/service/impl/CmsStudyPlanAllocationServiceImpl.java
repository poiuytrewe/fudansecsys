package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanAllocationStatusEnum;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanAllocationDao;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanItemDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanAllocation;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanAllocationQuery;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemInfo;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemQuery;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CmsStudyPlanAllocationService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
@Service
public class CmsStudyPlanAllocationServiceImpl implements CmsStudyPlanAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanAllocationServiceImpl.class);

    @Resource
    private CmsStudyPlanItemDao cmsStudyPlanItemDao;

    @Resource
    private CmsStudyPlanAllocationDao cmsStudyPlanAllocationDao;

    @Resource
    private CmsStudyPlanComponent cmsStudyPlanComponent;

    @Resource
    private CmsUserComponent cmsUserComponent;

    /**
     * 查询某个用户的
     */
    @Override
    public List<CmsStudyPlanAllocationVo> queryAllocationList(Long userId) {
        CmsStudyPlanAllocationQuery query = CmsStudyPlanAllocationQuery.listQuery();
        query.setUserId(userId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
        List<CmsStudyPlanAllocation> allocationList = cmsStudyPlanAllocationDao.selectListByParam(query);

        if (CollectionUtils.isEmpty(allocationList)) {
            return Collections.emptyList();
        }

        List<Long> planIds = Lists.transform(allocationList, CmsStudyPlanAllocation::getPlanId);
        Map<Long, CmsStudyPlan> studyPlanMap = cmsStudyPlanComponent.queryStudyPlanMap(planIds);

        return allocationList.stream().map(allocation -> {
            CmsStudyPlanAllocationVo allocationVo = new CmsStudyPlanAllocationVo();
            allocationVo.setId(allocation.getId());
            Long planId = allocation.getPlanId();
            CmsStudyPlan studyPlan = studyPlanMap.get(planId);
            if (studyPlan != null) {
                allocationVo.setPlanId(studyPlan.getId());
                allocationVo.setEnrollYear(studyPlan.getEnrollYear());
                allocationVo.setReferenceDate(studyPlan.getReferenceDate());
                allocationVo.setName(studyPlan.getName());
                StudyPlanAllocationStatusEnum allocationStatusEnum =
                        StudyPlanAllocationStatusEnum.eval(studyPlan.getVersion(), allocation.getPlanVersion());
                allocationVo.setStatus(allocationStatusEnum.getCode());
            }
            allocationVo.setCreateTime(allocation.getCreateTime());
            allocationVo.setModifyTime(allocation.getModifyTime());
            return allocationVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CmsStudyPlanAllocationInfoVo> queryAllocationInfoList(Long planId) {
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan);

        CmsStudyPlanAllocationQuery query = CmsStudyPlanAllocationQuery.listQuery();
        query.setPlanId(planId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
        List<CmsStudyPlanAllocation> allocationList = cmsStudyPlanAllocationDao.selectListByParam(query);

        List<Long> userIds = Lists.transform(allocationList, CmsStudyPlanAllocation::getUserId);
        Map<Long, CmsUser> userMap = Collections.emptyMap();
        Map<Long, CmsStudyPlanItemInfo> itemInfoVoMap = Collections.emptyMap();
        if (CollectionUtils.isNotEmpty(userIds)) {
            userMap = cmsUserComponent.queryUserMap(userIds);
            List<CmsStudyPlanItemInfo> itemInfoVoList = queryUserStudyPlanItemInfoList(userIds, planId);
            itemInfoVoMap = itemInfoVoList.stream()
                    .collect(Collectors.toMap(CmsStudyPlanItemInfo::getUserId, Function.identity()));
        }

        List<CmsStudyPlanAllocationInfoVo> retList = Lists.newArrayList();
        for (CmsStudyPlanAllocation allocation : allocationList) {
            CmsUser cmsUser = userMap.get(allocation.getUserId());
            CmsStudyPlanItemInfo itemInfo = itemInfoVoMap.get(allocation.getUserId());
            CmsStudyPlanAllocationInfoVo allocationInfoVo = convertAllocationInfoVo(cmsUser, studyPlan, allocation, itemInfo);
            retList.add(allocationInfoVo);
        }

        return retList;
    }

    public List<CmsStudyPlanItemInfo> queryUserStudyPlanItemInfoList(List<Long> userIds, Long planId) {
        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.listQuery();
        query.setPlanId(planId);
        query.setUserIdList(userIds);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        return cmsStudyPlanItemDao.selectInfoByParam(query);
    }

    private CmsStudyPlanAllocationInfoVo convertAllocationInfoVo(
            CmsUser cmsUser, CmsStudyPlan studyPlan, CmsStudyPlanAllocation allocation, CmsStudyPlanItemInfo itemInfo) {
        CmsStudyPlanAllocationInfoVo allocationInfoVo = new CmsStudyPlanAllocationInfoVo();
        allocationInfoVo.setId(allocation.getId());
        Long userId = allocation.getUserId();
        allocationInfoVo.setUserId(userId);
        if (cmsUser != null) {
            allocationInfoVo.setUserStuId(cmsUser.getStuId());
            allocationInfoVo.setUserName(cmsUser.getName());
        }
        Long planId = allocation.getPlanId();
        allocationInfoVo.setPlanId(planId);
        CmsStudyPlanItemInfoVo itemInfoVo = convertItemInfoVo(userId, planId, itemInfo);
        allocationInfoVo.setInfo(itemInfoVo);
        StudyPlanAllocationStatusEnum allocationStatusEnum =
                StudyPlanAllocationStatusEnum.eval(studyPlan.getVersion(), allocation.getPlanVersion());
        allocationInfoVo.setStatus(allocationStatusEnum.getCode());
        allocationInfoVo.setCreateTime(allocation.getCreateTime());
        allocationInfoVo.setModifyTime(allocation.getModifyTime());
        return allocationInfoVo;
    }

    private CmsStudyPlanItemInfoVo convertItemInfoVo(Long userId, Long planId, CmsStudyPlanItemInfo info) {
        CmsStudyPlanItemInfoVo infoVo = new CmsStudyPlanItemInfoVo();
        infoVo.setUserId(userId);
        infoVo.setPlanId(planId);
        infoVo.setTotal(info == null ? 0L : info.getTotal());
        infoVo.setUnfinished(info == null ? 0L : info.getUnfinished());
        infoVo.setRegularUnfinished(info == null ? 0L : info.getRegularUnfinished());
        infoVo.setDelayUnfinished(info == null ? 0L : info.getDelayUnfinished());
        infoVo.setOvertimeUnfinished(info == null ? 0L : info.getOvertimeUnfinished());
        infoVo.setFinished(info == null ? 0L : info.getFinished());
        infoVo.setRegularFinished(info == null ? 0L : info.getRegularFinished());
        infoVo.setDelayFinished(info == null ? 0L : info.getDelayFinished());
        infoVo.setOvertimeFinished(info == null ? 0L : info.getOvertimeFinished());
        return infoVo;
    }

    @Override
    public CmsStudyPlanAllocationInfoVo queryAllocationInfo(Long userId, Long planId) {
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan);
        CmsStudyPlanAllocationQuery query = CmsStudyPlanAllocationQuery.singletonQuery();
        query.setPlanId(planId);
        query.setUserId(userId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
        List<CmsStudyPlanAllocation> allocationList = cmsStudyPlanAllocationDao.selectListByParam(query);
        CmsUser cmsUser = cmsUserComponent.queryUser(userId);
        if (CollectionUtils.isNotEmpty(allocationList)) {
            CmsStudyPlanAllocation allocation = allocationList.get(0);
            List<CmsStudyPlanItemInfo> itemInfoList
                    = queryUserStudyPlanItemInfoList(Collections.singletonList(userId), planId);
            CmsStudyPlanItemInfo itemInfo = CollectionUtils.isEmpty(itemInfoList) ? null : itemInfoList.get(0);
            return convertAllocationInfoVo(cmsUser, studyPlan, allocation, itemInfo);
        }
        return null;
    }

    @Override
    public CmsStudyPlanAllocationOverviewVo queryCmsStudyPlanOverview(Long planId) {
        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan);

        CmsStudyPlanAllocationOverviewVo overviewVo = new CmsStudyPlanAllocationOverviewVo();
        overviewVo.setId(studyPlan.getId());
        overviewVo.setName(studyPlan.getName());
        overviewVo.setEnrollYear(studyPlan.getEnrollYear());
        overviewVo.setReferenceDate(studyPlan.getReferenceDate());
        overviewVo.setCreateTime(studyPlan.getCreateTime());
        overviewVo.setModifyTime(studyPlan.getModifyTime());

        List<CmsStudyPlanStage> stages = cmsStudyPlanComponent.queryStudyPlanStageByPlanId(planId);
        List<CmsStudyPlanStageOverviewVo> stageOverviewList = cmsStudyPlanComponent.convertStageOverviewVoList(stages);
        overviewVo.setStages(stageOverviewList);
        return overviewVo;
    }

    @Override
    public CmsStudyPlanAllocationOverviewVo queryUserAllocationOverview(Long userId, Long planId) {
        CmsStudyPlanAllocation allocation = cmsStudyPlanComponent.queryUserStudyPlanAllocation(userId, planId);
        AssertUtils.notNull(allocation, "当前未分配对应的培养计划");

        CmsStudyPlan studyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(studyPlan);
        CmsStudyPlanAllocationOverviewVo overviewVo = new CmsStudyPlanAllocationOverviewVo();
        overviewVo.setId(studyPlan.getId());
        overviewVo.setName(studyPlan.getName());
        overviewVo.setEnrollYear(studyPlan.getEnrollYear());
        overviewVo.setReferenceDate(studyPlan.getReferenceDate());
        overviewVo.setCreateTime(studyPlan.getCreateTime());
        overviewVo.setModifyTime(studyPlan.getModifyTime());
        StudyPlanAllocationStatusEnum allocationStatusEnum =
                StudyPlanAllocationStatusEnum.eval(studyPlan.getVersion(), allocation.getPlanVersion());
        overviewVo.setStatus(allocationStatusEnum.getCode());
        overviewVo.setAllocationCreateTime(allocation.getCreateTime());
        overviewVo.setAllocationModifyTime(allocation.getModifyTime());
        CmsUser cmsUser = cmsUserComponent.queryUser(userId);
        AssertUtils.notNull(cmsUser);
        List<CmsStudyPlanStage> stages = cmsStudyPlanComponent.queryStudyPlanStageByPlanId(planId);
        List<CmsStudyPlanStageOverviewVo> stageOverviewList = cmsStudyPlanComponent.convertStageOverviewVoList(cmsUser, stages);
        overviewVo.setStages(stageOverviewList);
        return overviewVo;
    }

    /**
     * 根据id删除处理
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCmsStudyPlanAllocationById(Long allocationId) {
        CmsStudyPlanAllocation allocation = cmsStudyPlanComponent.queryUserStudyPlanAllocationById(allocationId);
        AssertUtils.notNull(allocation);
        int affect = cmsStudyPlanAllocationDao.deleteById(allocationId);
        logger.info("删除CmsStudyPlanAllocation affect:{}, id: {}", affect, allocationId);
        AssertUtils.state(affect == 1);
        cmsStudyPlanItemDao.deleteByPlanId(allocationId, allocation.getUserId());
    }
}