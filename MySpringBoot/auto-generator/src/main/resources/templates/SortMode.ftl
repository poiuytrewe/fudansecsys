package com.fudanuniversity.cms.commons.model.query;

import java.util.HashMap;
import java.util.Map;

/**
 * 排序
 * <p>
 * Created by ${user} at ${datetime}
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

    private final static Map<String, SortMode> SortModeMap;

    static {
        SortModeMap = new HashMap<String, SortMode>();
        SortMode[] sortModes = values();
        for (SortMode sortMode : sortModes) {
            if (SortModeMap.put(sortMode.getMode(), sortMode) != null) {
                throw new IllegalStateException("Duplicate mode error: "
                        + sortMode + "[" + sortMode.getMode() + "]");
            }
        }
    }

    public static SortMode sortModeOf(String mode) {
        return SortModeMap.get(mode);
    }
}

