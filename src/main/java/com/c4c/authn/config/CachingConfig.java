package com.c4c.authn.config;

import com.c4c.authn.common.Constants;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * The type Caching config.
 */
@Configuration
@EnableCaching
public class CachingConfig {
    /**
     * The constant DEFAULT_CACHE_TTL.
     */
    public static final int DEFAULT_CACHE_TTL = 60;
    /**
     * The constant IDEMPOTENCY_CACHE_TTL.
     */
    public static final int IDEMPOTENCY_CACHE_TTL = 2;

    /**
     * Redis template redis template.
     *
     * @param lettuceConnectionFactory the lettuce connection factory
     * @return the redis template
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(final LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    /**
     * Cache configuration redis cache configuration.
     *
     * @return the redis cache configuration
     */
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(DEFAULT_CACHE_TTL))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer()));
    }

    /**
     * Redis cache manager builder customizer redis cache manager builder customizer.
     *
     * @return the redis cache manager builder customizer
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> builder
                .withCacheConfiguration(Constants.ITEM_CACHE,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(DEFAULT_CACHE_TTL)));
    }

}
