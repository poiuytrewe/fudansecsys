package com.fudanuniversity.cms.commons.model.query;

import java.io.Serializable;

/**
 * 排序对象
 * <p>
 * Created by ${user} at ${datetime}
 */
public class SortColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    // 排序字段
    private String columnName;

    // 排序:asc或desc
    private SortMode sortMode;

    public SortColumn(String columnName, SortMode sortMode) {
        this.columnName = columnName;
        this.sortMode = sortMode;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public SortMode getSortMode() {
        return sortMode;
    }

    public void setSortMode(SortMode sortMode) {
        this.sortMode = sortMode;
    }
}
