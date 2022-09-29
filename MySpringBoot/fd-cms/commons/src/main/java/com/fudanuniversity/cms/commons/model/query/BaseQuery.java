package com.fudanuniversity.cms.commons.model.query;

import com.fudanuniversity.cms.commons.model.paging.Paging;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Xinyue.Tang at 2020-06-24 15:12:45
 */
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 最大条数
     */
    public static final int MAX_ROWS = 8000;

    /**
     * 最小条数
     */
    public static final int MIN_ROWS = 1;

    /**
     * 用于游标分页，指滑动分页时上次分页的最后一条记录标识
     */
    private Object cursor;

    /**
     * 记录开始位置
     */
    private Integer offset;

    /**
     * 记录行数
     */
    private Integer limit;

    /**
     * 排序
     */
    private List<SortColumn> sorts;

    public BaseQuery() {
        this.offset = 0;
        this.limit = MAX_ROWS;
    }

    public BaseQuery(List<SortColumn> sorts, Integer offset, Integer limit) {
        this.sorts = sorts;
        this.offset = offset;
        this.limit = limit;
    }

    public BaseQuery(int pageNo, int pageSize) {
        this.offset = (pageNo - 1) * pageSize;
        this.limit = pageSize;
    }

    public List<SortColumn> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortColumn> sorts) {
        this.sorts = sorts;
    }

    public void setSorts(SortColumn... sorts) {
        this.sorts = Arrays.asList(sorts);
    }

    public void setSorts(SortColumn sort) {
        if (null == sort) {
            return;
        }
        this.sorts = Collections.singletonList(sort);
    }

    public Object getCursor() {
        return cursor;
    }

    public void setCursor(Object cursor) {
        this.cursor = cursor;
    }

    public Integer getOffset() {
        return offset == null ? 0 : offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit == null ? 0 : limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public static int getMinRows() {
        return MIN_ROWS;
    }

    public static int getMaxRows() {
        return MAX_ROWS;
    }

    public void setPaging(int pageNo, int pageSize) {
        this.offset = (pageNo - 1) * pageSize;
        this.limit = pageSize;
    }

    public void setPaging(Paging paging) {
        this.offset = paging.getOffset();
        this.limit = paging.getLimit();
    }

    public void validateBaseArgument() {
        if (null == this.getOffset()) {
            throw new IllegalArgumentException("查询偏移量不能为空");
        }

        if (null == this.getLimit()) {
            throw new IllegalArgumentException("查询记录数量不能为空");
        }

        if (this.getOffset() < 0) {
            throw new IllegalArgumentException("查询偏移量必须大于等于0");
        }

        if (this.getLimit() < MIN_ROWS) {
            throw new IllegalArgumentException("查询记录数量必须大于" + MIN_ROWS);
        }

        if (this.getLimit() > MAX_ROWS) {
            throw new IllegalArgumentException("查询记录数量不能超过" + MAX_ROWS);
        }
    }
}
