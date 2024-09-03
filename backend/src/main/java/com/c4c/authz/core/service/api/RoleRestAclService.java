package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.RoleRestAclEntity;
import com.c4c.authz.core.entity.RoleRestAclId;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

/**
 * The interface Role rest acl service.
 */
public interface RoleRestAclService {
    /**
     * Create role rest acl entity.
     *
     * @param attributeEntity the attribute entity
     * @return the role rest acl entity
     */
    RoleRestAclEntity create(RoleRestAclEntity attributeEntity);

    /**
     * Update role rest acl entity.
     *
     * @param tenantEntity the tenant entity
     * @return the role rest acl entity
     */
    RoleRestAclEntity update(RoleRestAclEntity tenantEntity);

    /**
     * Find by id role rest acl entity.
     *
     * @param attributeId the attribute id
     * @return the role rest acl entity
     */
    RoleRestAclEntity findById(RoleRestAclId attributeId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<RoleRestAclEntity> findAll();

    /**
     * Find all by role id list.
     *
     * @param roleId the role id
     * @return the list
     */
    List<RoleRestAclEntity> findAllByRoleId(UUID roleId);

    /**
     * Find by pagination page.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the page
     */
    Page<RoleRestAclEntity> findByPagination(int pageIndex, int pageSize);

    /**
     * Delete by id.
     *
     * @param attributeId the attribute id
     */
    void deleteById(RoleRestAclId attributeId);

    /**
     * Delete all by id.
     *
     * @param roleRestAclIds the role rest acl ids
     */
    void deleteAllById(List<RoleRestAclId> roleRestAclIds);
}
