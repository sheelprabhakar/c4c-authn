package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.RoleAttributeEntity;
import com.c4c.authn.core.entity.RoleAttributeId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Role attribute repository.
 */
@Repository
public interface RoleAttributeRepository extends CrudRepository<RoleAttributeEntity, RoleAttributeId> {
    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<RoleAttributeEntity> findAll(Pageable pageable);
}
