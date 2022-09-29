package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsRecorder;
import com.fudanuniversity.cms.repository.query.CmsRecorderQuery;

import java.util.Date;
import java.util.List;

/**
 * CmsRecorderDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsRecorderDao {

    /**
     * 保存处理
     */
    int insert(CmsRecorder cmsRecorder);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsRecorder> cmsRecorderList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsRecorder cmsRecorder);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsRecorder> selectDetailListByParam(CmsRecorderQuery query);

    /**
     * 查询数据
     */
    List<CmsRecorder> selectInfoListByParam(CmsRecorderQuery query);

    List<CmsRecorder> selectRecorderFile(CmsRecorderQuery query);

    List<CmsRecorder> selectSummarizerFile(CmsRecorderQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsRecorderQuery query);

}
