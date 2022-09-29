package com.fudanuniversity.cms.repository.query;

import com.fudanuniversity.cms.commons.model.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.checkerframework.checker.units.qual.C;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsStudyScoreQuery extends BaseQuery {
    private static final long serialVersionUID=1L;


    private Integer id;

    private Integer year;

    private Integer season;

    private String stuId;

    private String stuName;

    private String stuType;

    private Integer score;

    private String mentor;

    private String comment;

    private String teaComment;

    public static CmsStudyScoreQuery listQuery(){
        CmsStudyScoreQuery cmsStudyScoreQuery=new CmsStudyScoreQuery();
        cmsStudyScoreQuery.setLimit(MAX_ROWS);
        return  cmsStudyScoreQuery;
    }

    public static CmsStudyScoreQuery singleQuery(){
        CmsStudyScoreQuery cmsStudyScoreQuery=new CmsStudyScoreQuery();
        cmsStudyScoreQuery.setLimit(1);
        return cmsStudyScoreQuery;
    }
}
