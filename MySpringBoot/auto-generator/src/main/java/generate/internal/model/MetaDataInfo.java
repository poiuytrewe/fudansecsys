package generate.internal.model;

import generate.template.ColumnClass;
import generate.template.UkClass;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class MetaDataInfo {

    private ColumnClass primaryColumnClass;

    private String tableName;

    private String tableComment;

    private String[] colNames;

    private String[] colTypes;

    private int[] colSizes;

    private Map<String, ColumnClass> columnClassMap;

    private List<ColumnClass> columnList;

    private Map<String,String> columnCommentMap;

    private List<ColumnClass> indexColumnList;

    private List<UkClass> ukColumnList = new ArrayList<>();

    private List<ColumnClass> notEmptyColumnList;

    public MetaDataInfo() {
    }

    public MetaDataInfo(String tableName, String[] colNames, String[] colTypes, int[] colSizes) {
        this.tableName = tableName;
        this.colNames = colNames;
        this.colTypes = colTypes;
        this.colSizes = colSizes;
    }

}
