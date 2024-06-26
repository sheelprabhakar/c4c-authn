package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.TenantEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The interface Tenant repository.
 */
public interface TenantRepository extends JpaRepository<TenantEntity, UUID>, JpaSpecificationExecutor<TenantEntity> {
}
