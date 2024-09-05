package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.ClientRoleEntity;
import com.c4c.authz.core.entity.ClientRoleId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Client role repository.
 */
@Repository
public interface ClientRoleRepository extends JpaRepository<ClientRoleEntity, ClientRoleId> {
    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<ClientRoleEntity> findAll(Pageable pageRequest);

    /**
     * Find by client id list.
     *
     * @param clientId the client id
     * @return the list
     */
    List<ClientRoleEntity> findByClientId(UUID clientId);
}
