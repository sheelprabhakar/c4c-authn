package com.c4c.authz.core.service.impl;

import static com.c4c.authz.common.Constants.SYSTEM_TENANT;

import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.core.repository.TenantRepository;
import com.c4c.authz.core.service.api.SystemTenantService;
import jakarta.annotation.PostConstruct;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type System tenant service.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class SystemTenantServiceImpl implements SystemTenantService {
    /**
     * The System tenant entity.
     */
    private TenantEntity systemTenantEntity;
    /**
     * The Tenant repository.
     */
    private final TenantRepository tenantRepository;

    /**
     * Instantiates a new System tenant service.
     *
     * @param tenantRepository the tenant repository
     */
    public SystemTenantServiceImpl(final TenantRepository tenantRepository) {
    this.tenantRepository = tenantRepository;
  }


    /**
     * Init.
     */
    @PostConstruct
  void init() {
    this.systemTenantEntity = this.tenantRepository.findByShortName(SYSTEM_TENANT);
  }

    /**
     * Is system tenant boolean.
     *
     * @param tenantId the tenant id
     * @return the boolean
     */
    @Override
  @Cacheable(value = "tenants", key = "#p0")
  public boolean isSystemTenant(final UUID tenantId) {
    return tenantId.equals(this.systemTenantEntity.getId());
  }
}
