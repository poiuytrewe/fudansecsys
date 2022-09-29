package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanAllocationQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsStudyPlanAllocationMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-07 11:39:06
 */
@Mapper
public interface CmsStudyPlanAllocationMapper {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlanAllocation cmsStudyPlanAllocation);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlanAllocation> cmsStudyPlanAllocationList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 删除处理
     */
    int deleteByQuery(CmsStudyPlanAllocationQuery query);

    /**
     * 更新处理
     */
    int updateById(CmsStudyPlanAllocation cmsStudyPlanAllocation);

    /**
     * 根据条件查询信息列表
     */
    List<CmsStudyPlanAllocation> selectListByParam(CmsStudyPlanAllocationQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsStudyPlanAllocationQuery query);
}
