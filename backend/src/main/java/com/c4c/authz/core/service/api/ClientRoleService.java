package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.ClientRoleEntity;
import com.c4c.authz.core.entity.ClientRoleId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

/**
 * The interface Client role service.
 */
public interface ClientRoleService {
    /**
     * Create client role entity.
     *
     * @param clientRoleEntity the client role entity
     * @return the client role entity
     */
    ClientRoleEntity create(ClientRoleEntity clientRoleEntity);

    /**
     * Update client role entity.
     *
     * @param clientRoleEntity the client role entity
     * @return the client role entity
     */
    ClientRoleEntity update(ClientRoleEntity clientRoleEntity);

    /**
     * Find by client id list.
     *
     * @param clientId the client id
     * @return the list
     */
    List<ClientRoleEntity> findByClientId(UUID clientId);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<ClientRoleEntity> findAll();

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<ClientRoleEntity> findByPagination(int pageNo, int pageSize);

    /**
     * Delete by id.
     *
     * @param clientRoleId the client role id
     */
    void deleteById(ClientRoleId clientRoleId);

    /**
     * Delete all by id.
     *
     * @param clientRoleIds the client role ids
     */
    void deleteAllById(List<ClientRoleId> clientRoleIds);

    /**
     * Delete by client id.
     *
     * @param clientId the client id
     */
    void deleteByClientId(UUID clientId);
}
