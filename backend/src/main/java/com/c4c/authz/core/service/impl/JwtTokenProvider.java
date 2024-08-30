package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.common.exception.CustomException;
import com.c4c.authz.core.entity.OauthTokenEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.service.api.OauthTokenService;
import com.c4c.authz.core.service.api.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The type Jwt token provider.
 */
@Component
@Transactional
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
     * The Validity in milliseconds.
     */
    @Getter
    @Value("${security.jwt.token.expire-length:3600000}")
    private int validityInMilliseconds = 3600000; // 1h
    /**
     * The User details service.
     */
    private final UserDetailsServiceImpl userDetailsService;
    /**
     * The Oauth token service.
     */
    private final OauthTokenService oauthTokenService;
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
     * @param oauthTokenService  the oauth token service
     * @param userService        the user service
     */
    public JwtTokenProvider(final UserDetailsServiceImpl userDetailsService,
                            final OauthTokenService oauthTokenService,
                            final UserService userService) {
        this.userDetailsService = userDetailsService;
        this.oauthTokenService = oauthTokenService;
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
        Date validity = new Date(now.getTime() + validityInMilliseconds);

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
        Date validity = new Date(c.getTimeInMillis() + validityInMilliseconds);

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
            if (!user.getTenantId().equals(CurrentUserContext.getCurrentTenantId())) {
                throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
            }
            boolean isFound = false;
            List<OauthTokenEntity> oauthTokenEntities = this.oauthTokenService.getByUserId(user.getId(), Calendar.getInstance());
            for (OauthTokenEntity entity : oauthTokenEntities) {
                if (entity.getAccessToken().equals(token)) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
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
