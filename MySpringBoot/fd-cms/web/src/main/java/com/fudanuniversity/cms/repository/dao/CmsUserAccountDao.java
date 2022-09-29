package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.fudanuniversity.cms.repository.query.CmsUserAccountQuery;

import java.util.List;

/**
 * CmsUserAccountDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
 */
public interface CmsUserAccountDao {

    /**
     * 保存处理
     */
    int insert(CmsUserAccount cmsUserAccount);

    int replace(CmsUserAccount cmsUserAccount);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsUserAccount> cmsUserAccountList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsUserAccount cmsUserAccount);

    /**
     * 根据id删除
     * @param stuId
     */
    int deleteByStuId(String stuId);

    /**
     * 查询数据
     */
    List<CmsUserAccount> selectListByParam(CmsUserAccountQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsUserAccountQuery query);
}
