package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanWorkService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkVo;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanWorkDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanWork;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanWorkQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * CmsStudyPlanWorkService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
@Service
public class CmsStudyPlanWorkServiceImpl implements CmsStudyPlanWorkService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanWorkServiceImpl.class);

    @Resource
    private CmsStudyPlanWorkDao cmsStudyPlanWorkDao;

    @Resource
    private CmsStudyPlanComponent cmsStudyPlanComponent;

    /**
     * 保存处理
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveCmsStudyPlanWork(CmsStudyPlanWorkAddVo addVo) {
        Long planStageId = addVo.getPlanStageId();
        Integer workType = addVo.getWorkType();
        CmsStudyPlanStage planStage = cmsStudyPlanComponent.queryStudyPlanStageById(planStageId);
        AssertUtils.notNull(planStage, "培养计划阶段[" + planStageId + "]不存在");

        cmsStudyPlanComponent.increaseStudyPlanVersion(planStage.getPlanId());
        List<CmsStudyPlanWork> planWorks = cmsStudyPlanComponent.queryStudyPlanWorks(planStageId, workType);
        Long planId = planStage.getPlanId();
        CmsStudyPlanWork studyPlanWork = new CmsStudyPlanWork();
        studyPlanWork.setPlanId(planId);
        studyPlanWork.setPlanStageId(planStageId);
        studyPlanWork.setWorkType(workType);
        int index = planWorks.size() + 1;
        studyPlanWork.setIndex(index);
        studyPlanWork.setName(addVo.getName());
        studyPlanWork.setCreateTime(new Date());
        int affect = cmsStudyPlanWorkDao.insert(studyPlanWork);
        logger.info("保存CmsStudyPlanWork affect:{}, cmsStudyPlanWork: {}", affect, addVo);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCmsStudyPlanWorkById(CmsStudyPlanWorkUpdateVo updateVo) {
        Long workId = updateVo.getId();
        CmsStudyPlanWork planWork = cmsStudyPlanComponent.queryStudyPlanWorkById(workId);
        AssertUtils.notNull(planWork, "培养计划阶段任务[" + workId + "]不存在");

        cmsStudyPlanComponent.increaseStudyPlanVersion(planWork.getPlanId());
        CmsStudyPlanWork updater = new CmsStudyPlanWork();
        updater.setId(updateVo.getId());
        updater.setName(updateVo.getName());
        updater.setModifyTime(new Date());
        int affect = cmsStudyPlanWorkDao.updateById(updater);
        logger.info("更新CmsStudyPlanWork affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCmsStudyPlanWorkById(Long workId) {
        AssertUtils.notNull(workId);
        CmsStudyPlanWork delPlanWork = cmsStudyPlanComponent.queryStudyPlanWorkById(workId);
        AssertUtils.notNull(delPlanWork, "培养计划阶段任务[" + delPlanWork + "]不存在");
        int affect = cmsStudyPlanWorkDao.deleteById(workId);
        logger.info("删除CmsStudyPlanWork affect:{}, id: {}", affect, workId);
        AssertUtils.state(affect == 1);

        cmsStudyPlanComponent.increaseStudyPlanVersion(delPlanWork.getPlanId());
        //删除一个阶段后，在同一个其他stage下的work需要更新index
        Long stageId = delPlanWork.getPlanStageId();
        Integer workType = delPlanWork.getWorkType();
        List<CmsStudyPlanWork> planWorks = cmsStudyPlanComponent.queryStudyPlanWorks(stageId, workType);
        for (int i = 0; i < planWorks.size(); i++) {
            CmsStudyPlanWork refPlanWork = planWorks.get(i);
            Integer workIndex = refPlanWork.getIndex();
            if (!Objects.equals(workIndex, i + 1)) {
                CmsStudyPlanWork updater = new CmsStudyPlanWork();
                updater.setId(refPlanWork.getId());
                updater.setIndex(i + 1);
                updater.setModifyTime(new Date());
                int updateAffect = cmsStudyPlanWorkDao.updateById(updater);
                logger.info("更新CmsStudyPlanWork affect:{}, updater: {}", affect, updater);
                AssertUtils.state(updateAffect == 1);
            }
        }
    }

    /**
     * 查询数据列表
     */
    @Override
    public List<CmsStudyPlanWorkVo> queryCmsStudyPlanWorkList(CmsStudyPlanWorkQueryVo queryVo) {
        CmsStudyPlanWorkQuery query = CmsStudyPlanWorkQuery.listQuery();
        query.setId(queryVo.getId());
        query.setPlanId(queryVo.getPlanId());
        query.setWorkType(queryVo.getWorkType());
        query.setIndex(queryVo.getIndex());
        query.setName(queryVo.getName());

        query.setSorts(SortColumn.create("index", SortMode.ASC));
        List<CmsStudyPlanWork> cmsStudyPlanWorkList = cmsStudyPlanWorkDao.selectListByParam(query);

        return cmsStudyPlanWorkList.stream().map(work -> {
            CmsStudyPlanWorkVo workVo = new CmsStudyPlanWorkVo();
            workVo.setId(work.getId());
            workVo.setPlanId(work.getPlanId());
            workVo.setPlanStageId(work.getPlanStageId());
            workVo.setWorkType(work.getWorkType());
            workVo.setIndex(work.getIndex());
            workVo.setName(work.getName());
            workVo.setCreateTime(work.getCreateTime());
            workVo.setModifyTime(work.getModifyTime());
            return workVo;
        }).collect(Collectors.toList());
    }
}