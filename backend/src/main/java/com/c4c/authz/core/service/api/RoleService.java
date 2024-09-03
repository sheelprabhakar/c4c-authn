package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.RoleEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

/**
 * The interface Role service.
 */
public interface RoleService {
    /**
     * Create role entity.
     *
     * @param roleEntity the role entity
     * @return the role entity
     */
    RoleEntity create(RoleEntity roleEntity);

    /**
     * Update role entity.
     *
     * @param tenantEntity the tenant entity
     * @return the role entity
     */
    RoleEntity update(RoleEntity tenantEntity);

    /**
     * Find by id role entity.
     *
     * @param roleId the role id
     * @return the role entity
     */
    RoleEntity findById(UUID roleId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<RoleEntity> findAll();

    /**
     * Find by pagination page.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the page
     */
    Page<RoleEntity> findByPagination(int pageIndex, int pageSize);

    /**
     * Delete by id.
     *
     * @param roleId the role id
     */
    void deleteById(UUID roleId);

    /**
     * Delete all by id.
     *
     * @param roleIds the role ids
     */
    void deleteAllById(List<UUID> roleIds);

    /**
     * Find by tenant id and name role entity.
     *
     * @param tenantId           the tenant id
     * @param clientCredRoleName the client cred role name
     * @return the role entity
     */
    RoleEntity findByTenantIdAndName(UUID tenantId, String clientCredRoleName);
}
