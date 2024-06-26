package com.c4c.authn.core.repository;

import com.c4c.authn.core.entity.UserTokenEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User token repository.
 */
@Repository
public interface UserTokenRepository extends CrudRepository<UserTokenEntity, UUID> {
}
