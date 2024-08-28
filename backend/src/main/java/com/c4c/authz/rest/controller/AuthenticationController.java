package com.c4c.authz.rest.controller;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.rest.resource.auth.JwtRequest;
import com.c4c.authz.rest.resource.auth.JwtResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.c4c.authz.common.Constants.AUTH_URL;

/**
 * The type Authentication controller.
 */
@Slf4j
@RestController()
@RequestMapping(AuthenticationController.BASE_URL)
public class AuthenticationController extends BaseController {
    /**
     * The constant BASE_URL.
     */
    static final String BASE_URL = AUTH_URL;

    /**
     * Instantiates a new Authentication controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    public AuthenticationController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    /**
     * Authenticate response entity.
     *
     * @param authenticationRequest the authentication request
     * @return the response entity
     */
    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(
            final @Valid @RequestBody JwtRequest authenticationRequest) {
        JwtResponse jwtResponse = this.getRestAdapterV1().authenticate(authenticationRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/{tenantId}/oauth2/v2.0/token")
    public ResponseEntity<JwtResponse> authenticateClient(
        @PathVariable(value = "tenantId") final UUID tenantId,
        final @RequestParam(value = "clientId", required = true) String clientId,
        final @RequestParam(value = "clientSecret", required = true) String clientSecret,
        final @RequestParam(value = "grantType", required = true) String grantType) {
        JwtResponse jwtResponse = this.getRestAdapterV1().authenticateClient(tenantId, clientId, clientSecret, grantType);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Logout response entity.
     *
     * @return the response entity
     */
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        this.getRestAdapterV1().logout();
        return ResponseEntity.ok("Logged out successfully");
    }

    /**
     * Refresh token response entity.
     *
     * @param refreshToken the refresh token
     * @return the response entity
     */
    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(final @RequestParam String refreshToken) {
        JwtResponse jwtResponse = this.getRestAdapterV1().refreshToken(refreshToken);
        return ResponseEntity.ok(jwtResponse);
    }
}
