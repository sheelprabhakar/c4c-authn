package com.c4c.authn.config;

import com.c4c.authn.filters.IdempotenceFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class IdempotencyConfig {

    @Value("${c4c.authn.idempotency.paths}")
    private List<String> idempotencyApiPaths;

    @Value("${c4c.authn.idempotency.ttlInMinutes:60}")
    private Long ttlInMinutes;

    @Bean("idemRedisTemplate")
    RedisTemplate<String, IdempotenceFilter.IdempotencyValue> idemRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<IdempotenceFilter.IdempotencyValue> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(IdempotenceFilter.IdempotencyValue.class);

        RedisTemplate<String, IdempotenceFilter.IdempotencyValue> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);

        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    @Bean
    public FilterRegistrationBean<IdempotenceFilter> idempotenceFilterRegistrationBean(
            @Qualifier("idemRedisTemplate")
            RedisTemplate<String, IdempotenceFilter.IdempotencyValue> idemRedisTemplate) {

        FilterRegistrationBean<IdempotenceFilter> registrationBean = new FilterRegistrationBean<>();

        IdempotenceFilter idempotenceFilter = new IdempotenceFilter(idemRedisTemplate, ttlInMinutes);

        registrationBean.setFilter(idempotenceFilter);
        registrationBean.addUrlPatterns(idempotencyApiPaths.toArray(String[]::new));
        registrationBean.setOrder(1); //set precedence
        return registrationBean;
    }

}