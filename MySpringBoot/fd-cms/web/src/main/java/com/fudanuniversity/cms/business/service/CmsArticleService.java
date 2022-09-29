package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.article.CmsArticleAddVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleEditVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleQueryVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;

/**
 * CmsArticleService
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsArticleService {


    /**
     * 保存处理
     */
    void saveCmsArticle(CmsArticleAddVo articleAddVo);

    /**
     * 根据id更新处理
     */
    void editCmsArticleBy(CmsArticleEditVo editVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsArticleById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsArticleVo> queryPagingResult(Paging paging);

}