package ${packagePrefix}.repository.query;

import com.fudanuniversity.cms.commons.model.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;
<#list columnList as column>
<#if (column.javaType == "BigDecimal")>
import java.math.BigDecimal;]
<#break>
</#if>
</#list>

/**
 * 查询对象
 * <p>
 * Created by ${user} at ${datetime}
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class ${classSimpleName}Query extends BaseQuery {

    private static final long serialVersionUID = 1L;
    <#list columnList as column>
    <#if (column.columnProperty=='createTime') || (column.columnProperty=='modifyTime')>
    /**
    * 字段备注:${column.columnComment} <p>
    * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
    * 索引字段:${column.index?string("是","不是")}
    */
    private ${column.javaType} ${column.columnProperty};

    /**
     * 字段备注:小于等于${column.columnComment} <p>
     * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
     * 索引字段:${column.index?string("是","不是")}
     */
    private ${column.javaType} elt${column.columnProperty?cap_first};

    /**
     * 字段备注:大于等于${column.columnComment} <p>
     * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
     * 索引字段:${column.index?string("是","不是")}
     */
    private ${column.javaType} egt${column.columnProperty?cap_first};
    <#else>

    /**
     * 字段备注:${column.columnComment} <p>
     * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
     * 索引字段:${column.index?string("是","不是")}
     */
    private ${column.javaType} ${column.columnProperty};
    <#if (column.columnProperty == primaryColumnClass.columnName)>

    /**
     * 字段备注:${column.columnComment} <p>
     * 数据库字段长度:(${column.columnPrecision},${column.columnScale}) <p>
     * 索引字段:${column.index?string("是","不是")}
     */
    private List<${column.javaType}> ${column.columnProperty}List;
    </#if>
    </#if>
    </#list>

    public void checkIndexCondition(){
        if (get${primaryColumnClass.columnProperty?cap_first}() == null
        <#list indexColumnList as column>       && get${column.columnProperty?cap_first}() == null<#if (column_has_next)>
        </#if></#list>) {
            throw new UnsupportedOperationException("请通过索引查询！");
        }
    }

    public static ${classSimpleName}Query singletonQuery() {
        ${classSimpleName}Query query = new ${classSimpleName}Query();
        query.setLimit(1);
        return query;
    }

    public static ${classSimpleName}Query listQuery() {
        ${classSimpleName}Query query = new ${classSimpleName}Query();
        query.setLimit(MAX_ROWS);
        return query;
    }
}