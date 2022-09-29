package com.fudanuniversity.cms.repository.query;

import com.fudanuniversity.cms.commons.model.query.BaseQuery;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class CmsProcessTinyCategoryQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private Integer studyType;

    private String processCategory;

    public static CmsProcessTinyCategoryQuery singletonQuery() {
        CmsProcessTinyCategoryQuery query=new CmsProcessTinyCategoryQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsProcessTinyCategoryQuery listQuery() {
        CmsProcessTinyCategoryQuery query=new CmsProcessTinyCategoryQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}
