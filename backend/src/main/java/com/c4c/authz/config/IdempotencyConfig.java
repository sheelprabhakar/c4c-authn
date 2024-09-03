package com.c4c.authz.config;

import com.c4c.authz.filters.IdempotenceFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * The type Idempotency config.
 */
@Configuration
public class IdempotencyConfig {

    /**
     * The Idempotency api paths.
     */
    @Value("${c4c.authz.idempotency.paths}")
    private List<String> idempotencyApiPaths;

    /**
     * The Ttl in minutes.
     */
    @Value("${c4c.authz.idempotency.ttlInMinutes:60}")
    private Long ttlInMinutes;

    /**
     * Idem redis template redis template.
     *
     * @param redisConnectionFactory the redis connection factory
     * @return the redis template
     */
    @Bean("idemRedisTemplate")
    RedisTemplate<String, IdempotenceFilter.IdempotencyValue> idemRedisTemplate(
            final RedisConnectionFactory redisConnectionFactory) {
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

    /**
     * Idempotence filter registration bean filter registration bean.
     *
     * @param idemRedisTemplate the idem redis template
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<IdempotenceFilter> idempotenceFilterRegistrationBean(
            @Qualifier("idemRedisTemplate")
            final RedisTemplate<String, IdempotenceFilter.IdempotencyValue> idemRedisTemplate) {

        FilterRegistrationBean<IdempotenceFilter> registrationBean = new FilterRegistrationBean<>();

        IdempotenceFilter idempotenceFilter = new IdempotenceFilter(idemRedisTemplate, ttlInMinutes);

        registrationBean.setFilter(idempotenceFilter);
        registrationBean.addUrlPatterns(idempotencyApiPaths.toArray(String[]::new));
        registrationBean.setOrder(1); //set precedence
        return registrationBean;
    }

}
