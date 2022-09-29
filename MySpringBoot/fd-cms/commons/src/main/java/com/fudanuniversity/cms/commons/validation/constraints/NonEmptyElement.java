package com.fudanuniversity.cms.commons.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验集合中每个元素不能为空
 * <p>
 * Created by Xinyue.Tang at 2018-07-23 19:27:47
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Repeatable(NonEmptyElement.List.class)
public @interface NonEmptyElement {

    String message() default "{com.fudanuniversity.cms.commons.validation.constraints.NonEmptyElement.message}";

    Class<?>[] groups() default {};

    boolean allowEmptyString() default true;

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several annotation constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        NonEmptyElement[] value();
    }
}