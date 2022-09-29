package generate.internal.generate;

import generate.internal.model.ClassInfo;
import generate.internal.model.DBInfo;
import generate.internal.model.MetaDataInfo;
import generate.template.ColumnClass;
import generate.template.UkClass;
import generate.utils.SqlJavaTypeUtils;
import generate.utils.SyntaxUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.*;

public class MetaDataInfoGenerate {


    public MetaDataInfo getMetaDataInfo(Connection conn, DBInfo dBInfo, String tablename, ClassInfo classInfo) throws SQLException {
        Map<String, ColumnClass> columnClassMap = new HashMap<String, ColumnClass>();
        List<ColumnClass> columnClassList = new ArrayList<ColumnClass>();

        // 获取表字段信息
        MetaDataInfo metaDataInfo = genMetaDataInfo(conn, dBInfo, tablename, columnClassMap, columnClassList);

        // 获取字段注释
        getColumnCommentInfo(conn, tablename, columnClassMap, metaDataInfo);

        // 获取表索引信息
        getIndexInfo(conn, tablename, columnClassMap, metaDataInfo, classInfo);

        parse(classInfo, metaDataInfo);

        return metaDataInfo;
    }

    /**
     * 功能：生成实体类主体代码
     */
    private void parse(ClassInfo classInfo, MetaDataInfo metaDataInfo) {
        String packagePrefix = classInfo.getPackagePrefix();
        String tableName = metaDataInfo.getTableName();
        String logicTableName = SyntaxUtils.removeShardSuffix(tableName);
        classInfo.setLogicTableName(logicTableName);
        String classPackage = packagePrefix + ".entity";
        String classSimpleName = SyntaxUtils.underline2Camel(logicTableName, false);
        classInfo.setClassName(classPackage + "." + classSimpleName);
        classInfo.setClassSimpleName(classSimpleName);
        String classVariableName = SyntaxUtils.decapitalize(classInfo.getClassSimpleName());
        classInfo.setClassVariableName(classVariableName);
    }

    private void getIndexInfo(Connection conn, String tablename, Map<String, ColumnClass> columnClassMap, MetaDataInfo metaDataInfo, ClassInfo classInfo) throws SQLException {
        Statement stmt = conn.createStatement();
        LinkedHashMap<String, ColumnClass> indexColumnMap = new LinkedHashMap<String, ColumnClass>();
        HashMap<String, UkClass> ukMap = new HashMap<String, UkClass>();

        ResultSet rs = stmt.executeQuery("show index from " + tablename);
        while (rs.next()) {

            String keyName = rs.getString("Key_name");
            String columnName = rs.getString("Column_name");
            // 1代表是非唯一索引,0代表唯一索引
            String nonUnique = rs.getString("Non_unique");
            String seqInIndex = rs.getString("Seq_in_index");

            ColumnClass columnClass = columnClassMap.get(columnName);
            columnClass.setSeqInIndex(Integer.parseInt(seqInIndex));
            if ("PRIMARY".equalsIgnoreCase(keyName)) {
                columnClass.setIndex(true);
                metaDataInfo.setPrimaryColumnClass(columnClass);
                continue;
            }

            // 不是不是唯一索引，第一个都是索引，后面都不是当做索引字段
            if ("1".equals(seqInIndex)) {
                columnClass.setIndex(true);
                indexColumnMap.put(columnClass.getColumnName(), columnClass);

                // 唯一键
                if ("0".equals(nonUnique)) {
                    UkClass ukClass = new UkClass();
                    ukClass.setTableName(tablename);
                    ukClass.setCamelTableName(SyntaxUtils.underline2Camel(tablename, false));
                    ukClass.setKeyName(keyName);
                    ukClass.setUkName(SyntaxUtils.underline2Camel(keyName, true));
                    List<ColumnClass> columnClassList = new ArrayList<ColumnClass>();
                    ukClass.setUkList(columnClassList);
                    columnClassList.add(columnClass);
                    metaDataInfo.getUkColumnList().add(ukClass);
                    ukMap.put(keyName, ukClass);
                    continue;
                }
            }

            if ("0".equals(nonUnique) && !"1".equals(seqInIndex)) {
                UkClass ukClass = ukMap.get(keyName);
                if (null != ukClass) {
                    ukClass.getUkList().add(columnClass);
                }
            }


        }

        // 唯一键时，统计uk
        if (!classInfo.isSupportUk()) {
            metaDataInfo.setUkColumnList(new ArrayList<UkClass>());
        }

        List<ColumnClass> indexColumns = new ArrayList<ColumnClass>(indexColumnMap.values());
        metaDataInfo.setIndexColumnList(indexColumns);
        rs.close();
    }

