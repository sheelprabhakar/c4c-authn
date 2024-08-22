package com.c4c.authn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * The type Authn application.
 */
@SuppressWarnings("CheckStyle")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AuthnApplication {
    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        SpringApplication.run(AuthnApplication.class, args);
    }
}
