package com.fudanuniversity.cms.commons.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 判断校验的字段是否在某个取值列表中
 * <p>
 * Created by Xinyue.Tang at 2018-07-23 19:27:47
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Repeatable(In.List.class)
public @interface In {

    /**
     * String数组取值列表，如果校验的字段为数组或其他，则转成字符串作比较
     */
    String[] value();

    /**
     * 空字符串是否校验通过
     */
    boolean allowEmptyString() default true;

    String message() default "{com.fudanuniversity.cms.commons.validation.constraints.In.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several annotation constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        In[] value();
    }
}