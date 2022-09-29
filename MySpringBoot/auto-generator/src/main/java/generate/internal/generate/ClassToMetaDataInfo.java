package generate.internal.generate;

import generate.internal.model.MetaDataInfo;
import generate.utils.SyntaxUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassToMetaDataInfo {



    /**
     * @param clazz
     * @return
     */
    public MetaDataInfo classToMetaDataInfo(Class<?> clazz){
        MetaDataInfo metaDataInfo = new MetaDataInfo();
        String tableName = SyntaxUtils.camel2Underline(clazz.getSimpleName());
        metaDataInfo.setTableName(tableName);
        Field[] fields = clazz.getDeclaredFields();
        int size = fields.length;
        List<String> colNameList = new ArrayList<String>(size);
        List<String> sqlTypeList = new ArrayList<String>(size);
        Map<String,String> columnCommentMap = new HashMap<String, String>();
        for (Field field:fields){
            String name = field.getName();
            String colomn = SyntaxUtils.camel2Underline(name);
            columnCommentMap.put(colomn, "无");
            colNameList.add(colomn);
            Class type = field.getType();
            if (type.getName().equalsIgnoreCase("boolean")){
                sqlTypeList.add("tinyint(1)");
            } else if(type.getName().equalsIgnoreCase("Integer")){
                sqlTypeList.add("int(11)");
            } else if(type.getName().equalsIgnoreCase("Long")){
                sqlTypeList.add("bigint(20)");
            } else if(type.getName().equalsIgnoreCase("Float")){
                sqlTypeList.add("int(11)");
            } else if(type.getName().equalsIgnoreCase("BigDecimal")){
                sqlTypeList.add("decimal(22,6)");
            } else if(type.getName().equalsIgnoreCase("String")){
                sqlTypeList.add("varchar(32)");
            } else if(type.getName().equalsIgnoreCase("Date")){
                sqlTypeList.add("datetime");
            } else if(type.getName().equalsIgnoreCase("Blod")){
                sqlTypeList.add("image");
            } else {
                sqlTypeList.add("未知");
            }


        }
        metaDataInfo.setColTypes(sqlTypeList.toArray(new String[size]));
        metaDataInfo.setColNames(colNameList.toArray(new String[size]));
        metaDataInfo.setColumnCommentMap(columnCommentMap);
        return metaDataInfo;
    }
}
