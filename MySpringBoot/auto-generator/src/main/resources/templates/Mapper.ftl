package ${packagePrefix}.repository.mapper;

import ${packagePrefix}.repository.entity.${classSimpleName};
import ${packagePrefix}.repository.query.${classSimpleName}Query;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ${classSimpleName}Mapper接口
 * <p>
 * Created by ${user} at ${datetime}
 */
@Mapper
public interface ${classSimpleName}Mapper {

    /**
     * 保存处理
     */
    int insert(${classSimpleName} ${classVariableName});

    /**
     * 批量upsert
     */
    int bulkUpsert(List<${classSimpleName}> ${classVariableName}List);

    /**
     * 删除处理
     */
    int deleteById(${idType} ${id});

    /**
     * 更新处理
     */
    int updateById(${classSimpleName} ${classVariableName});

    /**
     * 根据条件查询信息列表
     */
    List<${classSimpleName}> selectListByParam(${classSimpleName}Query query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(${classSimpleName}Query query);

}
