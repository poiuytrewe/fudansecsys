package com.fudanuniversity.cms.commons.model.query;

/**
 * Created by Xinyue.Tang at 2020-06-24 15:12:45
 */
public enum SortMode {

    ASC("ASC"), DESC("DESC");

    private final String mode;

    SortMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}