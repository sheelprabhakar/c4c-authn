package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.ClientEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

/**
 * The interface Client service.
 */
public interface ClientService {
    /**
     * Create client entity.
     *
     * @param clientEntity the client entity
     * @return the client entity
     */
    ClientEntity create(ClientEntity clientEntity);

    /**
     * Find by id client entity.
     *
     * @param id the id
     * @return the client entity
     */
    ClientEntity findById(UUID id);

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<ClientEntity> findByPagination(int pageIndex, int pageSize);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<ClientEntity> findAll();

    /**
     * Update client entity.
     *
     * @param clientEntity the client entity
     * @return the client entity
     */
    ClientEntity update(ClientEntity clientEntity);


    /**
     * Find by tenant id and name client entity.
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return the client entity
     */
    ClientEntity findByTenantIdAndName(UUID tenantId, String name);

    /**
     * Find by tenant id and client id client entity.
     *
     * @param tenantId the tenant id
     * @param clientId the client id
     * @return the client entity
     */
    ClientEntity findByTenantIdAndClientId(UUID tenantId, String clientId);

    /**
     * Delete.
     *
     * @param clientEntity the client entity
     */
    void delete(ClientEntity clientEntity);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(UUID id);

    /**
     * Delete all by id.
     *
     * @param ids the ids
     */
    void deleteAllById(List<UUID> ids);

    /**
     * Find by client id client entity.
     *
     * @param clientId the client id
     * @return the client entity
     */
    ClientEntity findByClientId(String clientId);
}
