package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.UserRoleEntity;
import com.c4c.authn.core.entity.UserRoleId;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * The interface User role service.
 */
public interface UserRoleService {
    /**
     * Create user role entity.
     *
     * @param map the map
     * @return the user role entity
     */
    UserRoleEntity create(UserRoleEntity map);

    /**
     * Update user role entity.
     *
     * @param tenantEntity the tenant entity
     * @return the user role entity
     */
    UserRoleEntity update(UserRoleEntity tenantEntity);

    /**
     * Find by id user role entity.
     *
     * @param userRoleId the user role id
     * @return the user role entity
     */
    UserRoleEntity findById(UserRoleId userRoleId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<UserRoleEntity> findAll();

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<UserRoleEntity> findByPagination(int pageNo, int pageSize);

    /**
     * Delete by id.
     *
     * @param roleId the role id
     */
    void deleteById(UserRoleId roleId);

    /**
     * Delete all by id.
     *
     * @param roleIds the role ids
     */
    void deleteAllById(List<UserRoleId> roleIds);
}