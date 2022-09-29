package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsStudyPlanComponent;
import com.fudanuniversity.cms.business.service.CmsStudyPlanItemService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemEditVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemUserEditVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanItemVo;
import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.enums.StudyPlanItemStatusEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanItemDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanItem;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * CmsStudyPlanAllocationService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
@Service
public class CmsStudyPlanItemServiceImpl implements CmsStudyPlanItemService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanItemServiceImpl.class);

    @Resource
    private CmsStudyPlanItemDao cmsStudyPlanItemDao;

    @Resource
    private CmsStudyPlanComponent cmsStudyPlanComponent;

    @Override
    public void editStudyPlanItem(CmsStudyPlanItemEditVo editVo) {
        Long allocationId = editVo.getId();
        CmsStudyPlanItem allocation = cmsStudyPlanComponent.queryStudyPlanItemById(allocationId);
        AssertUtils.notNull(allocation, "培养计划安排[" + allocationId + "]不存在");

        CmsStudyPlanItem updater = new CmsStudyPlanItem();
        updater.setId(allocationId);
        Integer finished = editVo.getFinished();
        Date finishedDate = editVo.getFinishedDate();
        Date dbFinishedDate = allocation.getFinishedDate();

        Date current = new Date();
        if (finished == null) {
            updater.setFinishedDate(dbFinishedDate);//finished为null不更新完成日期
        } else {
            updater.setFinished(finished);
            if (BooleanEnum.isTrue(finished)) {
                //如果管理员设置了完成时间则取传入的完成时间
                finishedDate = finishedDate == null ? current : finishedDate;
                updater.setFinishedDate(finishedDate);
            }
        }
        Date delayDate = editVo.getDelayDate();
        if (delayDate != null) {
            Date planWorkEndDate = allocation.getPlanWorkEndDate();
            long delayDays = DateExUtils.evalCrossDays(planWorkEndDate, delayDate);
            updater.setPlanWorkDelay((int) delayDays);
        }
        updater.setRemark(updater.getRemark());
        updater.setModifyTime(new Date());
        int affect = cmsStudyPlanItemDao.updateById(updater);
        logger.info("更新CmsStudyPlanAllocation affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    public void deleteStudyPlanItemById(Long id) {
        int affect = cmsStudyPlanItemDao.deleteById(id);
        logger.info("删除CmsStudyPlanAllocation affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    @Override
    public PagingResult<CmsStudyPlanItemVo> queryPagingResult(CmsStudyPlanItemQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanItemVo> pagingResult = PagingResult.create(paging);

        CmsStudyPlanItemQuery query = new CmsStudyPlanItemQuery();
        Long count = cmsStudyPlanItemDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            //query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsStudyPlanItem> cmsStudyPlanItemList = cmsStudyPlanItemDao.selectListByParam(query);
            pagingResult.setRows(cmsStudyPlanItemList, allocation -> {
                CmsStudyPlanItemVo allocationVo = new CmsStudyPlanItemVo();
                allocationVo.setId(allocation.getId());
                allocationVo.setUserId(allocation.getUserId());
                allocationVo.setPlanId(allocation.getPlanId());
                allocationVo.setPlanStageId(allocation.getPlanStageId());
                allocationVo.setPlanWorkId(allocation.getPlanWorkId());
                allocationVo.setPlanWorkStartDate(allocation.getPlanWorkStartDate());
                Date planWorkEndDate = allocation.getPlanWorkEndDate();
                allocationVo.setPlanWorkEndDate(planWorkEndDate);
                Integer planWorkDelay = allocation.getPlanWorkDelay();
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
            });
        }

        return pagingResult;
    }

    @Override
    public void editUserStudyPlanItem(Long userId, CmsStudyPlanItemUserEditVo userEditVo) {
        Long itemId = userEditVo.getId();
        CmsStudyPlanItem allocation = cmsStudyPlanComponent.queryUserStudyPlanItem(userId, itemId);
        AssertUtils.notNull(allocation, "培养计划项目[" + itemId + "]不存在");

        CmsStudyPlanItem updater = new CmsStudyPlanItem();
        updater.setId(itemId);
        Integer finished = userEditVo.getFinished();
        Date current = new Date();
        if (finished == null) {
            updater.setFinishedDate(allocation.getFinishedDate());//finished为null不更新完成日期
        } else {
            updater.setFinished(finished);
            if (BooleanEnum.isTrue(finished)) {
                updater.setFinishedDate(current);
            }
        }
        updater.setRemark(updater.getRemark());
        updater.setModifyTime(current);
        int affect = cmsStudyPlanItemDao.updateById(updater);
        logger.info("更新CmsStudyPlanAllocation affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }
}