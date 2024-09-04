package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.RestAclEntity;
import com.c4c.authz.core.repository.RestAclRepository;
import com.c4c.authz.core.service.api.RestAclService;
import com.c4c.authz.core.service.api.SystemTenantService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Rest acl service.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class RestAclServiceImpl implements RestAclService {

    /**
     * The System tenant service.
     */
    private final SystemTenantService systemTenantService;
    /**
     * The Rest acl repository.
     */
    private final RestAclRepository restAclRepository;

    /**
     * Instantiates a new Rest acl service.
     *
     * @param systemTenantService the system tenant service
     * @param restAclRepository   the rest acl repository
     */
    public RestAclServiceImpl(final SystemTenantService systemTenantService,
                              final RestAclRepository restAclRepository) {
        this.systemTenantService = systemTenantService;
        this.restAclRepository = restAclRepository;
    }

    /**
     * Create rest acl entity.
     *
     * @param restAclEntity the rest acl entity
     * @return the rest acl entity
     */
    @Override
    @Transactional(readOnly = false)
    public RestAclEntity create(final RestAclEntity restAclEntity) {
        restAclEntity.created(CurrentUserContext.getCurrentUser());
        return this.restAclRepository.save(restAclEntity);
    }

    /**
     * Update rest acl entity.
     *
     * @param restAclEntity the rest acl entity
     * @return the rest acl entity
     */
    @Override
    @Transactional(readOnly = false)
    public RestAclEntity update(final RestAclEntity restAclEntity) {
        restAclEntity.updated(CurrentUserContext.getCurrentUser());
        return this.restAclRepository.save(restAclEntity);
    }

    /**
     * Find by id rest acl entity.
     *
     * @param resourceId the resource id
     * @return the rest acl entity
     */
    @Override
    public RestAclEntity findById(final UUID resourceId) {
        return this.restAclRepository.findById(resourceId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<RestAclEntity> findAll() {
        if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenantId())) {
            return (List<RestAclEntity>) this.restAclRepository.findAll();
        } else {
            return this.restAclRepository.findAllByTenantId(CurrentUserContext.getCurrentTenantId());
        }
    }

    /**
     * Find by pagination page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<RestAclEntity> findByPagination(final Pageable pageable) {
        if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenantId())) {
            return this.restAclRepository.findAll(pageable);
        } else {
            return this.restAclRepository.findAllByTenantId(pageable, CurrentUserContext.getCurrentTenantId());
        }
    }

    /**
     * Delete by id.
     *
     * @param resourceId the resource id
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteById(final UUID resourceId) {
        this.restAclRepository.deleteById(resourceId);
    }

    /**
     * Delete all by id.
     *
     * @param resourceIds the resource ids
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteAllById(final List<UUID> resourceIds) {
        this.restAclRepository.deleteAllById(resourceIds);
    }
}
