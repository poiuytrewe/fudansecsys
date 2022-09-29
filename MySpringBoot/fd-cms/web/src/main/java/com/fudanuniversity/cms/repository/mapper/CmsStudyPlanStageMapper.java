package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanStageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsStudyPlanStageMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 19:58:05
 */
@Mapper
public interface CmsStudyPlanStageMapper {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlanStage cmsStudyPlanStage);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlanStage> cmsStudyPlanStageList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     *
     */
    int deleteByPlanId(Long planId);

    /**
     * 更新处理
     */
    int updateById(CmsStudyPlanStage cmsStudyPlanStage);

    /**
     * 根据条件查询信息列表
     */
    List<CmsStudyPlanStage> selectListByParam(CmsStudyPlanStageQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsStudyPlanStageQuery query);

}
