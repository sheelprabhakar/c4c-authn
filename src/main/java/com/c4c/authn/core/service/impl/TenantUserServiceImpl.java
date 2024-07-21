package com.c4c.authn.core.service.impl;

import com.c4c.authn.core.entity.TenantUserEntity;
import com.c4c.authn.core.repository.TenantRepository;
import com.c4c.authn.core.repository.TenantUserRepository;
import com.c4c.authn.core.service.api.TenantUserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Tenant user service.
 */
@Service
public class TenantUserServiceImpl implements TenantUserService {

  /**
   * The Tenant user repository.
   */
  private final TenantUserRepository tenantUserRepository;

  /**
   * The Tenant repository.
   */
  private final TenantRepository tenantRepository;

  /**
   * Instantiates a new Tenant user service.
   *
   * @param tenantUserRepository the tenant user repository
   * @param tenantRepository     the tenant repository
   */
  @Autowired
  public TenantUserServiceImpl(final TenantUserRepository tenantUserRepository,
                               final TenantRepository tenantRepository) {
    this.tenantUserRepository = tenantUserRepository;
    this.tenantRepository = tenantRepository;
  }

  /**
   * Save tenant user entity.
   *
   * @param tenantId the tenant id
   * @param userId   the user id
   * @return the tenant user entity
   */
  @Override
  public TenantUserEntity save(final UUID tenantId, final UUID userId) {
    TenantUserEntity tenantUserEntity = new TenantUserEntity();
    tenantUserEntity.setTenantId(tenantId);
    tenantUserEntity.setUserId(userId);

    this.tenantUserRepository.save(tenantUserEntity);
    return tenantUserEntity;
  }

}
