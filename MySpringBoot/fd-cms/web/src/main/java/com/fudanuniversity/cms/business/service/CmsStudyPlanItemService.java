package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.study.plan.*;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

import java.util.List;

/**
 * CmsStudyPlanAllocationService
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:54:25
 */
public interface CmsStudyPlanItemService {

    /**
     * 管理员更新培养计划状态
     */
    void editStudyPlanItem(CmsStudyPlanItemEditVo editVo);

    /**
     * 根据id删除处理
     */
    void deleteStudyPlanItemById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsStudyPlanItemVo> queryPagingResult(CmsStudyPlanItemQueryVo queryVo, Paging paging);

    /**
     * 用户更新培养计划状态
     */
    void editUserStudyPlanItem(Long userId, CmsStudyPlanItemUserEditVo userEditVo);
}