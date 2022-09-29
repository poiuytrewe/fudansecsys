package com.fudanuniversity.cms.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.util.Assert;

/**
 * redis存储会话
 * <p>
 * Created by Xinyue.Tang at 2021-05-01 22:01:21
 *
 * @see RedisHttpSessionConfiguration
 */
@Configuration
@EnableRedisHttpSession(
        maxInactiveIntervalInSeconds = 60 * 45 * 100/*TODO 为了方便调试会话时间太大，完成后修改会话时间*/,
        redisNamespace = "fd:cms:session")
public class RedisSessionConfig {

    /**
     * @see RedisHttpSessionConfiguration#setDefaultRedisSerializer(org.springframework.data.redis.serializer.RedisSerializer)
     */
    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> jackson2JsonSerializer() {
        DefaultJackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new DefaultJackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = JsonUtils.createGeneralObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会异常
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        return jackson2JsonRedisSerializer;
    }

    /**
     * 会话cookie使用的key约定为 sid
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("sid");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        return serializer;
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    static final byte[] EMPTY_ARRAY = new byte[0];

    static boolean isEmpty(@Nullable byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * 默认的Jackson2JsonRedisSerializer在反序列化失败时会抛出异常，这里简单的做了调整，如果序列化遇到错误直接返回null
     *
     * @see Jackson2JsonRedisSerializer
     */
    static class DefaultJackson2JsonRedisSerializer<T> implements RedisSerializer<T> {

        private final JavaType javaType;

        private ObjectMapper objectMapper = new ObjectMapper();

        public DefaultJackson2JsonRedisSerializer(Class<T> type) {
            this.javaType = getJavaType(type);
        }

        public DefaultJackson2JsonRedisSerializer(JavaType javaType) {
            this.javaType = javaType;
        }

        @SuppressWarnings("unchecked")
        public T deserialize(@Nullable byte[] bytes) throws SerializationException {
            if (isEmpty(bytes)) {
                return null;
            }
            try {
                return (T) this.objectMapper.readValue(bytes, 0, bytes.length, javaType);
            } catch (Exception ex) {
                return null;    //Note: 序列化失败可能是改了序列化对象的包名什么的，这里做优化处理，返回null，不一定最合理但是能规避烦人问题
                //throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
            }
        }

        @Override
        public byte[] serialize(@Nullable Object t) throws SerializationException {
            if (t == null) {
                return EMPTY_ARRAY;
            }
            try {
                return this.objectMapper.writeValueAsBytes(t);
            } catch (Exception ex) {
                throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
            }
        }

        public void setObjectMapper(ObjectMapper objectMapper) {
            Assert.notNull(objectMapper, "'objectMapper' must not be null");
            this.objectMapper = objectMapper;
        }

        protected JavaType getJavaType(Class<?> clazz) {
            return TypeFactory.defaultInstance().constructType(clazz);
        }
    }
}
