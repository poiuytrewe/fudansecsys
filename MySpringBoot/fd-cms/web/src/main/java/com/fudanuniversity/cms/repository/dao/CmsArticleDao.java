package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;

import java.util.Date;
import java.util.List;

/**
 * CmsArticleDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsArticleDao {

    /**
     * 保存处理
     */
    int insert(CmsArticle cmsArticle);

    /**
     * 批量upsert
     */

    /**
     * 根据id更新处理
     */
    int updateById(CmsArticle cmsArticle);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */

    List<CmsArticle> selectInfoListByParam(CmsArticleQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsArticleQuery query);

    Long selectByDateForUpdate(CmsArticle cmsArticle);

    Long selectByDateForSave(CmsArticle cmsArticle);

}
