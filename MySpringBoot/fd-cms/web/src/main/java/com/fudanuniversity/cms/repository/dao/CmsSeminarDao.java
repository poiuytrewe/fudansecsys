package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsSeminar;
import com.fudanuniversity.cms.repository.query.CmsSeminarQuery;

import java.util.List;

/**
 * CmsSeminarDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsSeminarDao {

    /**
     * 保存处理
     */
    int insert(CmsSeminar cmsSeminar);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsSeminar> cmsSeminarList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsSeminar cmsSeminar);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsSeminar> selectListByParam(CmsSeminarQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsSeminarQuery query);

    CmsSeminar selectFile(Long id);

}
