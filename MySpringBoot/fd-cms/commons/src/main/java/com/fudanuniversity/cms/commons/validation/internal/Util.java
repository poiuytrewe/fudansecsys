package com.fudanuniversity.cms.commons.validation.internal;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Xinyue.Tang at 2018-08-13 14:41:32
 */
public class Util {

    public static boolean isEmpty(Object obj, boolean passIfEmpty) {
        return obj == null || (passIfEmpty && "".equals(obj));
    }

    /**
     * 判断字符串前后是否有空白字符
     * <pre>
     * System.out.println(isTrimmed("")) --> false;
     * System.out.println(isTrimmed("\b")) --> true;
     * System.out.println(isTrimmed("aaa\b")) --> true;
     * System.out.println(isTrimmed("\r\naaa")) --> true;
     * System.out.println(isTrimmed("\faaa")) --> true;
     * System.out.println(isTrimmed("  aaa ")) --> true;
     * </pre>
     *
     * @see String#trim()
     */
    public static boolean isTrimmed(String value) {
        int len = value.length();

        if (len > 0 && value.charAt(0) <= ' ') {
            return true;
        }

        if (len > 0 && value.charAt(len - 1) <= ' ') {
            return true;
        }

        return false;
    }

    public static Set<Object> getEnumMethodValues(Class<? extends Enum<?>> enumClass, String methodName) {
        Set<Object> methodValues = new HashSet<Object>();
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants != null) {
            for (Enum<?> em : enumConstants) {
                try {
                    Object propertyValue = MethodUtils.invokeExactMethod(em, methodName, new Object[0]);
                    methodValues.add(propertyValue);

                } catch (Exception ex) {
                    throw new IllegalStateException("error in invoke enum '" + methodName
                            + "' method, please check " + enumClass + " [" + methodName + "] method all right", ex);
                }
            }
        }
        return methodValues;
    }

    public static Set<Object> getEnumPropertyValues(Class<? extends Enum<?>> enumClass, String propertyName) {
        Set<Object> propertyValues = new HashSet<Object>();
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants != null) {
            for (Enum<?> em : enumConstants) {
                try {
                    Object propertyValue = PropertyUtils.getProperty(em, propertyName);
                    propertyValues.add(propertyValue);
                } catch (Exception ex) {
                    throw new IllegalStateException("error in get enum '" + propertyName
                            + "' property, please check " + enumClass + " [" + propertyName + "] property all right", ex);
                }
            }
        }
        return propertyValues;
    }

    private Util() {
    }
}
