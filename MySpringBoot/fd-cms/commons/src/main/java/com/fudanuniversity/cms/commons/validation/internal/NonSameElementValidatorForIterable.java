package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.validation.constraints.NonSameElement;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;

/**
 * Created by Xinyue.Tang at 2020-07-21 11:05:54
 */
public class NonSameElementValidatorForIterable
        implements ConstraintValidator<NonSameElement, Iterable<?>> {

    private boolean ignoreEmptyString;

    @Override
    public void initialize(NonSameElement annotation) {
        ignoreEmptyString = annotation.ignoreEmptyString();
    }

    @Override
    public boolean isValid(
            Iterable<?> iterable, ConstraintValidatorContext ctx) {
        if (iterable == null) {
            return true;
        }

        HashSet<Object> elements = new HashSet<>();
        for (Object obj : iterable) {
            if (obj != null && (ignoreEmptyString && !"".equals(obj))) {
                if (!elements.add(obj)) {
                    return false;
                }
            }
        }

        return true;
    }
}
