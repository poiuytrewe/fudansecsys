package com.fudanuniversity.cms.commons.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Xinyue.Tang at 2020-05-14 10:51:35
 */
public class EnumUtils {

    private static final String IdProperty = "id";
    private static final String CodeProperty = "code";
    private static final String KeyProperty = "key";

    /**
     * Key: enumClass/propertyName, Value: Map(Key: propertyValue, Value: Enum))
     */
    private final static ConcurrentMap<ClassPropertyCacheKey, Map<Object, Enum<?>>> PropertyEnumCache = new ConcurrentHashMap<>();

    /**
     * 获取某个枚举Class下所有属性值与所有枚举对应关系
     */
    private static <T extends Enum<T>> Map<Object, Enum<?>> getPropertyEnumMap(Class<T> enumClass, String property) {
        if (enumClass == null) {
            throw new IllegalArgumentException("'enumClass' cannot be null");
        }

        if (StringUtils.isEmpty(property)) {
            throw new IllegalArgumentException("'property' cannot be empty");
        }

        ClassPropertyCacheKey cacheKey = new ClassPropertyCacheKey(enumClass, property);
        Map<Object, Enum<?>> propertyEnumMap = PropertyEnumCache.get(cacheKey);

        if (propertyEnumMap == null) {
            PropertyEnumCache.putIfAbsent(cacheKey, describePropertyEnumMap(enumClass, property));
        }

        return PropertyEnumCache.get(cacheKey);
    }

    private static Map<Object, Enum<?>> describePropertyEnumMap(Class<? extends Enum<?>> enumClass, String property) {
        Map<Object, Enum<?>> propertyEnumMap = new HashMap<>();

        Enum<?>[] enumConstants = enumClass.getEnumConstants();

        if (enumConstants != null) {
            for (Enum<?> em : enumConstants) {
                Object propertyValue;

                try {
                    propertyValue = PropertyUtils.getProperty(em, property);
                } catch (Exception ex) {
                    throw new IllegalStateException("error in get enum '" + property
                            + "' property, please check " + enumClass + " [" + property + "] property all right", ex);
                }

                if (propertyEnumMap.put(propertyValue, em) != null) {//with duplicate check
                    throw new IllegalStateException(enumClass + " has duplicate '"
                            + property + "' property value");
                }
            }
        }

        return propertyEnumMap;
    }

    /**
     * 根据id获得相应的enum.
     *
     * @param id        枚举的id
     * @param enumClass 枚举类型
     * @param <T>       类型。
     * @return 返回枚举对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T enumFromId(Object id, Class<T> enumClass) {
        Map<Object, Enum<?>> codeEnumMap = getPropertyEnumMap(enumClass, IdProperty);
        return (T) codeEnumMap.get(id);
    }

    /**
     * 根据code获得相应的enum.
     *
     * @param code      枚举的code
     * @param enumClass 枚举类型
     * @param <T>       类型。
     * @return 返回枚举对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T enumFromCode(Object code, Class<T> enumClass) {
        Map<Object, Enum<?>> codeEnumMap = getPropertyEnumMap(enumClass, CodeProperty);
        return (T) codeEnumMap.get(code);
    }

    /**
     * 根据code获得相应的enum.
     *
     * @param key       枚举的key
     * @param enumClass 枚举类型
     * @param <T>       类型。
     * @return 返回枚举对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T enumFromKey(Object key, Class<T> enumClass) {
        Map<Object, Enum<?>> codeEnumMap = getPropertyEnumMap(enumClass, KeyProperty);
        return (T) codeEnumMap.get(key);
    }

    /**
     * 根据desc获得相应的enum.
     *
     * @param enumClass 枚举类型
     * @param property  属性名称
     * @param value     属性值
     * @param <T>       类型。
     * @return 返回枚举对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T enumFromProperty(String property, Object value, Class<T> enumClass) {
        Map<Object, Enum<?>> propertyEnumMap = getPropertyEnumMap(enumClass, property);
        return (T) propertyEnumMap.get(value);
    }


    /**
     * 根据name获得相应的enum.
     *
     * @param enumClass 枚举类型
     * @param <T>       类型。
     * @return 返回枚举对象
     */
    public static <T extends Enum<T>> T nameValueOf(String name, Class<T> enumClass) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, name);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("枚举名称[" + name + "]错误");
        }
    }

    private static class ClassPropertyCacheKey {
        private final Class<?> clazz;
        private final String key;

        private ClassPropertyCacheKey(Class<?> clazz, String key) {
            this.clazz = clazz;
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClassPropertyCacheKey that = (ClassPropertyCacheKey) o;
            return clazz.equals(that.clazz) &&
                    key.equals(that.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clazz, key);
        }
    }

    private EnumUtils() {
    }
}
