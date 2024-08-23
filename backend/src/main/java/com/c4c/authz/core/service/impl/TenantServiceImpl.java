package com.c4c.authz.core.service.impl;


import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.common.SpringUtil;
import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.repository.TenantRepository;
import com.c4c.authz.core.service.api.TenantService;
import com.c4c.authz.core.service.api.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.c4c.authz.common.Constants.SYSTEM_TENANT;

/**
 * The type Tenant service.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class TenantServiceImpl implements TenantService {
    /**
     * The Tenant repository.
     */
    private final TenantRepository tenantRepository;

    /**
     * The User service.
     */
    private final UserService userService;


    /**
     * The System tenant entity.
     */
    private TenantEntity systemTenantEntity;

    /**
     * Instantiates a new Tenant service.
     *
     * @param tenantRepository the tenant repository
     * @param userService      the user service
     */
    public TenantServiceImpl(final TenantRepository tenantRepository, final UserService userService) {
        this.tenantRepository = tenantRepository;
        this.userService = userService;
    }

    /**
     * Init.
     */
    @PostConstruct
    void init() {
        systemTenantEntity = this.tenantRepository.findByShortName(SYSTEM_TENANT);
    }

    /**
     * Gets new user entity.
     *
     * @param tenantEntity the tenant entity
     * @return the new user entity
     */
    private static UserEntity getNewUserEntity(final TenantEntity tenantEntity) {

        return UserEntity.builder().userName(tenantEntity.getShortName()).email(tenantEntity.getEmail())
                .mobile(tenantEntity.getMobile()).firstName(tenantEntity.getShortName())
                .lastName(tenantEntity.getShortName()).passwordHash("admin123").build();
    }

    /**
     * Create tenant entity.
     *
     * @param tenantEntity the tenant entity
     * @return the tenant entity
     */
    @Override
    @Transactional(readOnly = false)
    public TenantEntity create(final TenantEntity tenantEntity) {
        tenantEntity.setActive(true);
        tenantEntity.created(CurrentUserContext.getCurrentUser());
        return this.saveTenantEntity(tenantEntity);
    }

    /**
     * Update tenant entity.
     *
     * @param tenantEntity the tenant entity
     * @return the tenant entity
     */
    @Override
    @Transactional(readOnly = false)
    public TenantEntity update(final TenantEntity tenantEntity) {
        tenantEntity.updated(CurrentUserContext.getCurrentUser());
        return this.saveTenantEntity(tenantEntity);
    }

    /**
     * Find by id tenant entity.
     *
     * @param tenantId the tenant id
     * @return the tenant entity
     */
    @Override
    public TenantEntity findById(final UUID tenantId) {
        return this.tenantRepository.findById(tenantId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<TenantEntity> findAll() {
        if (this.isSystemTenant(CurrentUserContext.getCurrentTenant())) {
            return this.tenantRepository.findAll();
        } else {
            return SpringUtil.fromSingleItem(
                    this.tenantRepository.findById(CurrentUserContext.getCurrentTenant()).orElse(null));
        }
    }

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    @Override
    public Page<TenantEntity> findByPagination(final int pageNo, final int pageSize) {
        if (this.isSystemTenant(CurrentUserContext.getCurrentTenant())) {
            return this.tenantRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by("name").ascending()));
        } else {
            return SpringUtil.pagedFromSingleItem(
                    this.tenantRepository.findById(CurrentUserContext.getCurrentTenant()).orElse(null));
        }
    }

    /**
     * Delete by id.
     *
     * @param tenantId the tenant id
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteById(final UUID tenantId) {
        this.tenantRepository.deleteById(tenantId);
    }

    /**
     * Delete all by id.
     *
     * @param tenantIds the tenant ids
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteAllById(final List<UUID> tenantIds) {
        this.tenantRepository.deleteAllById(tenantIds);
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

    /**
     * Save tenant entity tenant entity.
     *
     * @param tenantEntity the tenant entity
     * @return the tenant entity
     */
    private TenantEntity saveTenantEntity(final TenantEntity tenantEntity) {
        TenantEntity entity = this.tenantRepository.save(tenantEntity);
        // If User not register then automatically register admin user
        if (Objects.isNull(this.userService.findByEmail(entity.getEmail()))) {
            UserEntity userEntity = getNewUserEntity(entity);
            userEntity.setTenantId(entity.getId());
            this.userService.save(userEntity);
        }
        return entity;
    }
}
