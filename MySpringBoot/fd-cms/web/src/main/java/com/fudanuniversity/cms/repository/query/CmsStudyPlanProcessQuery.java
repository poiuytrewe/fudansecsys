package com.fudanuniversity.cms.repository.query;

import com.fudanuniversity.cms.commons.model.query.BaseQuery;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsStudyPlanProcessQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String stuName;

    private String stuId;

    private Integer type;

    private Integer missionStatus;

    private String category;

    private String tinyCategory;

    private Integer isDelay;

    private Integer year;

    private Integer month;

    private Integer wishYear;

    private Integer wishMonth;

    private Integer alert;

    private Date createTime;

    public static CmsStudyPlanProcessQuery singletonQuery(){
        CmsStudyPlanProcessQuery query=new CmsStudyPlanProcessQuery();
        query.setLimit(1);
        return query;
    }

   public static CmsStudyPlanProcessQuery listQuery(){
        CmsStudyPlanProcessQuery query=new CmsStudyPlanProcessQuery();
        query.setLimit(MAX_ROWS);
        return query;
   }
}
