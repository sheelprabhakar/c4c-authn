package com.c4c.authn.core.service.impl;

import com.c4c.authn.config.security.JwtTokenProvider;
import com.c4c.authn.config.tenant.CurrentUserContext;
import com.c4c.authn.core.entity.UserEntity;
import com.c4c.authn.core.entity.UserTokenEntity;
import com.c4c.authn.core.service.api.AuthenticationService;
import com.c4c.authn.core.service.api.UserExDetailsService;
import com.c4c.authn.core.service.api.UserService;
import com.c4c.authn.core.service.api.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * The type Authentication service.
 */
@Service
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    /**
     * The constant USER_NOT_FOUND.
     */
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    /**
     * The Jwt token provider.
     */
    private final JwtTokenProvider jwtTokenProvider;
    /**
     * The User details service.
     */
    private final UserExDetailsService userDetailsService;

    /**
     * The User service.
     */
    private final UserService userService;

    /**
     * The User token service.
     */
    private final UserTokenService userTokenService;
    /**
     * The Password encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new Authentication service.
     *
     * @param jwtTokenProvider   the jwt token provider
     * @param userDetailsService the user details service
     * @param userService        the user service
     * @param userTokenService   the user token service
     * @param passwordEncoder    the password encoder
     */
    @Autowired
    public AuthenticationServiceImpl(final JwtTokenProvider jwtTokenProvider,
                                     final UserExDetailsService userDetailsService,
                                     final UserService userService, final UserTokenService userTokenService,
                                     final PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.userTokenService = userTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate user token entity.
     *
     * @param username the username
     * @param password the password
     * @param isOtp    the is otp
     * @return the user token entity
     */
    @Override
    public UserTokenEntity authenticate(final String username,
                                        final String password, final boolean isOtp) {

        UserEntity userEntity = this.userService.findByEmail(username);
        if (userEntity == null) {
            log.info(USER_NOT_FOUND);
            throw new BadCredentialsException(USER_NOT_FOUND);
        }

        String encodePwd = "";
        if (isOtp) {
            if (this.userService.isOTPRequired(userEntity)) {
                throw new BadCredentialsException("OTP_EXPIRED");
            }
            encodePwd = userEntity.getOtp();
        } else {
            encodePwd = userEntity.getPasswordHash();
        }
        if (this.passwordEncoder.matches(password, encodePwd)) {
            this.userService.clearOTP(userEntity);
            final UserDetails userDetails = this.userDetailsService
                    .loadUserByUsername(username);
            log.info("Authenticated successfully");
            CurrentUserContext.setCurrentTenant(userEntity.getTenantId());
            String token = this.jwtTokenProvider.createToken(userDetails.getUsername(),
                    (Set<GrantedAuthority>) userDetails.getAuthorities());
            String refreshToken = this.jwtTokenProvider.createRefreshToken(userDetails.getUsername());
            return this.userTokenService.update(userEntity.getId(), CurrentUserContext.getCurrentTenant(), token,
                    refreshToken);
        } else {
            log.info("Authenticated failed");
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }

    }

    /**
     * Logout.
     */
    @Override
    public void logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserEntity userEntity = this.userService.findByEmail(userDetails.getUsername());
        if (userEntity == null) {
            log.info(USER_NOT_FOUND);
            throw new BadCredentialsException(USER_NOT_FOUND);
        }
        this.userTokenService.deleteById(userEntity.getId());
    }

    /**
     * Refresh token user token entity.
     *
     * @param refreshToken the refresh token
     * @return the user token entity
     */
    @Override
    public UserTokenEntity refreshToken(final String refreshToken) {
        if (this.jwtTokenProvider.validateToken(refreshToken)) {
            String username = this.jwtTokenProvider.getUsername(refreshToken);
            UserEntity userEntity = this.userService.findByEmail(username);
            UserDetails userDetails = this.userDetailsService
                    .loadUserByUsername(userEntity);
            CurrentUserContext.setCurrentTenant(userEntity.getTenantId());
            String token = this.jwtTokenProvider.createToken(userDetails.getUsername(),
                    (Set<GrantedAuthority>) userDetails.getAuthorities());

            String newRefreshToken = this.jwtTokenProvider.createRefreshToken(userDetails.getUsername());
            return this.userTokenService.update(userEntity.getId(), CurrentUserContext.getCurrentTenant(), token,
                    newRefreshToken);
        }
        return null;
    }

}
