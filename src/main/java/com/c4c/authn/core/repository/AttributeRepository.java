package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.AttributeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The interface Attribute repository.
 */
@Repository
public interface AttributeRepository extends CrudRepository<AttributeEntity, UUID> {
    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<AttributeEntity> findAll(Pageable pageable);

    /**
     * Find all by tenant id list.
     *
     * @param tenantId the tenant id
     * @return the list
     */
    List<AttributeEntity> findAllByTenantId(UUID tenantId);

    /**
     * Find all by tenant id page.
     *
     * @param pageable the pageable
     * @param tenantId the tenant id
     * @return the page
     */
    Page<AttributeEntity> findAllByTenantId(Pageable pageable, UUID tenantId);
}
