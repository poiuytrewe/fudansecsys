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
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsBulletinStateQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String stuId;

    private Integer isRead;

    public static CmsBulletinStateQuery singletonQuery() {
        CmsBulletinStateQuery query = new CmsBulletinStateQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsBulletinStateQuery listQuery() {
        CmsBulletinStateQuery query = new CmsBulletinStateQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}