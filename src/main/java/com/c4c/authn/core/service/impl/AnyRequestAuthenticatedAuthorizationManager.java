package com.c4c.authn.core.service.impl;

import com.c4c.authn.core.service.api.RoleAttributeService;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * The type Any request authenticated authorization manager.
 */
public final class AnyRequestAuthenticatedAuthorizationManager implements
        AuthorizationManager<RequestAuthorizationContext> {
    /**
     * The Role attribute service.
     */
    private final RoleAttributeService roleAttributeService;

    /**
     * Instantiates a new Any request authenticated authorization manager.
     *
     * @param roleAttributeService the role attribute service
     */
    public AnyRequestAuthenticatedAuthorizationManager(RoleAttributeService roleAttributeService) {
        this.roleAttributeService = roleAttributeService;
    }

    /**
     * Verify.
     *
     * @param authentication the authentication
     * @param object         the object
     */
    @Override
    public void verify(final Supplier<Authentication> authentication, final RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    /**
     * Check authorization decision.
     *
     * @param authentication the authentication
     * @param object         the object
     * @return the authorization decision
     */
    @Override
    public AuthorizationDecision check(final Supplier<Authentication> authentication,
                                       final RequestAuthorizationContext object) {
        return new AuthorityAuthorizationDecision(true,
                (Collection<GrantedAuthority>) authentication.get().getAuthorities());
    }
}
