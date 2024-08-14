package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
     * @param currentTenant the current tenant
     * @return the list
     */
    List<RoleEntity> findAllByTenantId(UUID currentTenant);

    /**
     * Find all by tenant id page.
     *
     * @param pageRequest   the page request
     * @param currentTenant the current tenant
     * @return the page
     */
    Page<RoleEntity> findAllByTenantId(PageRequest pageRequest, UUID currentTenant);
}
