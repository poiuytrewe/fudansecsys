package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.*;

import java.util.List;

/**
 * CmsStudyPlanStageService
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:54:25
 */
public interface CmsStudyPlanStageService {

    /**
     * 保存处理
     */
    void addCmsStudyPlanStage(CmsStudyPlanStageAddVo addVo);

    /**
     * 根据id更新处理
     */
    void updateCmsStudyPlanStageById(CmsStudyPlanStageUpdateVo updateVo);

    /**
     *
     */
    void editCmsStudyPlanStages(List<CmsStudyPlanStageEditVo> editVoList);

    /**
     * 根据id删除处理
     */
    void deleteCmsStudyPlanStageById(Long id);

    /**
     * 分页查询数据列表
     */
    List<CmsStudyPlanStageVo> queryCmsStudyPlanStageList(CmsStudyPlanStageQueryVo queryVo);

}