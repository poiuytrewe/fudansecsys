package ${packagePrefix}.repository.query;

import lombok.ToString;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
<#list columnList as column>
<#if (column.javaType == "BigDecimal")>
import java.math.BigDecimal;]
<#break>
</#if>
</#list>

/**
 * ${tableComment}
 * <p>
 * Created by ${user} at ${datetime}
 */
@Data
@NoArgsConstructor
@ToString
public class ${classSimpleName}QueryDto {

    private static final long serialVersionUID = 1L;
    <#list columnList as column>

    <#if (column.columnProperty=='createTime') || (column.columnProperty=='modifyTime')>
    /**
     * 小于等于${column.columnComment}
     */
    private ${column.javaType} elt${column.columnProperty?cap_first};

    /**
     * 大于等于${column.columnComment}
     */
    private ${column.javaType} egt${column.columnProperty?cap_first};
    <#else>
    /**
     * ${column.columnComment}
     */
    private ${column.javaType} ${column.columnProperty};
    </#if>
    </#list>
}