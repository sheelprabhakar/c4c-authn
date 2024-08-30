package com.c4c.authz.core.repository;

import com.c4c.authz.core.entity.UserRoleEntity;
import com.c4c.authz.core.entity.UserRoleId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User role repository.
 */
@Repository
public interface UserRoleRepository extends CrudRepository<UserRoleEntity, UserRoleId> {
    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<UserRoleEntity> findAll(Pageable pageable);

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<UserRoleEntity> findByUserId(UUID userId);
}
