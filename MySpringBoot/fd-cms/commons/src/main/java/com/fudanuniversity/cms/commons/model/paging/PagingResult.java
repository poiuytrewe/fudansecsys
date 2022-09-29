package com.fudanuniversity.cms.commons.model.paging;

import com.fudanuniversity.cms.commons.model.query.BaseQuery;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Xinyue.Tang at 2020-06-24 15:04:45
 */
public class PagingResult<T> extends Paging {
    private static final long serialVersionUID = 1L;

    /**
     * 结果集.
     */
    private List<T> rows;

    /**
     * 结果集.
     *
     * @return the results
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 保存结果.
     *
     * @param rows the results to set
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    /**
     * rows设置经过function转换过的list
     */
    public <S> void setRows(List<S> rows, Function<S, T> function) {
        if (rows != null && !rows.isEmpty()) {
            this.rows = rows.stream().map(function).collect(Collectors.toList());
        }
    }

    public static <T> PagingResult<T> create(BaseQuery query) {
        PagingResult<T> pageResult = new PagingResult<>();
        pageResult.setCursor(query.getCursor());
        pageResult.setOffset(query.getOffset());
        pageResult.setLimit(query.getLimit());
        return pageResult;
    }

    public static <T> PagingResult<T> create(Paging paging) {
        PagingResult<T> pageResult = new PagingResult<>();
        pageResult.setCursor(paging.getCursor());
        pageResult.setOffset(paging.getOffset());
        pageResult.setLimit(paging.getLimit());
        return pageResult;
    }

    public static <T> PagingResult<T> empty(Paging paging) {
        PagingResult<T> pageResult = new PagingResult<>();
        pageResult.setCursor(paging.getCursor());
        pageResult.setOffset(paging.getOffset());
        pageResult.setLimit(paging.getLimit());
        pageResult.setTotal(0L);
        pageResult.setRows(Collections.emptyList());
        return pageResult;
    }

    /**
     * PagingResult里数据转换
     */
    public static <S, T> PagingResult<T> convert(
            PagingResult<S> pagingResult, Function<S, T> function) {
        PagingResult<T> pageResult = new PagingResult<>();
        pageResult.setCursor(pagingResult.getCursor());
        pageResult.setOffset(pagingResult.getOffset());
        pageResult.setLimit(pagingResult.getLimit());
        pageResult.setTotal(pagingResult.getTotal());
        List<S> rows = pagingResult.getRows();
        if (rows != null && !rows.isEmpty()) {
            List<T> list = rows.stream().map(function).collect(Collectors.toList());
            pageResult.setRows(list);
        }
        return pageResult;
    }

    @Override
    public String toString() {
        return "PagingResult{" +
                "rows=" + rows +
                "} " + super.toString();
    }
}
