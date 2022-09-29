package ${packagePrefix}.repository.dao;

import ${packagePrefix}.repository.entity.${classSimpleName};
import ${packagePrefix}.repository.query.${classSimpleName}Query;

import java.util.List;

/**
 * ${classSimpleName}Dao
 * <p>
 * Created by ${user} at ${datetime}
 */
public interface ${classSimpleName}Dao {

    /**
     * 保存处理
     */
    int insert(${classSimpleName} ${classVariableName});

    /**
     * 根据id更新处理
     */
    int updateById(${classSimpleName} ${classVariableName});

    /**
     * 根据id删除
     */
    int deleteById(${idType} ${id});

    /**
     * 查询数据
     */
    List<${classSimpleName}> selectListByParam(${classSimpleName}Query query);

    /**
     * 查询数量
     */
    Long selectCountByParam(${classSimpleName}Query query);

}
