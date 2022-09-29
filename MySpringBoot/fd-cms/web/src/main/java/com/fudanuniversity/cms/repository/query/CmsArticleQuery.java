package com.fudanuniversity.cms.repository.query;

import com.fudanuniversity.cms.commons.model.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 查询对象
 * <p>
 * Created by Xinyue.Tang at 2021-05-04 14:12:39
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsArticleQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private Long id;

    /**
     * 字段备注:id
     * 数据库字段长度:(19,0)
     * 索引字段:是
     */
    private Date date;
    /**
     * 字段备注:名称 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String title;

    /**
     * 字段备注:内容 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 索引字段:不是
     */
    private String content;

    public static CmsArticleQuery singletonQuery() {
        CmsArticleQuery query = new CmsArticleQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsArticleQuery listQuery() {
        CmsArticleQuery query = new CmsArticleQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}