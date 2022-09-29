package com.fudanuniversity.cms.commons.model.paging;

import java.io.Serializable;

/**
 * 用于分页的参数
 * <p>
 * Created by Xinyue.Tang at 2020-06-24 15:03:11
 */
public class Paging implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DefaultPageSize = 20;

    /**
     * 用于游标分页，指滑动分页时上次分页的最后一条记录标识
     */
    private Object cursor;

    /**
     * 起始游标
     */
    private Integer offset;

    /**
     * 每页显示的记录数.
     */
    private Integer limit;

    /**
     * 总记录数.
     */
    private Long total;

    public Paging() {
        this(0, DefaultPageSize);
    }

    public Paging(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Paging(Integer offset, Integer limit, String cursor) {
        this.offset = offset;
        this.limit = limit;
        this.cursor = cursor;
    }

    public static Paging create(Integer offset, Integer limit) {
        return new Paging(offset, limit);
    }

    public static Paging create(Integer offset, Integer limit, String cursor) {
        return new Paging(offset, limit, cursor);
    }

    public Integer getPageNo() {
        if (offset == null || limit == null || limit == 0) {//未传入分页信息等情况
            return 1;
        }

        return offset / limit + 1;
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "cursor='" + cursor + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                ", total=" + total +
                '}';
    }
}
