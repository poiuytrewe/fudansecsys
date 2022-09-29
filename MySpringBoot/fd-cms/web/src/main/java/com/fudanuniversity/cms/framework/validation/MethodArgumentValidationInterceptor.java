package com.fudanuniversity.cms.framework.validation;

import com.fudanuniversity.cms.commons.exception.ErrorCode;
import com.fudanuniversity.cms.commons.exception.ParameterException;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import com.fudanuniversity.cms.commons.validation.ValidGroup;
import jakarta.validation.*;
import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.groups.Default;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Xinyue.Tang at 2018-07-23 21:39:01
 */
public class MethodArgumentValidationInterceptor implements MethodInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(MethodArgumentValidationInterceptor.class);

    private Validator validator;

    public MethodArgumentValidationInterceptor() {
        this(Validation.buildDefaultValidatorFactory());
    }

    public MethodArgumentValidationInterceptor(ValidatorFactory validatorFactory) {
        this(validatorFactory.getValidator());
    }

    public MethodArgumentValidationInterceptor(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?>[] groups = determineValidationGroups(invocation);

        ExecutableValidator executableValidator = validator.forExecutables();
        Method methodToValidate = invocation.getMethod();
        Set<ConstraintViolation<Object>> constraintViolations;

        try {
            constraintViolations = executableValidator.validateParameters(
                    invocation.getThis(), methodToValidate, invocation.getArguments(), groups);
        } catch (IllegalArgumentException ex) {
            // Probably a generic type mismatch between interface and impl as reported in SPR-12237 / HV-1011
            // Let's try to find the bridged method on the implementation class...
            methodToValidate = BridgeMethodResolver.findBridgedMethod(
                    ClassUtils.getMostSpecificMethod(invocation.getMethod(), invocation.getThis().getClass()));

            constraintViolations = executableValidator.validateParameters(
                    invocation.getThis(), methodToValidate, invocation.getArguments(), groups);
        }

//        if (!constraintViolations.isEmpty()) {
//            throw buildValidationException(constraintViolations);
//        }

        //noinspection UnnecessaryLocalVariable
        Object returnValue = invocation.proceed();

        /*
        constraintViolations = executableValidator.validateReturnValue(invocation.getThis(), methodToValidate, returnValue, groups);

        if (!constraintViolations.isEmpty()) {
            throw ValidationExceptionFactory.buildValidationException(constraintViolations);
        }*/

        return returnValue;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    private final static Class<?>[] DefaultGroups = new Class[]{Default.class};

    /**
     * 获取方法执行时的校验组
     *
     * @param invocation MethodInvocation
     * @return 校验组Class数组
     */
    protected Class<?>[] determineValidationGroups(MethodInvocation invocation) {
        ValidGroup validatedAnn = AnnotationUtils.findAnnotation(invocation.getMethod(), ValidGroup.class);
        if (validatedAnn == null) {
            validatedAnn = AnnotationUtils.findAnnotation(
                    Objects.requireNonNull(invocation.getThis()).getClass(), ValidGroup.class);
        }
        Class<?>[] groups = DefaultGroups;
        if (validatedAnn != null) {
            Class<?>[] defGroups = validatedAnn.value();
            if (validatedAnn.includeDefault()) {
                if (defGroups.length > 0) {
                    groups = new Class[defGroups.length + 1];
                    System.arraycopy(defGroups, 0, groups, 0, defGroups.length);
                    groups[defGroups.length] = Default.class;
                }
            } else {
                groups = validatedAnn.value();
            }
        }
        return groups;
    }

    public RuntimeException buildValidationException(Set<ConstraintViolation<Object>> violations) {
        ConstraintViolation<Object> violation = violations.iterator().next();//一条错误信息就够了
        Class<Object> rootBeanClass = violation.getRootBeanClass();
        String label = rootBeanClass == null ? "" : rootBeanClass.getSimpleName();
        Path propertyPath = violation.getPropertyPath();
        Object invalidValue = violation.getInvalidValue();
        String message = violation.getMessage();
        logger.warn("Application method argument constraint violation, label:{}, propertyPath: {}, message: {}, invalidValue: {}",
                label, propertyPath, message, JsonUtils.toJsonString(invalidValue));
        throw new ParameterException(ErrorCode.IllegalParameterCode, message);
    }
}