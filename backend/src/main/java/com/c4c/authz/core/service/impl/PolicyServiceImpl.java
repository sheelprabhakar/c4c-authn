package com.c4c.authz.core.service.impl;

import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.core.entity.RoleAttributeEntity;
import com.c4c.authz.core.service.api.PolicyService;
import com.c4c.authz.core.service.api.RoleAttributeService;
import java.util.ArrayList;
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
   * Instantiates a new Policy service.
   *
   * @param roleAttributeService the role attribute service
   */
  public PolicyServiceImpl(RoleAttributeService roleAttributeService) {
    this.roleAttributeService = roleAttributeService;
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
