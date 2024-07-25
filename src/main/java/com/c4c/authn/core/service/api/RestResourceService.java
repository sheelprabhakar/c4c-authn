package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.RestResourceEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * The interface Rest resource service.
 */
public interface RestResourceService {
    /**
     * Create rest resource entity.
     *
     * @param restResource the rest resource
     * @return the rest resource entity
     */
    RestResourceEntity create(RestResourceEntity restResource);

    /**
     * Update rest resource entity.
     *
     * @param restResourceEntity the rest resource entity
     * @return the rest resource entity
     */
    RestResourceEntity update(RestResourceEntity restResourceEntity);

    /**
     * Find by id rest resource entity.
     *
     * @param resourceId the resource id
     * @return the rest resource entity
     */
    RestResourceEntity findById(UUID resourceId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<RestResourceEntity> findAll();

    Page<RestResourceEntity> findByPagination(int pageNo, int pageSize);

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
