package ${packagePrefix}.business.service.impl;

import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import ${packagePrefix}.dto.${classSimpleName}Dto;
import ${packagePrefix}.dto.${classSimpleName}QueryDto;
import ${packagePrefix}.business.service.${classSimpleName}Service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${classSimpleName}Controller
 * <p>
 * Created by ${user} at ${datetime}
 */
@RestController
@RequestMapping("/demo")
public class ${classSimpleName}Controller extends BaseController {

    @Resource
    private ${classSimpleName}Service ${classVariableName}Service;

    /**
     * 保存处理
     */
    @PostMapping("/add")
    public JsonResult<?> save${classSimpleName}(@Valid ${classSimpleName}Dto ${classVariableName}Dto) {
        ${classVariableName}Service.save${classSimpleName}(${classVariableName}Dto);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id更新处理
     */
    @PostMapping("/modify")
    public JsonResult<?> update${classSimpleName}ById(@Valid ${classSimpleName}Dto ${classVariableName}Dto) {
        ${classVariableName}Service.update${classSimpleName}ById(${classVariableName}Dto);
        return JsonResult.buildSuccess();
    }

    /**
     * 根据id删除处理
     */
    @PostMapping("/remove")
    public JsonResult<?> delete${classSimpleName}ById(@NotNull @Min(1L) ${idType} ${id}) {
        ${classVariableName}Service.delete${classSimpleName}ById(${id});
        return JsonResult.buildSuccess();
    }

    /**
     * 根据条件查询信息列表
     */
    @GetMapping("/paging")
    public JsonResult<List<${classSimpleName}Dto>> queryPagingResult(@Valid ${classSimpleName}QueryDto queryDto, @Valid Paging paging) {
        PagingResult<${classSimpleName}Dto> pagingResult = ${classVariableName}Service.queryPagingResult(queryDto, paging);
        return JsonResult.buildSuccess(pagingResult);
    }
}