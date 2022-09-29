package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.validation.constraints.In;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Xinyue.Tang at 2018-07-23 19:27:47
 */
public class InValidator
        implements ConstraintValidator<In, Object> {

    private boolean allowEmptyString;

    private Set<String> values;

    public final void initialize(final In annotation) {
        this.allowEmptyString = annotation.allowEmptyString();

        this.values = new HashSet<>();
        String[] defVal = annotation.value();
        Collections.addAll(values, defVal);
    }

    public final boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null || (allowEmptyString && "".equals(value))) {
            return true;
        }

        return values.contains(valueOf(value)); // check if value is in this.values
    }

    private String valueOf(Object obj) {
        if (obj == null) {
            return null;
        }

        return String.valueOf(obj);
    }
}