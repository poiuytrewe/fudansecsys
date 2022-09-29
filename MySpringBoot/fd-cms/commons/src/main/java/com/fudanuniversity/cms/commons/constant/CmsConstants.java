package com.fudanuniversity.cms.commons.constant;

/**
 * Created by Xinyue.Tang at 2021-04-15 10:02:59
 */
public final class CmsConstants {

    /**
     * 用户登录后写入sessionAttribute的name
     */
    public final static String LoginSessionUserKey = "user";

    /**
     * 表id列
     */
    public final static String IdColumn = "id";
    /**
     * 表删除状态列
     */
    public final static String DeletedColumn = "deleted";
    /**
     * 表创建时间列
     */
    public final static String CreatedTimeColumn = "create_time";
    /**
     * 表更新时间列
     */
    public final static String ModifiedTimeColumn = "modify_time";

    private CmsConstants() {
    }
}
