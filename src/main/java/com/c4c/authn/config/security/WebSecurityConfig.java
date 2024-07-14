package com.c4c.authn.config.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Web security config.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
  /**
   * The constant AUTH_WHITELIST.
   */
  private static final String[] AUTH_WHITELIST = {
      "/swagger-resources",
      "/swagger-resources/**",
      "/swagger-ui.html",
      "/webjars/**",
      "/v3/api-docs/**",
      "/actuator/**",
      "/swagger-ui/**",
      "/api/v1/auth/authenticate",
      "/api/v1/auth/refreshToken",
      "/error"

  };
  /**
   * The Security debug.
   */
  @Value("${spring.security.debug:false}")
  private boolean securityDebug;
  /**
   * The Jwt token provider.
   */
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  public WebSecurityConfig(final JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  /**
   * Filter chain security filter chain.
   *
   * @param http the http
   * @return the security filter chain
   * @throws Exception the exception
   */
  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
    JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
    // Entry points
    return http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(AUTH_WHITELIST).permitAll()
            .requestMatchers("/tenant").hasAuthority("SUPER_ADMIN")
            .requestMatchers(HttpMethod.GET, "/tenant").hasAuthority("ADMIN")
            .anyRequest().authenticated())
        .httpBasic(withDefaults())
        .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

  /**
   * Web security customizer web security customizer.
   *
   * @return the web security customizer
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.debug(securityDebug).ignoring().requestMatchers(AUTH_WHITELIST)
        .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
  }

  /**
   * Cors configurer web mvc configurer.
   *
   * @return the web mvc configurer
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
      }
    };
  }
}
