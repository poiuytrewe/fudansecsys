package com.fudanuniversity.cms.framework.aop;

import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Xinyue.Tang at 2018-07-23 21:39:01
 */
public class InheritedAnnotationMethodMatcher extends StaticMethodMatcher {

    private final Class<? extends Annotation> annotationType;

    public InheritedAnnotationMethodMatcher(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        //validateApplicable(method);
        if (inheritedMatch(method)) {
            return true;
        }
        // The method may be on an interface, so let's check on the target class as well.
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        return specificMethod != method && inheritedMatch(specificMethod);
    }

    private boolean inheritedMatch(Method method) {
        return AnnotationUtils.findAnnotation(method, this.annotationType) != null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InheritedAnnotationMethodMatcher)) {
            return false;
        }
        InheritedAnnotationMethodMatcher otherMm = (InheritedAnnotationMethodMatcher) other;
        return this.annotationType.equals(otherMm.annotationType);
    }

    @Override
    public int hashCode() {
        return this.annotationType.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + this.annotationType;
    }
}