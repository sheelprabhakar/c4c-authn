package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.Constants;
import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.common.exception.CustomException;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.entity.OauthTokenEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.service.api.ClientExDetailsService;
import com.c4c.authz.core.service.api.ClientService;
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
import org.apache.commons.lang3.tuple.Pair;
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
import java.util.UUID;

import static com.c4c.authz.common.Constants.AUTHORITIES;
import static com.c4c.authz.common.Constants.IS_CLIENT_CRED;

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
     * The Client details service.
     */
    private final ClientExDetailsService clientDetailsService;
    /**
     * The Oauth token service.
     */
    private final OauthTokenService oauthTokenService;
    /**
     * The User service.
     */
    private final UserService userService;

    /**
     * The Client service.
     */
    private final ClientService clientService;
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
     * @param userDetailsService   the user details service
     * @param clientDetailsService the client details service
     * @param oauthTokenService    the oauth token service
     * @param userService          the user service
     * @param clientService        the client service
     */
    public JwtTokenProvider(final UserDetailsServiceImpl userDetailsService,
                            final ClientExDetailsService clientDetailsService,
                            final OauthTokenService oauthTokenService,
                            final UserService userService, final ClientService clientService) {
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
        this.oauthTokenService = oauthTokenService;
        this.userService = userService;
        this.clientService = clientService;
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
     * @param username     the username
     * @param roles        the roles
     * @param isClientCred the is client cred
     * @return the string
     */
    public String createToken(final String username, final Set<GrantedAuthority> roles, boolean isClientCred) {

        Claims claims = Jwts.claims().subject(username)
                .add(IS_CLIENT_CRED, isClientCred)
                .add(AUTHORITIES, roles.stream().map(
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
        Claims claims = Jwts.claims().subject(username)
                .add(IS_CLIENT_CRED, false).build();

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
        Pair<String, Boolean> userInfo = getUsername(token);
        UserDetails userDetails = null;
        // Double check token from DB
        if (Boolean.TRUE.equals( userInfo.getRight())) {
            ClientEntity clientEntity = this.clientService.findByClientId(userInfo.getLeft());
            if (null != clientEntity) {
                userDetails = this.clientDetailsService.loadClientByClient(clientEntity);
                validateUserTokenFromDb(clientEntity.getTenantId(),
                        this.oauthTokenService.getByClientId(clientEntity.getId(), Calendar.getInstance()), token);
            }
        } else {
            UserEntity userEntity = this.userService.findByUserName(userInfo.getLeft());
            if (null != userEntity) {
                userDetails = this.userDetailsService.loadUserByUsername(userEntity);
                validateUserTokenFromDb(userEntity.getTenantId(),
                        this.oauthTokenService.getByUserId(userEntity.getId(), Calendar.getInstance()), token);
            }
        }
        if (null != userDetails) {
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } else {
            throw new CustomException(Constants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Validate user token from db.
     *
     * @param user              the user
     * @param oauthTokenService the oauth token service
     * @param token             the token
     */
    private void validateUserTokenFromDb(final UUID user,
                                         final List<OauthTokenEntity> oauthTokenService, final String token) {
        if (user != null) {
            if (!user.equals(CurrentUserContext.getCurrentTenantId())) {
                throw new CustomException(Constants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
            }
            boolean isFound = false;
            List<OauthTokenEntity> oauthTokenEntities = oauthTokenService;
            for (OauthTokenEntity entity : oauthTokenEntities) {
                if (entity.getAccessToken().equals(token)) {
                    CurrentUserContext.setCurrentTokeId(entity.getId());
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                throw new CustomException(Constants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new CustomException(Constants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Gets username.
     *
     * @param token the token
     * @return the username
     */
    public Pair<String, Boolean> getUsername(final String token) {
        Claims payload = Jwts.parser().verifyWith(this.secret).build().parseSignedClaims(token).getPayload();

        return Pair.of(payload.getSubject(), Boolean.parseBoolean(payload.get(IS_CLIENT_CRED).toString()));
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
