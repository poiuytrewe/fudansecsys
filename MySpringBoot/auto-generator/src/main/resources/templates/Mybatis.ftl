<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${packagePrefix}.repository.mapper.${classSimpleName}Mapper">

    <!-- 返回结果集 -->
    <resultMap id="${classSimpleName}Map" type="${packagePrefix}.repository.entity.${classSimpleName}">
    <#list columnList as column>
        <result column="${column.columnName}" property="${column.columnProperty}"/>
    </#list>
    </resultMap>

    <!-- 字段列表信息 -->
    <sql id="_field_list">
        <#list columnList as column><#if (column_has_next)>`${column.columnName}`, <#else> `${column.columnName}`</#if></#list>
    </sql>

    <!-- 值列表信息 -->
    <sql id="_value_list">
        <#list columnList as column><#if (column_has_next)><#noparse>#{</#noparse>${column.columnProperty}<#noparse>}</#noparse>, <#else> <#noparse>#{</#noparse>${column.columnProperty}<#noparse>}</#noparse></#if></#list>
    </sql>

    <!-- 普通条件查询 -->
    <sql id="_common_where">
    <#list columnList as column>
    <#if (column.columnProperty=='createTime') || (column.columnProperty=='modifyTime')>
        <if test="elt${column.columnProperty?cap_first} != null">
            <![CDATA[
            AND `${column.columnName}` <= <#noparse>#{</#noparse>elt${column.columnProperty?cap_first}<#noparse>}</#noparse>
            ]]>
        </if>
        <if test="egt${column.columnProperty?cap_first} != null">
            <![CDATA[
            AND `${column.columnName}` >= <#noparse>#{</#noparse>egt${column.columnProperty?cap_first}<#noparse>}</#noparse>
            ]]>
        </if>
    <#else>
        <if test="${column.columnProperty} != null">
            AND `${column.columnName}` = <#noparse>#{</#noparse>${column.columnProperty}<#noparse>}</#noparse>
        </if>
    <#if (column.columnProperty == primaryColumnClass.columnName)>
        <if test="${column.columnProperty}List != null and ${column.columnProperty}List.size() > 0">
            AND `${column.columnName}` IN
            <foreach collection="${column.columnProperty}List" item="${column.columnProperty}"
                     open="( " separator=" , " close=" )">
                <#noparse>#{</#noparse>${column.columnProperty}<#noparse>}</#noparse>
            </foreach>
        </if>
    </#if>
    </#if>
    </#list>
    </sql>

    <!-- 通用排序 -->
    <sql id="_common_sorts">
        <if test="sorts != null">
            ORDER BY
            <foreach collection="sorts" item="item" separator=",">
                ${r"`${item.columnName}`"} ${r"${item.sortMode.mode}"}
            </foreach>
        </if>
    </sql>

    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO
            `${tableName}`(<#list columnList as column><#if (column.columnProperty != primaryColumnClass.columnName)>`${column.columnName}`<#if (column_has_next)>, </#if></#if></#list>)
        VALUES
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list columnList as column><#if (column.columnProperty != primaryColumnClass.columnName)><#noparse>#{</#noparse>${column.columnProperty}<#noparse>}</#noparse><#if (column_has_next)>, </#if></#if></#list>
        </trim>
    </insert>

    <insert id="bulkUpsert" parameterType="list">
        INSERT INTO
            `${tableName}`(<#list columnList as column><#if (column.columnProperty != primaryColumnClass.columnName)>`${column.columnName}`<#if (column_has_next)>, </#if></#if></#list>)
        VALUES
        <foreach collection="list" item="data" separator=",">
            (<#list columnList as column><#if (column.columnProperty != primaryColumnClass.columnName)><#noparse>#{data.</#noparse>${column.columnProperty}<#noparse>}</#noparse><#if (column_has_next)>, </#if></#if></#list>)
        </foreach>
        ON DUPLICATE KEY UPDATE
<#list columnList as column><#if (column.columnProperty != primaryColumnClass.columnName)><#if (column.columnProperty == 'createTime')>       `${column.columnName}` = `${column.columnName}`<#else>       `${column.columnName}` = VALUES(`${column.columnName}`)</#if><#if (column_has_next)>, </#if>
        </#if></#list>
    </insert>

    <update id="updateById">
        UPDATE `${tableName}`
        <trim prefix="SET" suffixOverrides=",">
        <#list columnList as column>
            <#if (column.columnProperty != primaryColumnClass.columnName)>
            <if test="${column.columnProperty} != null">
                `${column.columnName}` = <#noparse>#{</#noparse>${column.columnProperty}<#noparse>},</#noparse>
            </if>
            </#if>
        </#list>
        </trim>
        <#noparse>WHERE </#noparse>`${primaryColumnClass.columnName}`<#noparse> = #{</#noparse>${primaryColumnClass.columnProperty}<#noparse>}</#noparse>
        LIMIT 1
    </update>

    <delete id="deleteById">
        <![CDATA[
        DELETE FROM `${tableName}` <#noparse>WHERE </#noparse>`${primaryColumnClass.columnName}`<#noparse> = #{</#noparse>${primaryColumnClass.columnProperty}<#noparse>}</#noparse> LIMIT 1
        ]]>
    </delete>

    <select id="selectListByParam" parameterType="map" resultMap="${classSimpleName}Map">
        SELECT
        <include refid="_field_list"/>
        FROM `${tableName}`
        <trim prefix="WHERE" prefixOverrides="AND" suffix="">
            <include refid="_common_where"/>
        </trim>
        <include refid="_common_sorts"/>
        <#noparse>LIMIT #{offset}, #{limit}</#noparse>
    </select>

    <select id="selectCountByParam" parameterType="map" resultType="java.lang.Long">
        SELECT COUNT(*)
        FROM `${tableName}`
        <trim prefix="WHERE" prefixOverrides="AND" suffix="">
            <include refid="_common_where"/>
        </trim>
    </select>
</mapper>