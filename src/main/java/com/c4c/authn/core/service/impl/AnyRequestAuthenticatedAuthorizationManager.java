package com.c4c.authn.core.service.impl;

import com.c4c.authn.core.domain.AttributeRecord;
import com.c4c.authn.core.entity.RoleAttributeEntity;
import com.c4c.authn.core.entity.RoleEntity;
import com.c4c.authn.core.service.api.RoleAttributeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
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
     * The Path matcher.
     */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

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
     * @param authentication              the authentication
     * @param requestAuthorizationContext the request authorization context
     * @return the authorization decision
     */
    @Override
    public AuthorizationDecision check(final Supplier<Authentication> authentication,
                                       final RequestAuthorizationContext requestAuthorizationContext) {
        boolean granted = false;
        List<RoleEntity> roleEntities = (List<RoleEntity>) authentication.get().getAuthorities();
        String requestPath = requestAuthorizationContext.getRequest().getRequestURI();
        String verb = requestAuthorizationContext.getRequest().getMethod();
        for (RoleEntity roleEntity : roleEntities) {
            List<AttributeRecord> attributeRecords = this.getAttributesByRoleId(roleEntity.getId());
            for (AttributeRecord attributeRecord : attributeRecords) {
                if (this.pathMatcher.match(attributeRecord.path(), requestPath) &&
                        attributeRecord.verbs().contains(verb)) {
                    granted = true;
                    break;
                }
            }
            if (granted) {
                break;
            }
        }
        return new AuthorityAuthorizationDecision(granted,
                (Collection<GrantedAuthority>) authentication.get().getAuthorities());
    }


    /**
     * Gets attributes by role id.
     *
     * @param roleId the role id
     * @return the attributes by role id
     */
    @Cacheable(cacheNames = "attributes", key = "#p0")
    private List<AttributeRecord> getAttributesByRoleId(final UUID roleId) {
        List<RoleAttributeEntity> allByRoleId = this.roleAttributeService.findAllByRoleId(roleId);
        List<AttributeRecord> attributeRecords = new ArrayList<>();
        allByRoleId.forEach(entity -> {

            AttributeRecord attributeRecord = new AttributeRecord(entity.getAttributeEntity().getAttributeName(),
                    entity.getAttributeEntity().getPath(), getVerbs(entity));
            attributeRecords.add(attributeRecord);
        });

        return attributeRecords;
    }

    /**
     * Gets verbs.
     *
     * @param entity the entity
     * @return the verbs
     */
    private static List<String> getVerbs(final RoleAttributeEntity entity) {
        List<String> verbs = new ArrayList<>();
        if (entity.isCanCreate()) {
            verbs.add(HttpMethod.POST.name());
        }
        if (entity.isCanDelete()) {
            verbs.add(HttpMethod.DELETE.name());
        }
        if (entity.isCanUpdate()) {
            verbs.add(HttpMethod.PUT.name());
            verbs.add(HttpMethod.PATCH.name());
        }
        if (entity.isCanRead()) {
            verbs.add(HttpMethod.GET.name());
        }
        return verbs;
    }
}
