package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.RoleEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Role repository.
 */
@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, UUID> {
}
