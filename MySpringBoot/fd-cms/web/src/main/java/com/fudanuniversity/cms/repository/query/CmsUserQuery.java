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
 * Created by Xinyue.Tang at 2021-05-04 20:01:54
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsUserQuery extends BaseQuery { //groupId已改

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private String stuId;

    private Integer roleId;

    private Integer isSystemMng;
    private Integer isTalkMng;
    private Integer isLabMng;
    private Integer isPaperMng;
    private Integer isEducateMng;
    private Integer isDeviceMng;

    private List<Long> idList;

    private String name;

    private Integer  groupId;

    private String telephone;

    private String email;

    private String mentor;

    private String leader;

    private Integer studyType;

    private Integer keshuo;

    private Integer enrollYear;

    private Date enrollDate;

    private String papers;

    private String patents;

    private String services;

    private String projects;

    private Integer status;

    private Integer deleted;

    public static CmsUserQuery singletonQuery() {
        CmsUserQuery query = new CmsUserQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsUserQuery listQuery() {
        CmsUserQuery query = new CmsUserQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}