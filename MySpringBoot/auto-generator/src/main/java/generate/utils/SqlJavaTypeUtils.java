package generate.utils;

import java.util.Locale;

public class SqlJavaTypeUtils {

    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    public static String sqlType2JavaType(String sqlType) {

        sqlType = sqlType.toLowerCase(Locale.ENGLISH);
        if (sqlType.startsWith("bit")) {
            return "Integer";
        } else if (sqlType.startsWith("tinyint")) {
            return "Integer";//Byte
        } else if (sqlType.startsWith("smallint")) {
            return "Integer";//Short
        } else if (sqlType.startsWith("int")) {
            return "Integer";
        } else if (sqlType.startsWith("bigint")) {
            return "Long";
        } else if (sqlType.startsWith("float")) {
            return "Float";
        } else if (sqlType.startsWith("double")) {
            return "Double";
        } else if (sqlType.startsWith("decimal") || sqlType.startsWith("numeric")
                || sqlType.startsWith("real") || sqlType.startsWith("money")
                || sqlType.startsWith("smallmoney")) {
            return "BigDecimal";
        } else if (sqlType.startsWith("varchar") || sqlType.startsWith("char")
                || sqlType.startsWith("nvarchar") || sqlType.startsWith("nchar")
                || sqlType.startsWith("text")
                || sqlType.startsWith("mediumtext")
                || sqlType.startsWith("longtext")) {
            return "String";
        } else if (sqlType.startsWith("blob")
                || sqlType.startsWith("longblob")) {
            return "byte[]";
        } else if (sqlType.startsWith("image")) {
            return "Blod";
        } else if (sqlType.startsWith("date")) {
            return "Date";
        } else if (sqlType.startsWith("year")) {
            return "Integer";
        } else if (sqlType.startsWith("datetime")) {
            return "Date";
        } else if (sqlType.startsWith("timestamp")) {
            return "Date";
        }

        return null;
    }

}
