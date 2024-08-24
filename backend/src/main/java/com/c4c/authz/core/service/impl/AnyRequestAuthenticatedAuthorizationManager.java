package com.c4c.authz.core.service.impl;

import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.service.api.PolicyService;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.AntPathMatcher;

/**
 * The type Any request authenticated authorization manager.
 */
@Slf4j
public class AnyRequestAuthenticatedAuthorizationManager implements
    AuthorizationManager<RequestAuthorizationContext> {
  /**
   * The Policy service.
   */
  private final PolicyService policyService;
  /**
   * The Path matcher.
   */
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  /**
   * Instantiates a new Any request authenticated authorization manager.
   *
   * @param policyService the policy service
   */
  public AnyRequestAuthenticatedAuthorizationManager(PolicyService policyService) {
    this.policyService = policyService;
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
      List<PolicyRecord> policyRecords = this.policyService.getPoliciesByRoleId(roleEntity.getId());
      for (PolicyRecord policyRecord : policyRecords) {
        if (this.pathMatcher.match(policyRecord.path(), requestPath)
            && policyRecord.verbs().contains(verb)) {
          granted = true;
          break;
        }
      }
      if (granted) {
        break;
      }
    }
    if (!granted) {
      log.info("{} {} not authorised", requestPath, verb);
    }
    return new AuthorityAuthorizationDecision(granted,
        (Collection<GrantedAuthority>) authentication.get().getAuthorities());
  }
}
