package com.fudanuniversity.cms.repository.dao.impl;

import com.fudanuniversity.cms.repository.dao.CmsArticleDao;
import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.mapper.CmsArticleMapper;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * CmsArticleDao 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-04 14:15:26
 */
@Repository
public class CmsArticleDaoImpl implements CmsArticleDao {

    @Resource
    private CmsArticleMapper cmsArticleMapper;

    @Override
    public int insert(CmsArticle cmsArticle) {
        Assert.notNull(cmsArticle, "保存对象不能为空");
        return cmsArticleMapper.insert(cmsArticle);
    }

    @Override
    public int updateById(CmsArticle cmsArticle) {
        Assert.notNull(cmsArticle, "更新对象不能为空");
        Assert.notNull(cmsArticle.getId(), "更新对象id不能为空");
        Assert.notNull(cmsArticle.getModifyTime(), "更新时间不能为空");

        return cmsArticleMapper.updateById(cmsArticle);
    }

    @Override
    public int deleteById(Long id) {
        Assert.notNull(id, "删除记录id不能为空");

        return cmsArticleMapper.deleteById(id);
    }

    @Override
    public List<CmsArticle> selectInfoListByParam(CmsArticleQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        return cmsArticleMapper.selectInfoListByParam(query);
    }

    @Override
    public Long selectCountByParam(CmsArticleQuery query) {
        Assert.notNull(query, "查询参数不能为空");

        return cmsArticleMapper.selectCountByParam(query);
    }

    @Override
    public Long selectByDateForUpdate(CmsArticle cmsArticle){
        Assert.notNull(cmsArticle,"不能为空");
        return cmsArticleMapper.selectByDateForUpdate(cmsArticle);
    }

    @Override
    public Long selectByDateForSave(CmsArticle cmsArticle){
        Assert.notNull(cmsArticle,"不能为空");
        return cmsArticleMapper.selectByDateForSave(cmsArticle);
    }

}