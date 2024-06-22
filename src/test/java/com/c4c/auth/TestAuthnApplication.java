package com.c4c.auth;

import org.springframework.boot.SpringApplication;

public class TestAuthnApplication {

  public static void main(String[] args) {
    SpringApplication.from(AuthnApplication::main).with(TestcontainersConfiguration.class)
        .run(args);
  }

}
