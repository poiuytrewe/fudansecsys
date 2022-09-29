package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkVo;

import java.util.List;

/**
 * CmsStudyPlanWorkService
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:54:25
 */
public interface CmsStudyPlanWorkService {

    /**
     * 保存处理
     */
    void saveCmsStudyPlanWork(CmsStudyPlanWorkAddVo addVo);

    /**
     * 根据id更新处理
     */
    void updateCmsStudyPlanWorkById(CmsStudyPlanWorkUpdateVo updateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsStudyPlanWorkById(Long id);

    /**
     * 分页查询数据列表
     */
    List<CmsStudyPlanWorkVo> queryCmsStudyPlanWorkList(CmsStudyPlanWorkQueryVo queryVo);

}