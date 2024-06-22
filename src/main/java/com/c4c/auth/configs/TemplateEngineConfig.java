package com.c4c.auth.configs;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * The type TemplateEngineConfig.
 */
@Configuration
public class TemplateEngineConfig implements WebMvcConfigurer {
  private static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

  /**
   * Email template engine template engine.
   *
   * @return the template engine
   */
  @Bean
  @Primary
  public TemplateEngine emailTemplateEngine() {
    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    // Resolver for HTML emails (except the editable one)
    templateEngine.addTemplateResolver(emailTemplateResolver());

    return templateEngine;
  }

  private ITemplateResolver emailTemplateResolver() {
    final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setOrder(1);
    templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
    templateResolver.setPrefix("/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
    templateResolver.setCacheable(false);

    return templateResolver;
  }

  /**
   * Web template resolver class loader template resolver.
   *
   * @return the class loader template resolver
   */
  @Bean
  @Description("Thymeleaf template resolver serving HTML 5")
  public ClassLoaderTemplateResolver webTemplateResolver() {

    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");

    // Template cache is true by default.
    // Set to false if you want templates to be automatically updated when modified.
    templateResolver.setCacheable(false);

    return templateResolver;
  }

  /**
   * Web template engine spring template engine.
   *
   * @return the spring template engine
   */
  @Bean
  @Description("Thymeleaf template engine with Spring integration")
  public SpringTemplateEngine webTemplateEngine() {

    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(webTemplateResolver());

    return templateEngine;
  }

  /**
   * View resolver view resolver.
   *
   * @return the view resolver
   */
  @Bean
  @Description("Thymeleaf view resolver")
  public ViewResolver viewResolver() {

    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

    viewResolver.setTemplateEngine(webTemplateEngine());
    viewResolver.setCharacterEncoding("UTF-8");

    return viewResolver;
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
  }
}
