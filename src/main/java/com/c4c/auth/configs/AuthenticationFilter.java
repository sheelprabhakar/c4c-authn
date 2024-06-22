package com.c4c.auth.configs;

import static com.c4c.auth.common.Constants.HEADER_STRING;
import static com.c4c.auth.common.Constants.JWT_EXPIRED_MESSAGE;
import static com.c4c.auth.common.Constants.JWT_ILLEGAL_ARGUMENT_MESSAGE;
import static com.c4c.auth.common.Constants.JWT_SIGNATURE_MESSAGE;
import static com.c4c.auth.common.Constants.TOKEN_PREFIX;

import com.c4c.auth.common.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The type AuthenticationFilter.
 */
public class AuthenticationFilter extends OncePerRequestFilter {
  private final UserDetailsService userDetailsService;

  private final JwtTokenUtil jwtTokenUtil;

  /**
   * Instantiates a new Authentication filter.
   *
   * @param userDetailsService the user details service
   * @param jwtTokenUtil       the jwt token util
   */
  public AuthenticationFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
    this.userDetailsService = userDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain
  ) throws IOException, ServletException {
    String header = req.getHeader(HEADER_STRING);
    String username = null;
    String authToken = null;

    if (header != null && header.startsWith(TOKEN_PREFIX)) {
      authToken = header.replace(TOKEN_PREFIX, "");

      try {
        username = jwtTokenUtil.getUsernameFromToken(authToken);
      } catch (IllegalArgumentException e) {
        logger.error(JWT_ILLEGAL_ARGUMENT_MESSAGE, e);
      } catch (ExpiredJwtException e) {
        logger.warn(JWT_EXPIRED_MESSAGE, e);
      } catch (SignatureException e) {
        logger.error(JWT_SIGNATURE_MESSAGE);
      }
    } else {
      logger.warn("couldn't find bearer string, will ignore the header");
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      boolean isValidToken = jwtTokenUtil.validateToken(authToken, userDetails);

      if (Boolean.TRUE.equals(isValidToken)) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities()
            );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        logger.info("authenticated user " + username);

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    chain.doFilter(req, res);
  }
}
