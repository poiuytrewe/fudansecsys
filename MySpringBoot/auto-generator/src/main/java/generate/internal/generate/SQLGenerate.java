package generate.internal.generate;

import generate.internal.model.ClassInfo;
import generate.internal.model.MetaDataInfo;
import generate.utils.SyntaxUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SQLGenerate {

    /**
     * 换行
     */
    private static final String CHANGE_LINE = "\r\n";

    private static final String TAB = "\t";

    public static void generateSql(MetaDataInfo metaDataInfo, ClassInfo classInfo) throws IOException {
        /**
         * -- auto Generated on 2016-12-19 19:54:30
         -- DROP TABLE IF EXISTS `res_condition_group`;
         CREATE TABLE res_condition_group(
         `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
         `name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '条件组名称',
         `group_switch` INTEGER(12) NOT NULL DEFAULT -1 COMMENT '启用状态 0:关闭 1:启用 ',
         `create_time` DATETIME NOT NULL COMMENT 'createTime',
         `modify_time` DATETIME NOT NULL COMMENT '修改时间',
         `operator` VARCHAR(50) NOT NULL COMMENT '长度限制[0,32] 操作者',
         PRIMARY KEY (`id`)
         )ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'res_condition_group';

         */
        File resultDir = new File(classInfo.getSaveDirPath());
        if (!resultDir.exists()) {
            resultDir.mkdirs();
        }

        File sqlDir = new File(classInfo.getSaveDirPath(), "sql");
        if (!sqlDir.exists()) {
            sqlDir.mkdirs();
        }
        String simpleName = classInfo.getClassSimpleName();

        File simpleNameSql = new File(sqlDir, simpleName.concat(".sql"));
        if (simpleNameSql.exists()){
            simpleNameSql.delete();
        }


        FileWriter writer = new FileWriter(simpleNameSql, true);
        String tableName = SyntaxUtils.camel2Underline(simpleName);
        writer.write("//-- DROP TABLE IF EXISTS `".concat(tableName).concat("`; ").concat(CHANGE_LINE));
        writer.write("CREATE TABLE `".concat(tableName).concat("`( ").concat(CHANGE_LINE));
        StringBuilder sql = new StringBuilder();
        String[] colNames = metaDataInfo.getColNames();
        String[] colTypes = metaDataInfo.getColTypes();
        int index = 0;
        for (String colName : colNames) {
            String defaultJdbcType = colTypes[index++];
            //BIGINT(20)
            String notNull = "NOT NULL";
            String increment = "";
            if ("id".equals(colName)){
                increment = "AUTO_INCREMENT";
            }

            if (sql.length() != 0){
                sql.append(",").append(CHANGE_LINE);
            }

            sql.append("\t").append("`").append(colName).append("`").append(" ")
                    .append(defaultJdbcType).append(" ")
                    .append(notNull).append(" ").append(increment).append(" ")
                    .append(" COMMENT ").append(" '' ");
        }

        sql.append(",").append(CHANGE_LINE);
        sql.append(TAB).append("PRIMARY KEY (`id`)").append(CHANGE_LINE);
        sql.append(")ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '").append(tableName).append("';").append(CHANGE_LINE);
        writer.write(sql.toString());
        writer.flush();
        writer.close();
    }

}
