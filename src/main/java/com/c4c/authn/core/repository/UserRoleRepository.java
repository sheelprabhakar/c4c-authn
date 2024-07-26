package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.UserRoleEntity;
import com.c4c.authn.core.entity.UserRoleId;
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
}
