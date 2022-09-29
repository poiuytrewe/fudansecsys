package com.fudanuniversity.cms.commons.model.query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
* 基本查询对象
* <p>
* Created by ${user} at ${datetime}
*/
public class BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 最大条数
     */
    private static final int MAX_ROWS = 2000;

    /**
     * 最小条数
     */
    private static final int MIN_ROWS = 1;

    /**
     * 排序
     */
    private List<SortColumn> sorts;

    /**
     * 记录开始位置
     */
    private Integer offset;

    /**
     * 记录行数
     */
    private Integer rows;

    public BaseQuery() {
        this.offset = 0;
        this.rows = MAX_ROWS;
    }

    public BaseQuery(List<SortColumn> sorts, Integer offset, Integer rows) {
        this.sorts = sorts;
        this.offset = offset;
        this.rows = rows;
    }

    public BaseQuery(int pageNo, int pageSize) {
        this.offset = (pageNo - 1) * pageSize;
        this.rows = pageSize;
    }

    public List<SortColumn> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortColumn> sorts) {
        this.sorts = sorts;
    }

    public void setSorts(SortColumn sort) {
        if (null == sort) {
            return;
        }
        this.sorts = Collections.singletonList(sort);
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public static int getMinRows() {
        return MIN_ROWS;
    }

    public static int getMaxRows() {
        return MAX_ROWS;
    }

    public void setPage(int pageNo, int pageSize) {
        this.offset = (pageNo - 1) * pageSize;
        this.rows = pageSize;
    }

    public void validateBaseArgument() {
        if (null == this.getOffset()) {
            throw new IllegalArgumentException("查询偏移量不能为空");
        }

        if (null == this.getRows()) {
            throw new IllegalArgumentException("查询记录数量不能为空");
        }

        if (this.getOffset() < 0) {
            throw new IllegalArgumentException("查询偏移量必须大于等于0");
        }

        if (this.getRows() < MIN_ROWS) {
            throw new IllegalArgumentException("查询记录数量必须大于" + MIN_ROWS);
        }

        if (this.getRows() > MAX_ROWS) {
            throw new IllegalArgumentException("查询记录数量不能超过" + MAX_ROWS);
        }
    }
}