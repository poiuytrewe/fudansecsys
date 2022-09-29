package com.fudanuniversity.cms.commons.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验集合中不能出现相同元素
 * <p>
 * Created by Xinyue.Tang at 2020-07-21 11:04:03
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Repeatable(NonSameElement.List.class)
public @interface NonSameElement {

    String message() default "{com.fudanuniversity.cms.commons.validation.constraints.NonSameElement.message}";

    Class<?>[] groups() default {};

    boolean ignoreEmptyString() default true;

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several annotation constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        NonSameElement[] value();
    }
}
