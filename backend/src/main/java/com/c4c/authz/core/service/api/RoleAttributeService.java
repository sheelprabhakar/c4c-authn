package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.RoleAttributeEntity;
import com.c4c.authz.core.entity.RoleAttributeId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

/**
 * The interface Role attribute service.
 */
public interface RoleAttributeService {
    /**
     * Create role attribute entity.
     *
     * @param attributeEntity the attribute entity
     * @return the role attribute entity
     */
    RoleAttributeEntity create(RoleAttributeEntity attributeEntity);

    /**
     * Update role attribute entity.
     *
     * @param tenantEntity the tenant entity
     * @return the role attribute entity
     */
    RoleAttributeEntity update(RoleAttributeEntity tenantEntity);

    /**
     * Find by id role attribute entity.
     *
     * @param attributeId the attribute id
     * @return the role attribute entity
     */
    RoleAttributeEntity findById(RoleAttributeId attributeId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<RoleAttributeEntity> findAll();

    /**
     * Find all by role id list.
     *
     * @param roleId the role id
     * @return the list
     */
    List<RoleAttributeEntity> findAllByRoleId(UUID roleId);

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<RoleAttributeEntity> findByPagination(int pageIndex, int pageSize);

    /**
     * Delete by id.
     *
     * @param attributeId the attribute id
     */
    void deleteById(RoleAttributeId attributeId);

    /**
     * Delete all by id.
     *
     * @param roleAttributeIds the role attribute ids
     */
    void deleteAllById(List<RoleAttributeId> roleAttributeIds);
}
