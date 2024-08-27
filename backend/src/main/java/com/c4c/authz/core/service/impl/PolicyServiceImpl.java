package com.c4c.authz.core.service.impl;

import static com.c4c.authz.common.Constants.CLIENT_CRED_ROLE_NAME;

import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.entity.RoleAttributeEntity;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.service.api.ClientService;
import com.c4c.authz.core.service.api.PolicyService;
import com.c4c.authz.core.service.api.RoleAttributeService;
import com.c4c.authz.core.service.api.RoleService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * The type Policy service.
 */
@Service
@Slf4j
public class PolicyServiceImpl implements PolicyService {
  /**
   * The Role attribute service.
   */
  private final RoleAttributeService roleAttributeService;
  /**
   * The Role service.
   */
  private final RoleService roleService;

  /**
   * The Client service.
   */
  private final ClientService clientService;

  /**
   * Instantiates a new Policy service.
   *
   * @param roleAttributeService the role attribute service
   * @param roleService          the role service
   * @param clientService        the client service
   */
  public PolicyServiceImpl(RoleAttributeService roleAttributeService, final RoleService roleService,
                           final ClientService clientService) {
    this.roleAttributeService = roleAttributeService;
    this.roleService = roleService;
    this.clientService = clientService;
  }

  /**
   * Gets policies by role id.
   *
   * @param roleId the role id
   * @return the policies by role id
   */
  @Cacheable(cacheNames = "attributes", key = "#p0")
  @Override
  public List<PolicyRecord> getPoliciesByRoleId(final UUID roleId) {
    List<RoleAttributeEntity> allByRoleId = this.roleAttributeService.findAllByRoleId(roleId);
    List<PolicyRecord> policyRecords = new ArrayList<>();
    allByRoleId.forEach(entity -> {
      PolicyRecord policyRecord = new PolicyRecord(entity.getAttributeEntity().getName(),
          entity.getAttributeEntity().getPath(), getVerbs(entity));
      policyRecords.add(policyRecord);
    });

    return policyRecords;
  }

  /**
   * Gets policies for current client.
   *
   * @param tenantId the tenant id
   * @param clientId the client id
   * @return the policies for current client
   */
  @Override
  public List<PolicyRecord> getPoliciesForCurrentClient(final UUID tenantId, final String clientId) {
    ClientEntity clientEntity = this.clientService.findByTenantIdAndClientId(tenantId, clientId);
    RoleEntity roleEntity = this.roleService.findByTenantIdAndName(tenantId, CLIENT_CRED_ROLE_NAME);
    if(clientEntity != null && roleEntity != null) {
      return this.getPoliciesByRoleId(roleEntity.getId());
    }else {
      log.error("Client or role not found for tenantId: {} and clientId: {}", tenantId, clientId);
    }
    return new ArrayList<>();
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
