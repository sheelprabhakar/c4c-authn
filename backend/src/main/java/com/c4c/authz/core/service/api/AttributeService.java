package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.AttributeEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

/**
 * The interface Attribute service.
 */
public interface AttributeService {
    /**
     * Create attribute entity.
     *
     * @param restResource the rest resource
     * @return the attribute entity
     */
    AttributeEntity create(AttributeEntity restResource);

    /**
     * Update attribute entity.
     *
     * @param attributeEntity the attribute entity
     * @return the attribute entity
     */
    AttributeEntity update(AttributeEntity attributeEntity);

    /**
     * Find by id attribute entity.
     *
     * @param resourceId the resource id
     * @return the attribute entity
     */
    AttributeEntity findById(UUID resourceId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<AttributeEntity> findAll();

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<AttributeEntity> findByPagination(int pageIndex, int pageSize);

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
