package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.TenantEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The interface Tenant repository.
 */
public interface TenantRepository extends JpaRepository<TenantEntity, UUID>, JpaSpecificationExecutor<TenantEntity> {
  /**
   * Find by short name tenant entity.
   *
   * @param shortName the short name
   * @return the tenant entity
   */
  TenantEntity findByShortName(String shortName);
}
