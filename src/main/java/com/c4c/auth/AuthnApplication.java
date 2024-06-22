package com.c4c.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

/**
 * The type Authn application.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
    ThymeleafAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class AuthnApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthnApplication.class, args);
  }

}
