package com.c4c.authn.core.service.impl;

import com.c4c.authn.common.CurrentUserContext;
import com.c4c.authn.core.entity.RoleAttributeEntity;
import com.c4c.authn.core.entity.RoleAttributeId;
import com.c4c.authn.core.repository.RoleAttributeRepository;
import com.c4c.authn.core.service.api.RoleAttributeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Role attribute service.
 */
@Service
@Slf4j
@Transactional
public class RoleAttributeServiceImpl implements RoleAttributeService {
    /**
     * The Role attribute repository.
     */
    private final RoleAttributeRepository roleAttributeRepository;

    /**
     * Instantiates a new Role attribute service.
     *
     * @param roleAttributeRepository the role attribute repository
     */
    public RoleAttributeServiceImpl(final RoleAttributeRepository roleAttributeRepository) {
        this.roleAttributeRepository = roleAttributeRepository;
    }

    /**
     * Create role attribute entity.
     *
     * @param userRoleEntity the user role entity
     * @return the role attribute entity
     */
    @Override
    public RoleAttributeEntity create(final RoleAttributeEntity userRoleEntity) {
        userRoleEntity.created(CurrentUserContext.getCurrentUser());
        return this.saveRoleAttributeEntity(userRoleEntity);
    }

    /**
     * Update role attribute entity.
     *
     * @param userRoleEntity the user role entity
     * @return the role attribute entity
     */
    @Override
    public RoleAttributeEntity update(final RoleAttributeEntity userRoleEntity) {
        userRoleEntity.updated(CurrentUserContext.getCurrentUser());
        return this.saveRoleAttributeEntity(userRoleEntity);
    }

    /**
     * Find by id role attribute entity.
     *
     * @param roleAttributeId the role attribute id
     * @return the role attribute entity
     */
    @Override
    public RoleAttributeEntity findById(final RoleAttributeId roleAttributeId) {
        return this.roleAttributeRepository.findById(roleAttributeId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<RoleAttributeEntity> findAll() {
        return (List<RoleAttributeEntity>) this.roleAttributeRepository.findAll();
    }

    /**
     * Find all by role id list.
     *
     * @param roleId the role id
     * @return the list
     */
    @Override
    public List<RoleAttributeEntity> findAllByRoleId(final UUID roleId) {
        return this.roleAttributeRepository.findAllByRoleId(roleId);
    }

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    @Override
    public Page<RoleAttributeEntity> findByPagination(final int pageNo, final int pageSize) {
        return this.roleAttributeRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.unsorted()));
    }

    /**
     * Delete by id.
     *
     * @param roleAttributeId the role attribute id
     */
    @Override
    public void deleteById(final RoleAttributeId roleAttributeId) {
        this.roleAttributeRepository.deleteById(roleAttributeId);
    }

    /**
     * Delete all by id.
     *
     * @param roleAttributeIds the role attribute ids
     */
    @Override
    public void deleteAllById(final List<RoleAttributeId> roleAttributeIds) {
        this.roleAttributeRepository.deleteAllById(roleAttributeIds);
    }

    /**
     * Save role attribute entity role attribute entity.
     *
     * @param userRoleEntity the user role entity
     * @return the role attribute entity
     */
    private RoleAttributeEntity saveRoleAttributeEntity(final RoleAttributeEntity userRoleEntity) {
        return this.roleAttributeRepository.save(userRoleEntity);
    }
}
