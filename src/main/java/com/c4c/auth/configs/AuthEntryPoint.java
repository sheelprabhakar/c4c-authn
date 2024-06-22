package com.c4c.auth.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * The type AuthEntryPoint.
 */
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint, Serializable {

  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException
  ) throws IOException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
        "Unauthorized to access to this resource");
  }
}
