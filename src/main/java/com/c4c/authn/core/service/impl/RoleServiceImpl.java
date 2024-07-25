package com.c4c.authn.core.service.impl;

import com.c4c.authn.config.tenant.CurrentUserContext;
import com.c4c.authn.core.entity.RoleEntity;
import com.c4c.authn.core.repository.RoleRepository;
import com.c4c.authn.core.service.api.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Role service.
 */
@Service
@Slf4j
@Transactional
public class RoleServiceImpl implements RoleService {
    /**
     * The Role repository.
     */
    private final RoleRepository roleRepository;

    /**
     * Instantiates a new Role service.
     *
     * @param roleRepository the role repository
     */
    public RoleServiceImpl(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Create role entity.
     *
     * @param roleEntity the role entity
     * @return the role entity
     */
    @Override
    public RoleEntity create(final RoleEntity roleEntity) {
        roleEntity.created(CurrentUserContext.getCurrentUser());
        return this.saveRoleEntity(roleEntity);
    }

    /**
     * Update role entity.
     *
     * @param roleEntity the role entity
     * @return the role entity
     */
    @Override
    public RoleEntity update(final RoleEntity roleEntity) {
        roleEntity.updated(CurrentUserContext.getCurrentUser());
        return this.saveRoleEntity(roleEntity);
    }

    /**
     * Find by id role entity.
     *
     * @param roleId the role id
     * @return the role entity
     */
    @Override
    public RoleEntity findById(final UUID roleId) {
        return this.roleRepository.findById(roleId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<RoleEntity> findAll() {
        return (List<RoleEntity>) this.roleRepository.findAll();
    }

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    @Override
    public Page<RoleEntity> findByPagination(int pageNo, int pageSize) {
        return this.roleRepository.findAll(PageRequest.of(pageNo, pageSize,
                Sort.by("name").ascending()));
    }

    /**
     * Delete by id.
     *
     * @param roleId the role id
     */
    @Override
    public void deleteById(UUID roleId) {
        this.roleRepository.deleteById(roleId);
    }

    /**
     * Delete all by id.
     *
     * @param roleIds the role ids
     */
    @Override
    public void deleteAllById(List<UUID> roleIds) {
        this.roleRepository.deleteAllById(roleIds);
    }

    /**
     * Save role entity role entity.
     *
     * @param roleEntity the role entity
     * @return the role entity
     */
    private RoleEntity saveRoleEntity(final RoleEntity roleEntity) {
        return this.roleRepository.save(roleEntity);
    }
}