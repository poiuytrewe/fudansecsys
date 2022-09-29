package com.fudanuniversity.cms.repository.query;
import com.fudanuniversity.cms.commons.model.query.BaseQuery;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsSectionQuery extends BaseQuery { //感觉根本用不着
    private static final long serialVersionUID = 1L;

    private Long id;

    private Date date;

    private String stuId;

    private String name;

    private String topic;

    private Integer isFile;

    public static CmsSectionQuery singletonQuery() {
        CmsSectionQuery query = new CmsSectionQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsSectionQuery listQuery() {
        CmsSectionQuery query = new CmsSectionQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}
