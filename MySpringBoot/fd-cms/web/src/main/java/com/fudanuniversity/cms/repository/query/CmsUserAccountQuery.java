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
public class CmsUserAccountQuery extends BaseQuery {

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
    private Long gtId;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private List<Long> idList;

    /**
     * 字段备注:学号 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:是
     */
    private String stuId;

    /**
     * 字段备注:盐 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:不是
     */
    private String salt;

    /**
     * 字段备注:密码 <p>
     * 数据库字段长度:(64,0) <p>
     * 索引字段:不是
     */
    private String password;

    /**
     * 字段备注:小于等于创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Date eltCreateTime;

    /**
     * 字段备注:大于等于创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Date egtCreateTime;

    /**
     * 字段备注:小于等于更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Date eltModifyTime;

    /**
     * 字段备注:大于等于更新时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Date egtModifyTime;

    public static CmsUserAccountQuery singletonQuery() {
        CmsUserAccountQuery query = new CmsUserAccountQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsUserAccountQuery listQuery() {
        CmsUserAccountQuery query = new CmsUserAccountQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}