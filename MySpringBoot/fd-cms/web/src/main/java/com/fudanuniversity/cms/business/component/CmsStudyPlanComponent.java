package com.fudanuniversity.cms.business.component;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageOverviewVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkOverviewVo;
import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanItemStatusEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanWorkTypeEnum;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.*;
import com.fudanuniversity.cms.repository.entity.*;
import com.fudanuniversity.cms.repository.query.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Xinyue.Tang at 2021-05-05 19:23:04
 */
@Component
public class CmsStudyPlanComponent {

    @Resource
    private CmsStudyPlanDao cmsStudyPlanDao;

    @Resource
    private CmsStudyPlanStageDao cmsStudyPlanStageDao;

    @Resource
    private CmsStudyPlanWorkDao cmsStudyPlanWorkDao;

    @Resource
    private CmsStudyPlanAllocationDao cmsStudyPlanAllocationDao;

    @Resource
    private CmsStudyPlanItemDao cmsStudyPlanItemDao;

    /**
     * 每次变更培养计划的信息版本则增加
     */
    public int increaseStudyPlanVersion(Long planId) {
        AssertUtils.notNull(planId);
        CmsStudyPlanQuery query = CmsStudyPlanQuery.singletonQuery();
        query.setId(planId);
        int affect = cmsStudyPlanDao.increaseVersionById(planId);
        AssertUtils.state(affect == 1);
        return affect;
    }

