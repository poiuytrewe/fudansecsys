package ${packagePrefix}.business.service.impl;

import com.fudanuniversity.cms.commons.json.GsonUtils;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import ${packagePrefix}.business.service.${classSimpleName}Service;
import ${packagePrefix}.repository.dao.${classSimpleName}Dao;
import ${packagePrefix}.repository.entity.${classSimpleName};
import ${packagePrefix}.repository.query.${classSimpleName}Query;
import ${packagePrefix}.dto.${classSimpleName}Dto;
import ${packagePrefix}.repository.query.${classSimpleName}QueryDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* ${classSimpleName}Service 实现类
* <p>
    * Created by ${user} at ${datetime}
    */
    @Service
    public class ${classSimpleName}ServiceImpl implements ${classSimpleName}Service {

    private static final Logger logger = LoggerFactory.getLogger(${classSimpleName}ServiceImpl.class);

    @Resource
    private ${classSimpleName}Dao ${classVariableName}Dao;

    /**
    * 保存处理
    */
    @Override
    public void save${classSimpleName}(${classSimpleName}Dto ${classVariableName}Dto) {
    ${classSimpleName} ${classVariableName} = new ${classSimpleName}();
    //TODO 校验与赋值映射

    int affect = ${classVariableName}Dao.insert(${classVariableName});
    logger.info("保存${classSimpleName} affect:{}, ${classVariableName}: {}", affect, GsonUtils.toJsonString(${classVariableName}));
    }

    /**
    * 根据id更新处理
    */
    @Override
    public void update${classSimpleName}ById(${classSimpleName}Dto ${classVariableName}Dto) {
    ${classSimpleName} updater = new ${classSimpleName}();
    //TODO 值映射校验与赋值映射

    int affect = ${classVariableName}Dao.updateById(updater);
    logger.info("更新${classSimpleName} affect:{}, updater: {}", affect, GsonUtils.toJsonString(updater));
    }

    /**
    * 根据id删除处理
    */
    @Override
    public void delete${classSimpleName}ById(${idType} ${id}) {
    //TODO 补充状态检测业务逻辑
    int affect = ${classVariableName}Dao.deleteById(${id});
    logger.info("删除${classSimpleName} affect:{}, id: {}", affect, GsonUtils.toJsonString(id));
    }

    /**
    * 分页查询数据列表
    */
    @Override
    public PagingResult<${classSimpleName}Dto> queryPagingResult(${classSimpleName}QueryDto queryDto, Paging paging) {
        PagingResult<${classSimpleName}Dto> pagingResult = PagingResult.create(paging);

            ${classSimpleName}Query query = new ${classSimpleName}Query();
            //TODO 设置参数（分页参数除外）

            Long count = ${classVariableName}Dao.selectCountByParam(query);
            pagingResult.setTotal(count);

            if (count > 0L) {
            query.setOffset(ValueUtils.defaultInteger(paging.getOffset()));
            query.setRows(ValueUtils.defaultInteger(paging.getLimit()));
            //query.setSorts(SortColumn.create("create_at", SortMode.DESC));
            List<${classSimpleName}> ${classVariableName}List = ${classVariableName}Dao.selectListByParam(query);

            List<${classSimpleName}Dto> ${classVariableName}DtoList = ${classVariableName}List.stream().map(record -> {
                ${classSimpleName}Dto ${classVariableName}Dto = new ${classSimpleName}Dto();
                //TODO 赋值映射
                return ${classVariableName}Dto;
                }).collect(Collectors.toList());
                pagingResult.setRows(${classVariableName}DtoList);
                }

                return pagingResult;
                }
                }