    private ResultSet getColumnCommentInfo(Connection conn, String tablename, Map<String, ColumnClass> columnClassMap, MetaDataInfo metaDataInfo) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show full columns from " + tablename);
        Map<String, String> columnCommentMap = new HashMap<String, String>();
        while (rs.next()) {
            columnCommentMap.put(rs.getString("Field"), rs.getString("Comment"));
            ColumnClass columnClass = columnClassMap.get(rs.getString("Field"));
            columnClass.setColumnComment(rs.getString("Comment"));
            if ("NO".equals(rs.getString("Null")) && !"id".equals(rs.getString("Field"))) {
                columnClass.setAllowEmpty(false);
            }
        }

        metaDataInfo.setColumnCommentMap(columnCommentMap);
        rs.close();
        return rs;
    }

    private MetaDataInfo genMetaDataInfo(Connection conn, DBInfo dBInfo, String tableName,
                                         Map<String, ColumnClass> columnClassMap,
                                         List<ColumnClass> columnClassList) throws SQLException {
        //创建连接
        //查要生成实体类的表
        String sql = "select * from " + tableName + " limit 1";
        PreparedStatement pStemt = null;
        pStemt = conn.prepareStatement(sql);

        ResultSetMetaData rsmd = pStemt.getMetaData();
        int size = rsmd.getColumnCount();   //统计列
        String[] colNames = new String[size];// 列名
        String[] colTypes = new String[size];// 字段sql类型
        int[] colSizes = new int[size];// 列名大小数组

        MetaDataInfo metaDataInfo = new MetaDataInfo(tableName, colNames, colTypes, colSizes);
        for (int i = 0; i < size; i++) {
            colNames[i] = rsmd.getColumnName(i + 1);
            colTypes[i] = rsmd.getColumnTypeName(i + 1);
            ColumnClass columnClass = new ColumnClass();
            columnClass.setColumnName(colNames[i]);
            columnClass.setColumnType(colTypes[i]);
            columnClass.setColumnProperty(SyntaxUtils.underline2Camel(colNames[i], true));
            columnClass.setJavaType(SqlJavaTypeUtils.sqlType2JavaType(columnClass.getColumnType()));
            columnClass.setSeqInIndex(-1);

            columnClass.setIndex(false);
            columnClass.setColumnScale(rsmd.getScale(i + 1));
            columnClass.setColumnPrecision(rsmd.getPrecision(i + 1));
            columnClassList.add(columnClass);
            columnClassMap.put(colNames[i], columnClass);
        }

        metaDataInfo.setColumnList(columnClassList);
        metaDataInfo.setColumnClassMap(columnClassMap);
        metaDataInfo.setTableName(SyntaxUtils.removeShardSuffix(tableName));

        String tableComment = "";
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getTables(null, dBInfo.getDbName(), tableName, new String[]{"TABLE"});
        if (resultSet.next()) {
            tableComment = resultSet.getString("REMARKS");
        }
        metaDataInfo.setTableComment(StringUtils.defaultString(tableComment));

        return metaDataInfo;
    }


}
