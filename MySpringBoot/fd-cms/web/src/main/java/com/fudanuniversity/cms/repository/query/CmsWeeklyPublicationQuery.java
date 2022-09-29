package com.fudanuniversity.cms.repository.query;
import com.fudanuniversity.cms.commons.model.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsWeeklyPublicationQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String stuName;

    private String stuId;

    private String target;

    private String outlinedAchievements;

    private String achievements;

    private String plan;

    private String week;

    private Integer score;

    private String comment;

    private String tempComment;

    private Integer isRead;

    private Date date;

    public static CmsWeeklyPublicationQuery singletonQuery(){
        CmsWeeklyPublicationQuery query=new CmsWeeklyPublicationQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsWeeklyPublicationQuery listQuery(){
        CmsWeeklyPublicationQuery query=new CmsWeeklyPublicationQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}
