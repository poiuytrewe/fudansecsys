package ${packagePrefix}.business.service;

import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import ${packagePrefix}.repository.entity.${classSimpleName};
import ${packagePrefix}.repository.query.${classSimpleName}Query;

/**
 * ${classSimpleName}Service
 * <p>
 * Created by ${user} at ${datetime}
 */
public interface ${classSimpleName}Service {

    /**
     * 保存处理
     */
    void add${classSimpleName}(${classSimpleName} ${classVariableName});

    /**
     * 根据id更新处理
     */
    void modify${classSimpleName}ById(${classSimpleName} ${classVariableName});

    /**
     * 根据id删除处理
     */
    void remove${classSimpleName}ById(${idType} ${id});

    /**
     * 分页查询数据列表
     */
    PagingResult<${classSimpleName}> queryPagingResult(${classSimpleName}Query query, Paging paging);

}