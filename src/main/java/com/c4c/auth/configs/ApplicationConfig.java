package com.c4c.auth.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The type ApplicationConfig.
 */
@Configuration
public class ApplicationConfig {

  /**
   * Encoder b crypt password encoder.
   *
   * @return the b crypt password encoder
   */
  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
