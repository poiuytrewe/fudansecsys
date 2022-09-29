package com.fudanuniversity.cms.commons.util;

import org.apache.commons.collections4.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类名区别于org.apache.commons.beanutils.BeanUtils
 * <p>
 * Created by Xinyue.Tang at 2021-04-14 15:12:43
 */
public final class BeanExUtils {

    /**
     * Bean -> Map
     */
    public static Map<String, Object> introspect(Object obj) {
        return introspectWithExclude(obj, Collections.emptySet());
    }

    /**
     * Bean -> Map
     *
     * @param obj     传入的对象
     * @param exclude 内省排除的属性
     */
    public static Map<String, Object> introspectWithExclude(Object obj, Collection<String> exclude) {
        if (obj == null) {
            return Collections.emptyMap();
        }
        if (CollectionUtils.isEmpty(exclude)) {
            exclude = Collections.emptySet();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException e) {
            throw new RuntimeException("cannot introspect bean: " + obj.getClass());
        }
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            String name = pd.getName();
            if (!"class".equals(name) && !exclude.contains(name)) {
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    try {
                        Object value = reader.invoke(obj);
                        result.put(name, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("introspect bean cannot invoke reader method: " + reader.getName());
                    }
                }
            }
        }
        return result;
    }

    /**
     * Bean -> Map
     *
     * @param obj     传入的对象
     * @param include 内省包含的属性
     */
    public static Map<String, Object> introspectWithInclude(Object obj, Collection<String> include) {
        if (obj == null || CollectionUtils.isEmpty(include)) {
            return Collections.emptyMap();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException e) {
            throw new RuntimeException("cannot introspect bean: " + obj.getClass());
        }
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            String name = pd.getName();
            if (!"class".equals(name) && include.contains(name)) {
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    try {
                        Object value = reader.invoke(obj);
                        result.put(name, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("introspect bean cannot invoke reader method: " + reader.getName());
                    }
                }
            }
        }
        return result;
    }
}