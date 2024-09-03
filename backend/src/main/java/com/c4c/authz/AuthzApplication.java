package com.c4c.authz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * The type Authz application.
 */
@SuppressWarnings("CheckStyle")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AuthzApplication {
    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        SpringApplication.run(AuthzApplication.class, args);
    }
}
