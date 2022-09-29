package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanStageService;
import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanStageDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanStageQuery;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * CmsStudyPlanStageService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
@Service
public class CmsStudyPlanStageServiceImpl implements CmsStudyPlanStageService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanStageServiceImpl.class);

    @Resource
    private CmsStudyPlanStageDao cmsStudyPlanStageDao;

    @Resource
    private CmsStudyPlanComponent cmsStudyPlanComponent;

    /**
     * 保存处理
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addCmsStudyPlanStage(CmsStudyPlanStageAddVo addVo) {
        Long planId = addVo.getPlanId();
        CmsStudyPlan cmsStudyPlan = cmsStudyPlanComponent.queryStudyPlanById(planId);
        AssertUtils.notNull(cmsStudyPlan, "培养计划[" + planId + "]不存在");

        cmsStudyPlanComponent.increaseStudyPlanVersion(cmsStudyPlan.getId());
        Integer term = addVo.getTerm();
        List<CmsStudyPlanStage> planStages = cmsStudyPlanComponent.queryStudyPlanStages(planId, term);
        CmsStudyPlanStage studyPlanStage = new CmsStudyPlanStage();
        studyPlanStage.setPlanId(addVo.getPlanId());
        studyPlanStage.setTerm(term);
        int index = planStages.size() + 1;
        studyPlanStage.setIndex(index);
        studyPlanStage.setEndDate(addVo.getEndDate());
        studyPlanStage.setCreateTime(new Date());
        int affect = cmsStudyPlanStageDao.insert(studyPlanStage);
        logger.info("保存CmsStudyPlanStage affect:{}, cmsStudyPlanStage: {}", affect, addVo);
        AssertUtils.state(affect == 1);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCmsStudyPlanStageById(CmsStudyPlanStageUpdateVo updateVo) {
        Long stageId = updateVo.getId();
        AssertUtils.notNull(stageId);
        CmsStudyPlanStage stage = cmsStudyPlanComponent.queryStudyPlanStageById(stageId);
        AssertUtils.notNull(stage, "培养计划阶段[" + stageId + "]不存在");

        cmsStudyPlanComponent.increaseStudyPlanVersion(stage.getPlanId());
        CmsStudyPlanStage updater = new CmsStudyPlanStage();
        updater.setId(stageId);
        updater.setEndDate(updateVo.getEndDate());
        updater.setModifyTime(new Date());
        int affect = cmsStudyPlanStageDao.updateById(updater);
        logger.info("更新CmsStudyPlanStage affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void editCmsStudyPlanStages(List<CmsStudyPlanStageEditVo> editVoList) {
        AssertUtils.notEmpty(editVoList, "培养计划阶段列表不能为空");
        List<Long> stageIds = Lists.transform(editVoList, CmsStudyPlanStageEditVo::getId);
        List<CmsStudyPlanStage> stages = cmsStudyPlanComponent.queryStudyPlanStagesById(stageIds);
        AssertUtils.notEmpty(stages, "培养计划阶段列表不能为空");
        Long planId = null;
        for (CmsStudyPlanStage stage : stages) {
            if (planId == null) {
                planId = stage.getPlanId();
            } else {
                AssertUtils.state(planId.equals(stage.getPlanId()), "必须为同一个培养计划阶段");
            }
        }

        cmsStudyPlanComponent.increaseStudyPlanVersion(planId);
        Map<Integer, List<CmsStudyPlanStage>> termGroupMap = stages.stream().collect(
                groupingBy(CmsStudyPlanStage::getTerm, LinkedHashMap::new, Collectors.toList()));
        Collection<List<CmsStudyPlanStage>> termStages = termGroupMap.values();
        for (int i = 0; i < termStages.size(); i++) {
            CmsStudyPlanStageEditVo updateVo = editVoList.get(i);
            CmsStudyPlanStage updater = new CmsStudyPlanStage();
            updater.setId(updateVo.getId());
            updater.setTerm(updateVo.getTerm());
            updater.setIndex(i + 1);
            updater.setEndDate(updateVo.getEndDate());
            updater.setModifyTime(new Date());
            int affect = cmsStudyPlanStageDao.updateById(updater);
            logger.info("更新CmsStudyPlanStage affect:{}, updater: {}", affect, updater);
            AssertUtils.state(affect == 1);
        }
    }

    /**
     * 根据id删除处理
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCmsStudyPlanStageById(Long id) {
        AssertUtils.notNull(id);
        CmsStudyPlanStage stage = cmsStudyPlanComponent.queryStudyPlanStageById(id);
        AssertUtils.notNull(stage, "培养计划阶段[" + stage + "]不存在");
        int delAffect = cmsStudyPlanStageDao.deleteById(id);
        logger.info("删除CmsStudyPlanStage affect:{}, id: {}", delAffect, id);
        AssertUtils.state(delAffect == 1);

        cmsStudyPlanComponent.increaseStudyPlanVersion(stage.getPlanId());
        //删除一个阶段后，在同一个其他plan下的stage需要更新index
        Long planId = stage.getPlanId();
        Integer term = stage.getTerm();
        List<CmsStudyPlanStage> planStageList = cmsStudyPlanComponent.queryStudyPlanStages(planId, term);
        for (int i = 0; i < planStageList.size(); i++) {
            CmsStudyPlanStage planStage = planStageList.get(i);
            Integer stageIndex = planStage.getIndex();
            if (!Objects.equals(stageIndex, i + 1)) {
                CmsStudyPlanStage updater = new CmsStudyPlanStage();
                updater.setId(planStage.getId());
                updater.setIndex(i + 1);
                updater.setModifyTime(new Date());
                int updateAffect = cmsStudyPlanStageDao.updateById(updater);
                logger.info("更新CmsStudyPlanStage affect:{}, updater: {}", updateAffect, updater);
                AssertUtils.state(updateAffect == 1);
            }
        }
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public List<CmsStudyPlanStageVo> queryCmsStudyPlanStageList(CmsStudyPlanStageQueryVo queryVo) {
        CmsStudyPlanStageQuery query = new CmsStudyPlanStageQuery();
        query.setId(queryVo.getId());
        query.setPlanId(queryVo.getPlanId());
        query.setTerm(queryVo.getTerm());
        query.setIndex(queryVo.getIndex());
        query.setEndDate(queryVo.getEndDate());
        query.setPlanId(queryVo.getPlanId());
        query.setSorts(SortColumn.create("term", SortMode.ASC), SortColumn.create("index", SortMode.ASC));
        List<CmsStudyPlanStage> cmsStudyPlanStageList = cmsStudyPlanStageDao.selectListByParam(query);
        return cmsStudyPlanStageList.stream().map(stage -> {
            CmsStudyPlanStageVo stageVo = new CmsStudyPlanStageVo();
            stageVo.setId(stage.getId());
            stageVo.setPlanId(stage.getPlanId());
            stageVo.setTerm(stage.getTerm());
            stageVo.setIndex(stage.getIndex());
            stageVo.setEndDate(stage.getEndDate());
            stageVo.setCreateTime(stage.getCreateTime());
            stageVo.setModifyTime(stage.getModifyTime());
            return stageVo;
        }).collect(Collectors.toList());
    }
}