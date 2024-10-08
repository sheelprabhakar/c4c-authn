package com.c4c.authz.config.security;

import com.c4c.authz.core.service.api.PolicyService;
import com.c4c.authz.core.service.impl.AnyRequestAuthenticatedAuthorizationManager;
import com.c4c.authz.core.service.impl.JwtTokenProvider;
import com.c4c.authz.filters.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.c4c.authz.common.Constants.AUTH_URL;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The type Web security config.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
    //https://www.springcloud.io/post/2022-02/easy-dyn-acl-spring-security/#gsc.tab=0
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
            AUTH_URL + "/authenticate",
            AUTH_URL + "/refreshToken",
            AUTH_URL + "/**/oauth2/v2.0/token/**",
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

    /**
     * The Policy service.
     */
    private final PolicyService policyService;

    /**
     * Instantiates a new Web security config.
     *
     * @param jwtTokenProvider the jwt token provider
     * @param policyService    the policy service
     */
    @Autowired
    public WebSecurityConfig(final JwtTokenProvider jwtTokenProvider, final PolicyService policyService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.policyService = policyService;
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
                        //.requestMatchers("/tenant").hasAuthority("SUPER_ADMIN")
                        //.requestMatchers(HttpMethod.GET, "/tenant").hasAuthority("ADMIN")
                        .requestMatchers("/v1/api/**")
                        .access(new AnyRequestAuthenticatedAuthorizationManager(this.policyService))
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
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
            }
        };
    }
}
