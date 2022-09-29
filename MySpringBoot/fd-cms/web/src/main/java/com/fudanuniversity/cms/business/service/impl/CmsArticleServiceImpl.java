package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsArticleService;
import com.fudanuniversity.cms.business.vo.article.CmsArticleAddVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleEditVo;
import com.fudanuniversity.cms.business.vo.article.CmsArticleVo;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsArticleDao;
import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * CmsArticleService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Service
public class CmsArticleServiceImpl implements CmsArticleService {

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleServiceImpl.class);

    @Resource
    private CmsArticleDao cmsArticleDao;


    /**
     * 保存处理
     */
    @Override
    public void saveCmsArticle(CmsArticleAddVo articleAddVo) {
        CmsArticle article = new CmsArticle();
        article.setDate(articleAddVo.getDate());
        article.setTitle(articleAddVo.getTitle());
        article.setContent(articleAddVo.getContent());
        article.setCreateTime(new Date());
        if(selectByDateForSave(article)>0L){
            throw new BusinessException("请重新选择日期");
        }
        int affect = cmsArticleDao.insert(article);
        logger.info("保存CmsArticle affect:{}, cmsArticle: {}", affect, articleAddVo);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void editCmsArticleBy(CmsArticleEditVo editVo) {
        Long articleId = editVo.getId();
        CmsArticle article = queryArticleInfo(articleId);
        if (article == null) { //查看要修改的文章是否存在
            throw new BusinessException("当前文章已不存在");
        }

        CmsArticle updater = new CmsArticle();
        updater.setId(articleId);
        updater.setDate(editVo.getDate());
        updater.setTitle(editVo.getTitle());
        updater.setContent(editVo.getContent());
        updater.setModifyTime(new Date());
        if(selectByDateForUpdate(updater)>0){
            throw new BusinessException("请重新选择日期");
        }
        int affect = cmsArticleDao.updateById(updater);
        logger.info("更新CmsArticle affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsArticleById(Long id) {
        CmsArticle article = queryArticleInfo(id);
        if (article == null) {
            throw new BusinessException("当前文章已不存在");
        }

        int affect = cmsArticleDao.deleteById(id);
        logger.info("删除CmsArticle affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsArticleVo> queryPagingResult(Paging paging) {
        PagingResult<CmsArticleVo> pagingResult = PagingResult.create(paging);

        CmsArticleQuery query = CmsArticleQuery.listQuery();
        Long count = cmsArticleDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsArticle> cmsArticleList = cmsArticleDao.selectInfoListByParam(query);
            pagingResult.setRows(cmsArticleList, this::convertCmsArticleVo);
        }

        return pagingResult;
    }

    private CmsArticleVo convertCmsArticleVo(CmsArticle article) {
        CmsArticleVo articleVo = new CmsArticleVo();
        articleVo.setId(article.getId());
        articleVo.setDate(article.getDate());
        articleVo.setTitle(article.getTitle());
        articleVo.setContent(article.getContent());
        return articleVo;
    }

    private CmsArticle queryArticleInfo(Long articleId) {
        CmsArticleQuery query = CmsArticleQuery.singletonQuery();
        query.setId(articleId);
        List<CmsArticle> articles = cmsArticleDao.selectInfoListByParam(query);
        if (CollectionUtils.isNotEmpty(articles)) {
            return articles.get(0);
        }
        return null;
    }

    private Long selectByDateForUpdate(CmsArticle cmsArticle){
        return cmsArticleDao.selectByDateForUpdate(cmsArticle);
    }

    private Long selectByDateForSave(CmsArticle cmsArticle){
        return cmsArticleDao.selectByDateForSave(cmsArticle);
    }
}