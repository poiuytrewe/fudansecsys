package ${packagePrefix}.repository.entity;

import java.util.Date;
import java.io.Serializable;
<#list ukColumn.ukList as column>
	<#if (column.javaType == "BigDecimal")>
import java.math.BigDecimal;
	</#if>
</#list>

/**
 * ${ukColumn.camelTableName}${ukColumn.ukName?cap_first}Query
 * <p>
 * Created by ${user} at ${datetime}
 */
public class ${ukColumn.camelTableName}${ukColumn.ukName?cap_first}Query implements Serializable {

	private static final long serialVersionUID = 1L;

	<#list ukColumn.ukList as column>

	/**
	 * 数据库字段长度:(${column.columnPrecision},${column.columnScale})
	 * 字段备注:${column.columnComment}
	 * 是否索引:${column.index?string('是', '不是')}
	 */
	private ${column.javaType} ${column.columnProperty};

	</#list>



	public ${ukColumn.camelTableName}${ukColumn.ukName?cap_first}Query(){
	}

	<#list ukColumn.ukList as column>

	public void set${column.columnProperty?cap_first}(${column.javaType} ${column.columnProperty}){
		this.${column.columnProperty} = ${column.columnProperty};
	}

	public ${column.javaType} get${column.columnProperty?cap_first}(){
		return ${column.columnProperty};
	}

	</#list>


}

