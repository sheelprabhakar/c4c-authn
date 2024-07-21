package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.RestResourceEntity;

import java.util.List;
import java.util.UUID;

/**
 * The interface Rest resource service.
 */
public interface RestResourceService {
    /**
     * Create rest resource entity.
     *
     * @param map the map
     * @return the rest resource entity
     */
    RestResourceEntity create(RestResourceEntity map);

    /**
     * Update rest resource entity.
     *
     * @param restResourceEntity the rest resource entity
     * @return the rest resource entity
     */
    RestResourceEntity update(RestResourceEntity restResourceEntity);

    /**
     * Read rest resource entity.
     *
     * @param resourceId the resource id
     * @return the rest resource entity
     */
    RestResourceEntity read(UUID resourceId);

    /**
     * Read all list.
     *
     * @return the list
     */
    List<RestResourceEntity> readAll();
}
