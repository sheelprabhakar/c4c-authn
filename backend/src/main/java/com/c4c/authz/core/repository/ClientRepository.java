package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.ClientEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Client repository.
 */
@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, UUID> {

    /**
     * Find by tenant id and client id optional.
     *
     * @param tenantId the tenant id
     * @param clientId the client id
     * @return the optional
     */
    Optional<ClientEntity> findByTenantIdAndClientId(UUID tenantId, String clientId);

    /**
     * Find by tenant id and name optional.
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return the optional
     */
    Optional<ClientEntity> findByTenantIdAndName(UUID tenantId, String name);

    /**
     * Find all by tenant id page.
     *
     * @param pageable the pageable
     * @param tenantId the tenant id
     * @return the page
     */
    Page<ClientEntity> findAllByTenantId(Pageable pageable, UUID tenantId);

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<ClientEntity> findAll(Pageable pageable);

    /**
     * Find all by tenant id list.
     *
     * @param tenantId the tenant id
     * @return the list
     */
    List<ClientEntity> findAllByTenantId(UUID tenantId);

    /**
     * Find by client id optional.
     *
     * @param clientId the client id
     * @return the optional
     */
    Optional<ClientEntity> findByClientId(String clientId);
}
