package com.c4c.authn.config.security;

import com.c4c.authn.common.exception.CustomException;
import com.c4c.authn.core.entity.UserEntity;
import com.c4c.authn.core.entity.UserTokenEntity;
import com.c4c.authn.core.service.api.UserService;
import com.c4c.authn.core.service.api.UserTokenService;
import com.c4c.authn.core.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * The type Jwt token provider.
 */
@Component
@Slf4j
public class JwtTokenProvider {

  /**
   * The constant FIVE.
   */
  public static final int FIVE = 5;
  /**
   * The constant BEARER_LENGTH.
   */
  private static final int BEARER_LENGTH = 7;
  /**
   * The constant VALIDITY_IN_MILLISECONDS.
   */
  @Value("${security.jwt.token.expire-length:3600000}")
  private static final long VALIDITY_IN_MILLISECONDS = 3600000L; // 1h
  /**
   * The User details service.
   */
  private final UserDetailsServiceImpl userDetailsService;
  /**
   * The User token service.
   */
  private final UserTokenService userTokenService;
  /**
   * The User service.
   */
  private final UserService userService;
  /**
   * The Secret key.
   */
  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;
  /**
   * The Secret.
   */
  private SecretKey secret;

  /**
   * Instantiates a new Jwt token provider.
   *
   * @param userDetailsService the user details service
   * @param userTokenService   the user token service
   * @param userService        the user service
   */
  public JwtTokenProvider(final UserDetailsServiceImpl userDetailsService,
                          final UserTokenService userTokenService,
                          final UserService userService) {
    this.userDetailsService = userDetailsService;
    this.userTokenService = userTokenService;
    this.userService = userService;
  }

  /**
   * Init.
   */
  @PostConstruct
  protected void init() {
    this.secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Create token string.
   *
   * @param username the username
   * @param roles    the roles
   * @return the string
   */
  public String createToken(final String username, final Set<GrantedAuthority> roles) {

    Claims claims = Jwts.claims().subject(username).add("authorities", roles.stream().map(
            s -> new SimpleGrantedAuthority(s.getAuthority()))
        .filter(Objects::nonNull).toList()).build();

    Date now = new Date();
    Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);

    return Jwts.builder()//
        .claims(claims)//
        .issuedAt(now)//
        .expiration(validity)//
        .signWith(this.secret)//
        .compact();
  }

  /**
   * Create refresh token string.
   *
   * @param username the username
   * @return the string
   */
  public String createRefreshToken(final String username) {

    Claims claims = Jwts.claims().subject(username).build();

    Calendar c = Calendar.getInstance();
    c.add(Calendar.MINUTE, FIVE);
    Date validity = new Date(c.getTimeInMillis() + VALIDITY_IN_MILLISECONDS);

    return Jwts.builder()//
        .claims(claims)//
        .issuedAt(new Date(c.getTimeInMillis()))//
        .expiration(validity)//
        .signWith(this.secret)//
        .compact();
  }

  /**
   * Gets authentication.
   *
   * @param token the token
   * @return the authentication
   */
  public Authentication getAuthentication(final String token) {
    UserEntity user = this.userService.findByEmail(getUsername(token));
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(user);

    if (user != null) {
      UserTokenEntity userTokenEntity = this.userTokenService.getById(user.getId());
      if (userTokenEntity == null || !userTokenEntity.getAccessToken().equals(token)) {
        throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
      }
    }
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  /**
   * Gets username.
   *
   * @param token the token
   * @return the username
   */
  public String getUsername(final String token) {
    return Jwts.parser().verifyWith(this.secret).build().parseSignedClaims(token).getPayload()
        .getSubject();
  }

  /**
   * Resolve token string.
   *
   * @param req the req
   * @return the string
   */
  public String resolveToken(final HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(BEARER_LENGTH);
    }
    log.info("Invalid Token");
    return null;
  }

  /**
   * Validate token boolean.
   *
   * @param token the token
   * @return the boolean
   */
  public boolean validateToken(final String token) {
    try {
      Jwts.parser().verifyWith(this.secret).build()
          .parse(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.info("Expired or invalid JWT token");
      throw new CustomException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
    }
  }

}
