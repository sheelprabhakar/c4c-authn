package com.c4c.authz.core.service.api;

import com.c4c.authz.core.domain.PolicyRecord;
import java.util.List;
import java.util.UUID;

/**
 * The interface Policy service.
 */
public interface PolicyService {
  /**
   * Gets policies by role id.
   *
   * @param roleId the role id
   * @return the policies by role id
   */
  List<PolicyRecord> getPoliciesByRoleId(UUID roleId);

  /**
   * Gets policies for current client.
   *
   * @param tenantId the tenant id
   * @param clientId the client id
   * @return the policies for current client
   */
  List<PolicyRecord> getPoliciesForCurrentClient(UUID tenantId, String clientId);

  /**
   * Gets policies for current user.
   *
   * @param currentTenantId the current tenant id
   * @param currentUser     the current user
   * @return the policies for current user
   */
  List<PolicyRecord> getPoliciesForCurrentUser(UUID currentTenantId, String currentUser);
}
