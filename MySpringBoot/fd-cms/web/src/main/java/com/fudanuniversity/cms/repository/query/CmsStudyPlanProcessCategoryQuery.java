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
public class CmsStudyPlanProcessCategoryQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private Integer studyType;

    private String processCategory;

    private String categoryMission;

    private Integer year;

    private Integer month;

    private Date createTime;

    public static CmsStudyPlanProcessCategoryQuery single(){
        CmsStudyPlanProcessCategoryQuery query=new CmsStudyPlanProcessCategoryQuery();
        query.setLimit(1);
        return  query;
    }

    public static CmsStudyPlanProcessCategoryQuery list(){
        CmsStudyPlanProcessCategoryQuery query=new CmsStudyPlanProcessCategoryQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}
