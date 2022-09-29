package ${packagePrefix}.business.service;

import ${packagePrefix}.repository.entity.${classSimpleName};
import ${packagePrefix}.repository.query.${classSimpleName}Query;
import ${packagePrefix}.business.service.${classSimpleName}Service;
import ${packagePrefix}.test.unit.BaseTest;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ${classSimpleName}Test 测试类
 * <p>
 * Created by ${user} at ${datetime}
 */
public class ${classSimpleName}Test extends BaseTest {

    @Autowired
    private ${classSimpleName}Service ${classVariableName}Service;

    /**
    * 测试保存
    */
    @Test
    public void testInsert() throws Throwable {

        ${classSimpleName} po = new ${classSimpleName}();
        <#list columnList as column>
        <#if (column.allowEmpty == false) && (column.columnProperty != "createTime") >
        <#--<#if (column.javaType == "String")>-->
//        po.set${column.columnProperty?cap_first}("");
        <#--<#else>-->
        <#--Assert.notNull(${classVariableName}.get${column.columnProperty?cap_first}(), "${column.columnComment}不能为空");-->
        <#--</#if>-->
        </#if>
        </#list>

        System.out.println(" insert count: "+${classVariableName}Service.insert(po));

    }

    /**
    * 测试查询总量
    */
    @Test
    public void testQueryCountByParam() {

        ${classSimpleName}Query ${classVariableName}Query = new ${classSimpleName}Query();

        System.out.println(" queryCountByParam param "+ JSON.toJSONString(${classVariableName}Query) +
        "   count size :" + ${classVariableName}Service.queryCountByParam(${classVariableName}Query));

    }

    /**
    * 测试查询list
    */
    @Test
    public void testQueryListByParam() {

        ${classSimpleName}Query ${classVariableName}Query = new ${classSimpleName}Query();

        System.out.println(" queryListByParam param "+ JSON.toJSONString(${classVariableName}Query) +
        "   result size :" + ${classVariableName}Service.queryListByParam(${classVariableName}Query).size()  +
        "   result :" + JSON.toJSONString(${classVariableName}Service.queryListByParam(${classVariableName}Query)));

    }

}