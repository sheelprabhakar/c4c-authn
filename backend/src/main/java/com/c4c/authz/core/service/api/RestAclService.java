package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.RestAclEntity;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

/**
 * The interface Rest acl service.
 */
public interface RestAclService {
    /**
     * Create rest acl entity.
     *
     * @param restResource the rest resource
     * @return the rest acl entity
     */
    RestAclEntity create(RestAclEntity restResource);

    /**
     * Update rest acl entity.
     *
     * @param restAclEntity the rest acl entity
     * @return the rest acl entity
     */
    RestAclEntity update(RestAclEntity restAclEntity);

    /**
     * Find by id rest acl entity.
     *
     * @param resourceId the resource id
     * @return the rest acl entity
     */
    RestAclEntity findById(UUID resourceId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<RestAclEntity> findAll();

    /**
     * Find by pagination page.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the page
     */
    Page<RestAclEntity> findByPagination(int pageIndex, int pageSize);

    /**
     * Delete by id.
     *
     * @param resourceId the resource id
     */
    void deleteById(UUID resourceId);

    /**
     * Delete all by id.
     *
     * @param resourceIds the resource ids
     */
    void deleteAllById(List<UUID> resourceIds);
}
