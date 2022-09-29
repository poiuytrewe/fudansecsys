package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanWork;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanWorkQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsStudyPlanWorkMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 19:58:05
 */
@Mapper
public interface CmsStudyPlanWorkMapper {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlanWork cmsStudyPlanWork);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlanWork> cmsStudyPlanWorkList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 删除处理
     */
    int deleteByPlanId(Long planId);

    /**
     * 更新处理
     */
    int updateById(CmsStudyPlanWork cmsStudyPlanWork);

    /**
     * 根据条件查询信息列表
     */
    List<CmsStudyPlanWork> selectListByParam(CmsStudyPlanWorkQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsStudyPlanWorkQuery query);

}
