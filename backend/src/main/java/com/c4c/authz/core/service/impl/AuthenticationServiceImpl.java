package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.entity.OauthTokenEntity;
import com.c4c.authz.core.service.api.AuthenticationService;
import com.c4c.authz.core.service.api.ClientExDetailsService;
import com.c4c.authz.core.service.api.ClientRoleService;
import com.c4c.authz.core.service.api.ClientService;
import com.c4c.authz.core.service.api.UserExDetailsService;
import com.c4c.authz.core.service.api.UserService;
import com.c4c.authz.core.service.api.OauthTokenService;
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

import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

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
     * The Client details service.
     */
    private final ClientExDetailsService clientDetailsService;
    /**
     * The User service.
     */
    private final UserService userService;

    /**
     * The Oauth token service.
     */
    private final OauthTokenService oauthTokenService;
    /**
     * The Password encoder.
     */
    private final PasswordEncoder passwordEncoder;


    /**
     * The Client service.
     */
    private final ClientService clientService;

    /**
     * Instantiates a new Authentication service.
     *
     * @param jwtTokenProvider     the jwt token provider
     * @param userDetailsService   the user details service
     * @param clientDetailsService the client details service
     * @param userService          the user service
     * @param oauthTokenService    the oauth token service
     * @param passwordEncoder      the password encoder
     * @param clientService        the client service
     */
    @Autowired
    public AuthenticationServiceImpl(final JwtTokenProvider jwtTokenProvider,
                                     final UserExDetailsService userDetailsService,
                                     ClientExDetailsService clientDetailsService,
                                     final UserService userService, final OauthTokenService oauthTokenService,
                                     final PasswordEncoder passwordEncoder,
                                     final ClientService clientService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
        this.userService = userService;
        this.oauthTokenService = oauthTokenService;
        this.passwordEncoder = passwordEncoder;
        this.clientService = clientService;
    }

    /**
     * Authenticate oauth token entity.
     *
     * @param username the username
     * @param password the password
     * @param isOtp    the is otp
     * @return the oauth token entity
     */
    @Override
    public OauthTokenEntity authenticate(final String username,
                                         final String password, final boolean isOtp) {

        UserEntity userEntity = this.userService.findByUserName(username);
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
            CurrentUserContext.setCurrentTenantId(userEntity.getTenantId());
            String token = this.jwtTokenProvider.createToken(userDetails.getUsername(),
                    (Set<GrantedAuthority>) userDetails.getAuthorities());
            String refreshToken = this.jwtTokenProvider.createRefreshToken(userDetails.getUsername());
            return this.oauthTokenService.addUserToken(userEntity.getId(), CurrentUserContext.getCurrentTenantId(), token,
                    refreshToken, this.getExpiry());
        } else {
            log.info("Authenticated failed");
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }

    }

    /**
     * Gets expiry.
     *
     * @return the expiry
     */
    private Calendar getExpiry() {
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.MILLISECOND, (int)this.jwtTokenProvider.getValidityInMilliseconds());
        return expiry;
    }

    /**
     * Logout.
     */
    @Override
    public void logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserEntity userEntity = this.userService.findByUserName(userDetails.getUsername());
        if (userEntity == null) {
            log.info(USER_NOT_FOUND);
            throw new BadCredentialsException(USER_NOT_FOUND);
        }
       //ToDo
        //this.oauthTokenService.deleteAllById(userEntity.getId());
    }

    /**
     * Refresh token oauth token entity.
     *
     * @param refreshToken the refresh token
     * @return the oauth token entity
     */
    @Override
    public OauthTokenEntity refreshToken(final String refreshToken) {
        if (this.jwtTokenProvider.validateToken(refreshToken)) {
            String username = this.jwtTokenProvider.getUsername(refreshToken);
            UserEntity userEntity = this.userService.findByUserName(username);
            UserDetails userDetails = this.userDetailsService
                    .loadUserByUsername(userEntity);
            CurrentUserContext.setCurrentTenantId(userEntity.getTenantId());
            String token = this.jwtTokenProvider.createToken(userDetails.getUsername(),
                    (Set<GrantedAuthority>) userDetails.getAuthorities());
            String newRefreshToken = this.jwtTokenProvider.createRefreshToken(userDetails.getUsername());
            return this.oauthTokenService.addUserToken(userEntity.getId(), CurrentUserContext.getCurrentTenantId(), token,
                    newRefreshToken, this.getExpiry());
        }
        return null;
    }

    /**
     * Authenticate oauth token entity.
     *
     * @param tenantId     the tenant id
     * @param clientId     the client id
     * @param clientSecret the client secret
     * @param grantType    the grant type
     * @return the oauth token entity
     */
    @Override
    public OauthTokenEntity authenticate(final UUID tenantId, final String clientId, final String clientSecret,
                                         final String grantType) {
        ClientEntity clientEntity = this.clientService.findByClientId(clientId);
        if (clientEntity != null && clientSecret.equals(clientEntity.getClientSecret())) {
            UserDetails userDetails = this.clientDetailsService
                    .loadClientByClient(clientEntity);
            CurrentUserContext.setCurrentTenantId(clientEntity.getTenantId());
            String token = this.jwtTokenProvider.createToken(clientEntity.getClientId(),
                    (Set<GrantedAuthority>) userDetails.getAuthorities());
            return this.oauthTokenService.addClientToken(clientEntity.getId(), CurrentUserContext.getCurrentTenantId(), token,
                    this.getExpiry());
        } else {
            log.info("Authenticated failed");
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }
    }

}
