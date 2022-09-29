package generate.template;

import java.util.List;

public class UkClass {

    private String tableName;

    private String camelTableName;

    private String keyName;

    private String ukName;

    private List<ColumnClass> ukList;

    public UkClass() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUkName() {
        return ukName;
    }

    public void setUkName(String ukName) {
        this.ukName = ukName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getCamelTableName() {
        return camelTableName;
    }

    public void setCamelTableName(String camelTableName) {
        this.camelTableName = camelTableName;
    }

    public List<ColumnClass> getUkList() {
        return ukList;
    }

    public void setUkList(List<ColumnClass> ukList) {
        this.ukList = ukList;
    }
}
