package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Role repository.
 */
@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, UUID> {
    Page<RoleEntity> findAll(Pageable pageable);
}
