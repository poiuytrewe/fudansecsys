package generate.internal.model;

import lombok.Data;

@Data
public class ClassInfo {

    /**
     * 包前缀
     */
    private String packagePrefix;

    /**
     * 例如 user
     */
    private String systemModel;

    /**
     * 保存路径
     */
    private String saveDirPath;

    /**
     * 数据库表名（如果有分表则移除分表后缀）
     */
    private String logicTableName;

    /**
     * 类全名
     */
    private String className;

    /**
     * 类名
     */
    private String classSimpleName;

    /**
     * 类名（第一子母小写）
     */
    private String classVariableName;

    /**
     * 日期
     */
    private String datetime;

    /**
     * 当前系统用户
     */
    private String user;

    /**
     * 查询对象后缀名
     */
    private String querySuffix = "Query";

    /**
     * 是否生成唯一键相关查询方法和查询对象
     */
    private boolean supportUk = false;

}


