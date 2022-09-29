package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

/**
 * Created by Xinyue.Tang at 2018-08-13 12:07:21
 */
public class EnumValueValidatorForIterable
        implements ConstraintValidator<EnumValue, Iterable<?>> {

    private boolean allowEmptyString;
    private Set<Object> enumValues;

    @Override
    public void initialize(EnumValue annotation) {
        this.allowEmptyString = annotation.allowEmptyString();

        String property = annotation.property();
        String method = annotation.method();

        if ((property.length() == 0 && method.length() == 0)
                || (property.length() > 0 && method.length() > 0)) {
            throw new IllegalStateException("Error configuration of "
                    + annotation + ": select one from 'property' or 'method'");
        }

        Class<? extends Enum<?>> enumClass = annotation.enumClass();
        if (property.length() > 0) {
            enumValues = Util.getEnumPropertyValues(enumClass, property);
        } else {
            enumValues = Util.getEnumMethodValues(enumClass, method);
        }
    }

    @Override
    public boolean isValid(Iterable<?> field, ConstraintValidatorContext context) {
        if (field == null) {
            return true;
        }

        for (Object obj : field) {
            if (!isValid(obj)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValid(Object obj) {
        if (obj == null || (allowEmptyString && "".equals(obj))) {
            return true;
        }

        return enumValues.contains(obj);
    }
}
