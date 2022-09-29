package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsRecorder;
import com.fudanuniversity.cms.repository.query.CmsRecorderQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * CmsRecorderMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Mapper
public interface CmsRecorderMapper {

    /**
     * 保存处理
     */
    int insert(CmsRecorder cmsRecorder);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsRecorder> cmsRecorderList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsRecorder cmsRecorder);

    /**
     * 根据条件查询信息列表
     */
    List<CmsRecorder> selectDetailListByParam(CmsRecorderQuery query);

    /**
     * 根据条件查询信息列表
     */
    List<CmsRecorder> selectInfoListByParam(CmsRecorderQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsRecorderQuery query);

    List<CmsRecorder> selectRecorderFile(CmsRecorderQuery query);

    List<CmsRecorder> selectSummarizerFile(CmsRecorderQuery query);

}
