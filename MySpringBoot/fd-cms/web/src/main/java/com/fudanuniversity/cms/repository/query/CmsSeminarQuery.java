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
 * Created by Xinyue.Tang at 2021-05-04 20:03:38
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsSeminarQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private Long id;

    private Integer groupId;

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
     * 字段备注:演讲用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private String speakerName;

    private String speakerStuId;

    private String fileName;


    /**
     * 字段备注:演讲时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:不是
     */
    private Date date;

    /**
     * 字段备注:演讲主题 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String theme;

    /**
     * 字段备注:演讲资源保存链接地址 <p>
     * 数据库字段长度:(256,0) <p>
     * 索引字段:不是
     */
    private String link;

    /**
     * 字段备注:介绍与描述 <p>
     * 数据库字段长度:(536870911,0) <p>
     * 索引字段:不是
     */
    private String description;

    /**
     * 字段备注:小于等于创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private Date eltCreateTime;

    /**
     * 字段备注:大于等于创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
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

    private Integer isFile;

    private Date modifyTime;

    public static CmsSeminarQuery singletonQuery() {
        CmsSeminarQuery query = new CmsSeminarQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsSeminarQuery listQuery() {
        CmsSeminarQuery query = new CmsSeminarQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}