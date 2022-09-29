package com.fudanuniversity.cms.business.vo.article;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 文章
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsArticleQueryVo {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date date;

    private String title;

    private String content;

}