package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.TenantUserEntity;
import com.c4c.authn.core.entity.TenantUserEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantUserRepository extends JpaRepository<TenantUserEntity, TenantUserEntityId> {
}
