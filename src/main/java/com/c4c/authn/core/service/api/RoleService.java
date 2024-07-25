package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.RoleEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * The interface Role service.
 */
public interface RoleService {
    /**
     * Create role entity.
     *
     * @param map the map
     * @return the role entity
     */
    RoleEntity create(RoleEntity map);

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
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<RoleEntity> findByPagination(int pageNo, int pageSize);

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
}