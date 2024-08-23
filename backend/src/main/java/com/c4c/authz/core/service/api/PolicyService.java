package com.c4c.authz.core.service.api;

import com.c4c.authz.core.domain.PolicyRecord;
import java.util.List;
import java.util.UUID;
import org.springframework.cache.annotation.Cacheable;

/**
 * The interface Policy service.
 */
public interface PolicyService {
  @Cacheable(cacheNames = "attributes", key = "#p0")
  List<PolicyRecord> getPoliciesByRoleId(UUID roleId);
}
