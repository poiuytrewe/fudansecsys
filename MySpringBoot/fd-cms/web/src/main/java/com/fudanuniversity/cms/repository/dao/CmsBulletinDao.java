package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsBulletin;
import com.fudanuniversity.cms.repository.query.CmsBulletinQuery;

import java.util.List;

/**
 * CmsBulletinDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsBulletinDao {

    /**
     * 保存处理
     */
    int insert(CmsBulletin cmsBulletin);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsBulletin> cmsBulletinList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsBulletin cmsBulletin);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsBulletin> selectListByParam(CmsBulletinQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsBulletinQuery query);

    void readAllBulletin(CmsBulletin bulletin);

}
