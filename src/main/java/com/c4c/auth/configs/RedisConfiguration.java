package com.c4c.auth.configs;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * The type RedisConfiguration.
 */
@Configuration
@EnableRedisRepositories(value = "com.c4c.auth.repositories")
public class RedisConfiguration {
  /**
   * Redis connection factory lettuce connection factory.
   *
   * @return the lettuce connection factory
   */
  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisProperties properties = redisProperties();
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

    configuration.setHostName(properties.getHost());
    configuration.setPort(properties.getPort());

    return new LettuceConnectionFactory(configuration);
  }

  /**
   * Redis template redis template.
   *
   * @return the redis template
   */
  @Bean
  public RedisTemplate<?, ?> redisTemplate() {
    RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    return template;
  }

  /**
   * Redis properties redis properties.
   *
   * @return the redis properties
   */
  @Bean
  @Primary
  public RedisProperties redisProperties() {
    return new RedisProperties();
  }
}
