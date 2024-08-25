package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The interface Role repository.
 */
@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, UUID> {
    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<RoleEntity> findAll(Pageable pageable);

    /**
     * Find all by tenant id list.
     *
     * @param tenantId the tenant id
     * @return the list
     */
    List<RoleEntity> findAllByTenantId(UUID tenantId);

    /**
     * Find all by tenant id page.
     *
     * @param pageable the pageable
     * @param tenantId the tenant id
     * @return the page
     */
    Page<RoleEntity> findAllByTenantId(Pageable pageable, UUID tenantId);

}
