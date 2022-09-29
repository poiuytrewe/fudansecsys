package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsArticle;
import com.fudanuniversity.cms.repository.query.CmsArticleQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * CmsArticleMapper接口
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Mapper
public interface CmsArticleMapper {


    int insert(CmsArticle cmsArticle);

    int deleteById(Long id);

    int updateById(CmsArticle cmsArticle);

    List<CmsArticle> selectInfoListByParam(CmsArticleQuery query);

    Long selectCountByParam(CmsArticleQuery query);

    Long selectByDateForUpdate(CmsArticle cmsArticle);

    Long selectByDateForSave(CmsArticle cmsArticle);

}
