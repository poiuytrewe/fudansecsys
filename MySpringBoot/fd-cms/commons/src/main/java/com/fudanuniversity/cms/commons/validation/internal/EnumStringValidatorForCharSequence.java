package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.validation.constraints.EnumString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Xinyue.Tang at 2018-07-23 19:27:47
 */
public class EnumStringValidatorForCharSequence
        implements ConstraintValidator<EnumString, CharSequence> {

    private boolean allowEmptyString;

    private boolean ignoreCase;

    private Set<String> enumValuesString;

    @Override
    public void initialize(EnumString annotation) {
        this.allowEmptyString = annotation.allowEmptyString();

        this.ignoreCase = annotation.ignoreCase();
        this.enumValuesString = new HashSet<>();
        Class<? extends Enum<?>> enumClass = annotation.enumClass();
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        for (Enum<?> e : enumConstants) {
            String name = annotation.ignoreCase() ? e.name().toLowerCase() : e.name();
            enumValuesString.add(name);
        }
    }

    @Override
    public boolean isValid(CharSequence field, ConstraintValidatorContext ctx) {
        if (field == null || (allowEmptyString && "".equals(field.toString()))) {
            return true;
        }

        if (ignoreCase) {
            return enumValuesString.contains(field.toString().toLowerCase());
        }

        return enumValuesString.contains(field.toString());
    }

}