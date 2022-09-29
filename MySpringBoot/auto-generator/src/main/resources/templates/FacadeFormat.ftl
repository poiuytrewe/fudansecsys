package ${packagePrefix}.business.service;

import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import ${packagePrefix}.dto.${classSimpleName}Dto;
import ${packagePrefix}.repository.query.${classSimpleName}QueryDto;

/**
* ${classSimpleName}Service
* <p>
    * Created by ${user} at ${datetime}
    */
    public interface ${classSimpleName}Service {

    /**
    * 保存处理
    */
    void save${classSimpleName}(${classSimpleName}Dto ${classVariableName}Dto);

    /**
    * 根据id更新处理
    */
    void update${classSimpleName}ById(${classSimpleName}Dto ${classVariableName}Dto);

    /**
    * 根据id删除处理
    */
    void delete${classSimpleName}ById(${idType} ${id});

    /**
    * 分页查询数据列表
    */
    PagingResult<${classSimpleName}Dto> queryPagingResult(${classSimpleName}QueryDto queryDto, Paging paging);

        }