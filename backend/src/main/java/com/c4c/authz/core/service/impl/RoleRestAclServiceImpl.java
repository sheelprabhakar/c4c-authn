package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.RoleRestAclEntity;
import com.c4c.authz.core.entity.RoleRestAclId;
import com.c4c.authz.core.repository.RoleRestAclRepository;
import com.c4c.authz.core.service.api.RoleRestAclService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Role rest acl service.
 */
@Service
@Slf4j
@Transactional
public class RoleRestAclServiceImpl implements RoleRestAclService {
    /**
     * The Role rest acl repository.
     */
    private final RoleRestAclRepository roleRestAclRepository;

    /**
     * Instantiates a new Role rest acl service.
     *
     * @param roleRestAclRepository the role rest acl repository
     */
    public RoleRestAclServiceImpl(final RoleRestAclRepository roleRestAclRepository) {
        this.roleRestAclRepository = roleRestAclRepository;
    }

    /**
     * Create role rest acl entity.
     *
     * @param userRoleEntity the user role entity
     * @return the role rest acl entity
     */
    @Override
    public RoleRestAclEntity create(final RoleRestAclEntity userRoleEntity) {
        userRoleEntity.created(CurrentUserContext.getCurrentUser());
        return this.saveRoleAttributeEntity(userRoleEntity);
    }

    /**
     * Update role rest acl entity.
     *
     * @param userRoleEntity the user role entity
     * @return the role rest acl entity
     */
    @Override
    public RoleRestAclEntity update(final RoleRestAclEntity userRoleEntity) {
        userRoleEntity.updated(CurrentUserContext.getCurrentUser());
        return this.saveRoleAttributeEntity(userRoleEntity);
    }

    /**
     * Find by id role rest acl entity.
     *
     * @param roleRestAclId the role rest acl id
     * @return the role rest acl entity
     */
    @Override
    public RoleRestAclEntity findById(final RoleRestAclId roleRestAclId) {
        return this.roleRestAclRepository.findById(roleRestAclId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<RoleRestAclEntity> findAll() {
        return (List<RoleRestAclEntity>) this.roleRestAclRepository.findAll();
    }

    /**
     * Find all by role id list.
     *
     * @param roleId the role id
     * @return the list
     */
    @Override
    public List<RoleRestAclEntity> findAllByRoleId(final UUID roleId) {
        return this.roleRestAclRepository.findAllByRoleId(roleId);
    }

    /**
     * Find by pagination page.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the page
     */
    @Override
    public Page<RoleRestAclEntity> findByPagination(final int pageIndex, final int pageSize) {
        return this.roleRestAclRepository.findAll(PageRequest.of(pageIndex, pageSize, Sort.unsorted()));
    }

    /**
     * Delete by id.
     *
     * @param roleRestAclId the role rest acl id
     */
    @Override
    public void deleteById(final RoleRestAclId roleRestAclId) {
        this.roleRestAclRepository.deleteById(roleRestAclId);
    }

    /**
     * Delete all by id.
     *
     * @param roleRestAclIds the role rest acl ids
     */
    @Override
    public void deleteAllById(final List<RoleRestAclId> roleRestAclIds) {
        this.roleRestAclRepository.deleteAllById(roleRestAclIds);
    }

    /**
     * Save role attribute entity role rest acl entity.
     *
     * @param userRoleEntity the user role entity
     * @return the role rest acl entity
     */
    private RoleRestAclEntity saveRoleAttributeEntity(final RoleRestAclEntity userRoleEntity) {
        return this.roleRestAclRepository.save(userRoleEntity);
    }
}
