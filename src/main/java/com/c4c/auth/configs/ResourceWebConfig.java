package com.c4c.auth.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type ResourceWebConfig.
 */
@Configuration
public class ResourceWebConfig implements WebMvcConfigurer {
  /**
   * The Environment.
   */
  final Environment environment;

  /**
   * Instantiates a new Resource web config.
   *
   * @param environment the environment
   */
  public ResourceWebConfig(Environment environment) {
    this.environment = environment;
  }

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    // For windows user;
    // registry.addResourceHandler("/uploads/**").addResourceLocations("file:///D:/App/authorization/uploads/");
    String location = environment.getProperty("app.file.storage.mapping");

    registry.addResourceHandler("/uploads/**").addResourceLocations(location);
  }
}
