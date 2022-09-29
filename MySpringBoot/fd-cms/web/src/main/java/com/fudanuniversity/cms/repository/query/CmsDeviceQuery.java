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
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsDeviceQuery extends BaseQuery {//已改

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
     * 字段备注:设备类型 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Long type;

    /**
     * 字段备注:设备型号 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String model;

    /**
     * 字段备注:设备名称 <p>
     * 数据库字段长度:(256,0) <p>
     * 索引字段:不是
     */
    private String name;

    /**
     * 字段备注:负责人姓名 <p>
     * 数据库字段长度:(32,0) <p>
     * 索引字段:不是
     */
    private String principal;

    /**
     * 字段备注:库存 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:不是
     */

    private  String borrow;

    private Integer deleted;

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

    public static CmsDeviceQuery singletonQuery() {
        CmsDeviceQuery query = new CmsDeviceQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsDeviceQuery listQuery() {
        CmsDeviceQuery query = new CmsDeviceQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}