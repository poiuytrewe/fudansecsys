package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanItem;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemQuery;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsStudyPlanAllocationMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 19:58:05
 */
@Mapper
public interface CmsStudyPlanItemMapper {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlanItem cmsStudyPlanItem);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlanItem> cmsStudyPlanItemList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 软删除
     */
    int deleteByQuery(CmsStudyPlanItemQuery query);

    /**
     * 更新处理
     */
    int updateById(CmsStudyPlanItem cmsStudyPlanItem);

    /**
     * 根据条件查询信息列表
     */
    List<CmsStudyPlanItem> selectListByParam(CmsStudyPlanItemQuery query);

    /**
     *
     */
    List<CmsStudyPlanItemInfo> selectInfoByParam(CmsStudyPlanItemQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsStudyPlanItemQuery query);

}
