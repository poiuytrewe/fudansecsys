package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlan;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsStudyPlanMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 19:58:05
 */
@Mapper
public interface CmsStudyPlanMapper {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlan cmsStudyPlan);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlan> cmsStudyPlanList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsStudyPlan cmsStudyPlan);

    /**
     *
     */
    int increaseVersionById(Long id);

    /**
     * 根据条件查询信息列表
     */
    List<CmsStudyPlan> selectListByParam(CmsStudyPlanQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsStudyPlanQuery query);
}
