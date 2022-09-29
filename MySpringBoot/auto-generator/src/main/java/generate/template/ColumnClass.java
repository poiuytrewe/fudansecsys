package generate.template;

import java.util.ArrayList;
import java.util.List;

public class ColumnClass {

    /**
     * 数据库字段名称
     **/
    private String columnName;
    /**
     * 数据库字段类型
     **/
    private String columnType;
    /**
     * java属性名
     **/
    private String columnProperty;

    /**
     * java类型
     */
    private String javaType;

    /**
     * 数据库字段注释
     **/
    private String columnComment;

    /**
     * 字段精度
     */
    private int columnScale;

    /**
     * 字段长度
     */
    private int columnPrecision;


    private boolean index;

    // 字段允许空
    private boolean allowEmpty= true;

    private int seqInIndex;

    private List<ColumnClass> ukList = new ArrayList<ColumnClass>();

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnProperty() {
        return columnProperty;
    }

    public void setColumnProperty(String columnProperty) {
        this.columnProperty = columnProperty;
    }

    public int getColumnScale() {
        return columnScale;
    }

    public void setColumnScale(int columnScale) {
        this.columnScale = columnScale;
    }

    public int getColumnPrecision() {
        return columnPrecision;
    }

    public void setColumnPrecision(int columnPrecision) {
        this.columnPrecision = columnPrecision;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public boolean isIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public boolean isAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public List<ColumnClass> getUkList() {
        return ukList;
    }

    public void setUkList(List<ColumnClass> ukList) {
        this.ukList = ukList;
    }

    public int getSeqInIndex() {
        return seqInIndex;
    }

    public void setSeqInIndex(int seqInIndex) {
        this.seqInIndex = seqInIndex;
    }
}