    public CmsStudyPlan queryStudyPlanById(Long planId) {
        AssertUtils.notNull(planId);
        CmsStudyPlanQuery query = CmsStudyPlanQuery.singletonQuery();
        query.setId(planId);
        List<CmsStudyPlan> studyPlans = cmsStudyPlanDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(studyPlans)) {
            return studyPlans.get(0);
        }
        return null;
    }

    public Map<Long, CmsStudyPlan> queryStudyPlanMap(List<Long> planIds) {
        AssertUtils.notEmpty(planIds);
        CmsStudyPlanQuery query = CmsStudyPlanQuery.listQuery();
        query.setIdList(planIds);
        List<CmsStudyPlan> studyPlans = cmsStudyPlanDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(studyPlans)) {
            return studyPlans.stream().collect(Collectors.toMap(CmsStudyPlan::getId, Function.identity()));
        }
        return Collections.emptyMap();
    }

    public CmsStudyPlan queryStudyPlanByEnrollYear(Integer enrollYear) {
        AssertUtils.notNull(enrollYear);
        CmsStudyPlanQuery query = CmsStudyPlanQuery.singletonQuery();
        query.setEnrollYear(enrollYear);
        List<CmsStudyPlan> studyPlans = cmsStudyPlanDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(studyPlans)) {
            return studyPlans.get(0);
        }
        return null;
    }

    public CmsStudyPlanStage queryStudyPlanStageById(Long stageId) {
        AssertUtils.notNull(stageId);
        CmsStudyPlanStageQuery query = CmsStudyPlanStageQuery.singletonQuery();
        query.setId(stageId);
        List<CmsStudyPlanStage> stages = cmsStudyPlanStageDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(stages)) {
            return stages.get(0);
        }
        return null;
    }

    public List<CmsStudyPlanStage> queryStudyPlanStagesById(List<Long> stageIds) {
        AssertUtils.notEmpty(stageIds);
        CmsStudyPlanStageQuery query = CmsStudyPlanStageQuery.singletonQuery();
        query.setIdList(stageIds);
        query.setSorts(SortColumn.create("id", SortMode.ASC));
        return cmsStudyPlanStageDao.selectListByParam(query);
    }

    public List<CmsStudyPlanStage> queryStudyPlanStageByPlanId(Long planId) {
        AssertUtils.notNull(planId);
        CmsStudyPlanStageQuery query = CmsStudyPlanStageQuery.listQuery();
        query.setPlanId(planId);
        query.setSorts(SortColumn.create("term", SortMode.ASC), SortColumn.create("index", SortMode.ASC));
        return cmsStudyPlanStageDao.selectListByParam(query);
    }

    public List<CmsStudyPlanStage> queryStudyPlanStages(Long planId, Integer term) {
        AssertUtils.notNull(planId);
        CmsStudyPlanStageQuery query = CmsStudyPlanStageQuery.listQuery();
        query.setPlanId(planId);
        query.setTerm(term);
        query.setSorts(SortColumn.create("index", SortMode.ASC));
        return cmsStudyPlanStageDao.selectListByParam(query);
    }

    public CmsStudyPlanWork queryStudyPlanWorkById(Long workId) {
        AssertUtils.notNull(workId);
        CmsStudyPlanWorkQuery query = CmsStudyPlanWorkQuery.singletonQuery();
        query.setId(workId);
        List<CmsStudyPlanWork> works = cmsStudyPlanWorkDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(works)) {
            return works.get(0);
        }
        return null;
    }

    public List<CmsStudyPlanWork> queryStudyPlanWorks(List<Long> stageIds) {
        AssertUtils.notEmpty(stageIds);
        CmsStudyPlanWorkQuery query = CmsStudyPlanWorkQuery.listQuery();
        query.setPlanStageIdList(stageIds);
        return cmsStudyPlanWorkDao.selectListByParam(query);
    }

    public List<CmsStudyPlanWork> queryStudyPlanWorks(Long stageId, Integer workType) {
        AssertUtils.notNull(stageId);
        AssertUtils.notNull(workType);
        CmsStudyPlanWorkQuery query = CmsStudyPlanWorkQuery.listQuery();
        query.setPlanStageId(stageId);
        query.setWorkType(workType);
        return cmsStudyPlanWorkDao.selectListByParam(query);
    }

    public CmsStudyPlanAllocation queryUserStudyPlanAllocationById(Long allocationId) {
        CmsStudyPlanAllocationQuery query = CmsStudyPlanAllocationQuery.singletonQuery();
        query.setId(allocationId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        List<CmsStudyPlanAllocation> allocations = cmsStudyPlanAllocationDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(allocations)) {
            return allocations.get(0);
        }
        return null;
    }

    public CmsStudyPlanAllocation queryUserStudyPlanAllocation(Long userId, Long planId) {
        CmsStudyPlanAllocationQuery query = CmsStudyPlanAllocationQuery.singletonQuery();
        query.setUserId(userId);
        query.setPlanId(planId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        List<CmsStudyPlanAllocation> allocations = cmsStudyPlanAllocationDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(allocations)) {
            return allocations.get(0);
        }
        return null;
    }

    public List<CmsStudyPlanItem> queryStudyPlanItemsByUserIds(List<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.listQuery();
            query.setUserIdList(userIds);
            query.setDeleted(DeletedEnum.Normal.getCode().longValue());
            return cmsStudyPlanItemDao.selectListByParam(query);
        }
        return Collections.emptyList();
    }

    public CmsStudyPlanItem queryStudyPlanItemById(Long allocationId) {
        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.singletonQuery();
        query.setId(allocationId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        List<CmsStudyPlanItem> allocations = cmsStudyPlanItemDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(allocations)) {
            return allocations.get(0);
        }
        return null;
    }

    public CmsStudyPlanItem queryUserStudyPlanItem(Long userId, Long itemId) {
        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.singletonQuery();
        query.setId(itemId);
        query.setUserId(userId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        List<CmsStudyPlanItem> allocations = cmsStudyPlanItemDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(allocations)) {
            return allocations.get(0);
        }
        return null;
    }

    /**
     * Key: workId
     */
    public Map<Long, CmsStudyPlanItem> queryUserStudyPlanAllocationMap(Long userId) {
        CmsStudyPlanItemQuery query = CmsStudyPlanItemQuery.listQuery();
        query.setUserId(userId);
        query.setDeleted(DeletedEnum.Normal.getCode().longValue());
        List<CmsStudyPlanItem> allocations = cmsStudyPlanItemDao.selectListByParam(query);
        return allocations.stream().collect(Collectors.toMap(CmsStudyPlanItem::getPlanWorkId, Function.identity()));
    }

    /**
     * <pre>
     *  将StudyPlan下所有关联的work结构化组织起来
     *
     *   泛型描述：Map<stageId, Map<workType, List<CmsStudyPlanWork>>>
     * </pre>
     */
    public Map<Long, Map<Integer, List<CmsStudyPlanWork>>> convertCmsStudyPlanWorkMap(List<CmsStudyPlanWork> planWorks) {
        Map<Long, Map<Integer, List<CmsStudyPlanWork>>> retMap = Maps.newLinkedHashMap();
        //所有数据按照PlanStageId，WorkType，Index排序
        planWorks.sort((o1, o2) -> {
            int planStageIdComparison = o1.getPlanStageId().compareTo(o2.getPlanStageId());
            if (planStageIdComparison != 0) {
                return planStageIdComparison < 0 ? -1 : 1;
            }
            int workTypeComparison = o1.getWorkType().compareTo(o2.getWorkType());
            if (workTypeComparison != 0) {
                return workTypeComparison < 0 ? -1 : 1;
            }
            return o1.getIndex().compareTo(o2.getIndex());
        });
        for (CmsStudyPlanWork planWork : planWorks) {
            Long planStageId = planWork.getPlanStageId();
            Integer workType = planWork.getWorkType();
            //如果retMap中planStageId(key)对应的Map(value)为null，则初始化workTypeMap
            Map<Integer, List<CmsStudyPlanWork>> workTypeMap =
                    retMap.computeIfAbsent(planStageId, k -> Maps.newLinkedHashMap());
            //如果workTypeMap中workType(key)对应的List(value)为null，则初始化workList
            List<CmsStudyPlanWork> workList = workTypeMap.computeIfAbsent(workType, k -> Lists.newArrayList());
            workList.add(planWork);
        }
        return retMap;
    }

    public List<CmsStudyPlanStageOverviewVo> convertStageOverviewVoList(List<CmsStudyPlanStage> stages) {
        if (CollectionUtils.isNotEmpty(stages)) {
            List<CmsStudyPlanStageOverviewVo> retList = Lists.newArrayList();
            List<Long> stageIds = Lists.transform(stages, CmsStudyPlanStage::getId);
            List<CmsStudyPlanWork> planWorks = queryStudyPlanWorks(stageIds);
            Map<Long, Map<Integer, List<CmsStudyPlanWork>>> planStageWorkMap = convertCmsStudyPlanWorkMap(planWorks);
            for (CmsStudyPlanStage stage : stages) {
                CmsStudyPlanStageOverviewVo stageOverviewVo = new CmsStudyPlanStageOverviewVo();
                stageOverviewVo.setId(stage.getId());
                stageOverviewVo.setPlanId(stage.getPlanId());
                stageOverviewVo.setTerm(stage.getTerm());
                stageOverviewVo.setIndex(stage.getIndex());
                stageOverviewVo.setEndDate(stage.getEndDate());
                stageOverviewVo.setCreateTime(stage.getCreateTime());
                stageOverviewVo.setModifyTime(stage.getModifyTime());
                //任务
                Long stageId = stage.getId();
                Map<Integer, List<CmsStudyPlanWork>> workTypeMap = planStageWorkMap.getOrDefault(stageId, Collections.emptyMap());
                //公共任务
                List<CmsStudyPlanWork> commonWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Common.getCode());
                List<CmsStudyPlanWorkOverviewVo> commonWorkOverviewVoList = convertWorkOverviewVo(commonWorks);
                stageOverviewVo.setCommonWorks(commonWorkOverviewVoList);
                //科硕任务
                List<CmsStudyPlanWork> keshuoWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Keshuo.getCode());
                List<CmsStudyPlanWorkOverviewVo> keshuoWorkOverviewVoList = convertWorkOverviewVo(keshuoWorks);
                stageOverviewVo.setKeshuoWorks(keshuoWorkOverviewVoList);
                //学术型任务
                List<CmsStudyPlanWork> academicWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Academic.getCode());
                List<CmsStudyPlanWorkOverviewVo> academicWorkOverviewVoList = convertWorkOverviewVo(academicWorks);
                stageOverviewVo.setAcademicWorks(academicWorkOverviewVoList);
                //结合型任务
                List<CmsStudyPlanWork> synthesizingWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Synthesizing.getCode());
                List<CmsStudyPlanWorkOverviewVo> synthesizingWorkOverviewVoList = convertWorkOverviewVo(synthesizingWorks);
                stageOverviewVo.setSynthesizingWorks(synthesizingWorkOverviewVoList);
                //技术型公共任务
                List<CmsStudyPlanWork> technologyWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Technology.getCode());
                List<CmsStudyPlanWorkOverviewVo> technologyWorkOverviewVoList = convertWorkOverviewVo(technologyWorks);
                stageOverviewVo.setTechnologyWorks(technologyWorkOverviewVoList);
                retList.add(stageOverviewVo);
            }
            return retList;
        }
        return Collections.emptyList();
    }

    //用户培养计划任务会有任务的进度信息
    public List<CmsStudyPlanStageOverviewVo> convertStageOverviewVoList(CmsUser cmsUser, List<CmsStudyPlanStage> stages) {
        if (CollectionUtils.isNotEmpty(stages)) {
            List<CmsStudyPlanStageOverviewVo> retList = Lists.newArrayList();
            List<Long> stageIds = Lists.transform(stages, CmsStudyPlanStage::getId);
            List<CmsStudyPlanWork> planWorks = queryStudyPlanWorks(stageIds);
            Map<Long, Map<Integer, List<CmsStudyPlanWork>>> planStageWorkMap = convertCmsStudyPlanWorkMap(planWorks);
            Map<Long, CmsStudyPlanItem> allocationMap = queryUserStudyPlanAllocationMap(cmsUser.getId());
            for (CmsStudyPlanStage stage : stages) {
                CmsStudyPlanStageOverviewVo stageOverviewVo = convertCmsStudyPlanStageOverviewVo(stage);
                //任务
                Long stageId = stage.getId();
                Map<Integer, List<CmsStudyPlanWork>> workTypeMap = planStageWorkMap.getOrDefault(stageId, Collections.emptyMap());
                //公共任务
                List<CmsStudyPlanWork> commonWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Common.getCode());
                List<CmsStudyPlanWorkOverviewVo> commonWorkOverviewVoList = convertWorkOverviewVo(commonWorks, allocationMap);
                stageOverviewVo.setCommonWorks(commonWorkOverviewVoList);
                //科硕任务
                List<CmsStudyPlanWorkOverviewVo> keshuoWorkOverviewVoList = Collections.emptyList();
                if (BooleanEnum.isTrue(cmsUser.getKeshuo())) {
                    List<CmsStudyPlanWork> keshuoWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Keshuo.getCode());
                    keshuoWorkOverviewVoList = convertWorkOverviewVo(keshuoWorks, allocationMap);
                }
                stageOverviewVo.setKeshuoWorks(keshuoWorkOverviewVoList);
                StudyPlanWorkTypeEnum workTypeEnum = StudyPlanWorkTypeEnum.studyTypeOf(cmsUser.getStudyType());
                List<CmsStudyPlanWorkOverviewVo> academicWorkOverviewVoList = Collections.emptyList();
                List<CmsStudyPlanWorkOverviewVo> synthesizingWorkOverviewVoList = Collections.emptyList();
                List<CmsStudyPlanWorkOverviewVo> technologyWorkOverviewVoList = Collections.emptyList();
                if (Objects.equals(StudyPlanWorkTypeEnum.Academic, workTypeEnum)) {//学术型任务
                    List<CmsStudyPlanWork> academicWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Academic.getCode());
                    academicWorkOverviewVoList = convertWorkOverviewVo(academicWorks);
                } else if (Objects.equals(StudyPlanWorkTypeEnum.Synthesizing, workTypeEnum)) {//结合型任务
                    List<CmsStudyPlanWork> synthesizingWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Synthesizing.getCode());
                    synthesizingWorkOverviewVoList = convertWorkOverviewVo(synthesizingWorks, allocationMap);
                } else if (Objects.equals(StudyPlanWorkTypeEnum.Technology, workTypeEnum)) {//技术型公共任务
                    List<CmsStudyPlanWork> technologyWorks = workTypeMap.get(StudyPlanWorkTypeEnum.Technology.getCode());
                    technologyWorkOverviewVoList = convertWorkOverviewVo(technologyWorks, allocationMap);
                }
                stageOverviewVo.setAcademicWorks(academicWorkOverviewVoList);
                stageOverviewVo.setSynthesizingWorks(synthesizingWorkOverviewVoList);
                stageOverviewVo.setTechnologyWorks(technologyWorkOverviewVoList);
                retList.add(stageOverviewVo);
            }
            return retList;
        }
        return Collections.emptyList();
    }

    private CmsStudyPlanStageOverviewVo convertCmsStudyPlanStageOverviewVo(CmsStudyPlanStage stage) {
        CmsStudyPlanStageOverviewVo stageOverviewVo = new CmsStudyPlanStageOverviewVo();
        stageOverviewVo.setId(stage.getId());
        stageOverviewVo.setPlanId(stage.getPlanId());
        stageOverviewVo.setTerm(stage.getTerm());
        stageOverviewVo.setIndex(stage.getIndex());
        stageOverviewVo.setEndDate(stage.getEndDate());
        stageOverviewVo.setCreateTime(stage.getCreateTime());
        stageOverviewVo.setModifyTime(stage.getModifyTime());
        return stageOverviewVo;
    }

    private List<CmsStudyPlanWorkOverviewVo> convertWorkOverviewVo(List<CmsStudyPlanWork> stageWorks) {
        if (CollectionUtils.isNotEmpty(stageWorks)) {
            List<CmsStudyPlanWorkOverviewVo> workOverviewVoList = Lists.newArrayList();
            stageWorks.forEach(commonWork -> {
                CmsStudyPlanWorkOverviewVo workOverviewVo = new CmsStudyPlanWorkOverviewVo();
                workOverviewVo.setId(commonWork.getId());
                workOverviewVo.setPlanId(commonWork.getPlanId());
                workOverviewVo.setPlanStageId(commonWork.getPlanStageId());
                workOverviewVo.setWorkType(commonWork.getWorkType());
                workOverviewVo.setIndex(commonWork.getIndex());
                workOverviewVo.setName(commonWork.getName());
                workOverviewVo.setCreateTime(commonWork.getCreateTime());
                workOverviewVo.setModifyTime(commonWork.getModifyTime());
                workOverviewVoList.add(workOverviewVo);
            });
            return workOverviewVoList;
        }
        return Collections.emptyList();
    }

    private List<CmsStudyPlanWorkOverviewVo> convertWorkOverviewVo(
            List<CmsStudyPlanWork> stageWorks, Map<Long, CmsStudyPlanItem> allocationMap) {
        if (CollectionUtils.isNotEmpty(stageWorks)) {
            List<CmsStudyPlanWorkOverviewVo> workOverviewVoList = Lists.newArrayList();
            stageWorks.forEach(commonWork -> {
                CmsStudyPlanItem allocation = allocationMap.get(commonWork.getId());
                if (allocation != null) {
                    CmsStudyPlanWorkOverviewVo workOverviewVo = new CmsStudyPlanWorkOverviewVo();
                    workOverviewVo.setId(commonWork.getId());
                    workOverviewVo.setPlanId(commonWork.getPlanId());
                    workOverviewVo.setPlanStageId(commonWork.getPlanStageId());
                    workOverviewVo.setWorkType(commonWork.getWorkType());
                    workOverviewVo.setIndex(commonWork.getIndex());
                    workOverviewVo.setName(commonWork.getName());
                    workOverviewVo.setCreateTime(commonWork.getCreateTime());
                    workOverviewVo.setModifyTime(commonWork.getModifyTime());
                    CmsStudyPlanItemVo allocationVo = convertCmsStudyPlanItemVo(allocation);
                    workOverviewVo.setAllocation(allocationVo);
                    workOverviewVoList.add(workOverviewVo);
                }
            });
            return workOverviewVoList;
        }
        return Collections.emptyList();
    }

    private CmsStudyPlanItemVo convertCmsStudyPlanItemVo(CmsStudyPlanItem allocation) {
        CmsStudyPlanItemVo allocationVo = new CmsStudyPlanItemVo();
        allocationVo.setId(allocation.getId());
        allocationVo.setUserId(allocation.getUserId());
        allocationVo.setPlanId(allocation.getPlanId());
        allocationVo.setPlanStageId(allocation.getPlanStageId());
        allocationVo.setPlanWorkId(allocation.getPlanWorkId());
        Date planWorkStartDate = allocation.getPlanWorkStartDate();
        allocationVo.setPlanWorkStartDate(planWorkStartDate);
        Date planWorkEndDate = allocation.getPlanWorkEndDate();
        allocationVo.setPlanWorkEndDate(planWorkEndDate);
        Integer planWorkDelay = allocation.getPlanWorkDelay();
        allocationVo.setPlanWorkExpectedEndDate(DateUtils.addDays(planWorkEndDate, planWorkDelay));
        allocationVo.setPlanWorkDelay(planWorkDelay);
        Integer finished = allocation.getFinished();
        allocationVo.setFinished(finished);
        Date finishedDate = allocation.getFinishedDate();
        allocationVo.setFinishedDate(finishedDate);
        StudyPlanItemStatusEnum statusEnum = StudyPlanItemStatusEnum
                .eval(planWorkEndDate, planWorkDelay, finished, finishedDate);
        allocationVo.setStatus(statusEnum.getCode());
        allocationVo.setRemark(allocation.getRemark());
        allocationVo.setCreateTime(allocation.getCreateTime());
        allocationVo.setModifyTime(allocation.getModifyTime());
        return allocationVo;
    }
}
