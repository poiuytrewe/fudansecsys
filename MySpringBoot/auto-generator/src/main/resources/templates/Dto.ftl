package ${packagePrefix}.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
<#list columnList as column>
	<#if (column.javaType == "BigDecimal")>
    import java.math.BigDecimal;
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
public class ${classSimpleName}Dto implements Serializable {

	private static final long serialVersionUID = 1L;
	<#list columnList as column>

    /**
    *
    * ${column.columnComment}
    */
	private ${column.javaType} ${column.columnProperty};
	</#list>
}

