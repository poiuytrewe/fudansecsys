package generate.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BeanExUtils {

    /**
     * Bean -> Map
     */
    public static Map<String, Object> introspect(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> result = new LinkedHashMap<>();
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException e) {
            throw new IllegalArgumentException("cannot introspect bean: " + obj.getClass());
        }
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            String name = pd.getName();
            if (!"class".equals(name)) {
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    try {
                        Object value = reader.invoke(obj);
                        result.put(name, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new IllegalArgumentException("introspect bean cannot invoke method: " + reader.getName());
                    }
                }
            }
        }
        return result;
    }
}
