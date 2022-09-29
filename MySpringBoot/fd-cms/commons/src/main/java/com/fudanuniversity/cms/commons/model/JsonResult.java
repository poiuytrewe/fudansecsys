package com.fudanuniversity.cms.commons.model;

import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.ValueUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 封装内部接口、web、移动端等返回数据
 * <p>
 * Created by Xinyue.Tang at 2020-07-08 16:32:43
 */
public class JsonResult<V> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * true表示接口调用成功
     */
    private Boolean success;

    private String code;

    private String msg;

    private V data;

    private Paging paging;

    public JsonResult() {
    }

    public JsonResult(V data) {
        this.success = Boolean.TRUE;
        this.data = data;
    }

    public JsonResult(Boolean success, String code, String msg, V data, Paging paging) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.paging = paging;
    }

    /**
     * 构造结果
     */
    public static <T> JsonResult<T> buildSuccess() {
        return new JsonResult<>(Boolean.TRUE, null, null, null, null);
    }

    /**
     * 构造结果
     */
    public static <T> JsonResult<T> buildSuccess(T data) {
        return new JsonResult<>(Boolean.TRUE, null, null, data, null);
    }

    /**
     * 构造分页返回结果
     */
    public static <T> JsonResult<T> buildSuccess(T data, Paging paging) {
        return new JsonResult<>(Boolean.TRUE, null, null, data, paging);
    }

    /**
     * 构造分页返回结果
     */
    public static <T> JsonResult<List<T>> buildSuccess(List<T> data, Paging paging) {
        return new JsonResult<>(Boolean.TRUE, null, null, ValueUtils.defaultList(data), paging);
    }

    /**
     * 构造分页返回结果
     */
    public static <T> JsonResult<List<T>> buildSuccess(PagingResult<T> pageResult) {
        Paging paging = new Paging();

        if (pageResult == null) {
            return buildSuccess(Collections.emptyList(), paging);
        }

        paging.setOffset(pageResult.getOffset());
        paging.setLimit(pageResult.getLimit());
        paging.setCursor(pageResult.getCursor());
        paging.setTotal(pageResult.getTotal());

        return buildSuccess(pageResult.getRows(), paging);
    }

    /**
     * 构造分页返回结果
     */
    public static <T, R> JsonResult<List<R>> buildSuccess(PagingResult<T> pageResult, Function<T, R> function) {
        Paging paging = new Paging();

        if (pageResult == null) {
            return buildSuccess(Collections.emptyList(), paging);
        }

        paging.setOffset(pageResult.getOffset());
        paging.setLimit(pageResult.getLimit());
        paging.setCursor(pageResult.getCursor());
        paging.setTotal(pageResult.getTotal());

        List<T> rows = ValueUtils.defaultList(pageResult.getRows());
        List<R> dataList = rows.stream().map(function).collect(Collectors.toList());
        return buildSuccess(dataList, paging);
    }

    public static <T> JsonResult<T> buildFail(String errorCode) {
        return new JsonResult<>(Boolean.FALSE, errorCode, null, null, null);
    }

    public static <T> JsonResult<T> buildFail(String errorCode, String message) {
        return new JsonResult<>(Boolean.FALSE, errorCode, message, null, null);
    }

    public static <T> JsonResult<T> buildResult(
            Boolean success, String errorCode, String message, T data, Paging paging) {
        return new JsonResult<>(success, errorCode, message, data, paging);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
