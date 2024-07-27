package com.c4c.authn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Authn application.
 */
@SuppressWarnings("CheckStyle")
@SpringBootApplication
public final class AuthnApplication {
    /**
     * Instantiates a new Authn application.
     */
    private AuthnApplication() {

    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        SpringApplication.run(AuthnApplication.class, args);
    }
}
