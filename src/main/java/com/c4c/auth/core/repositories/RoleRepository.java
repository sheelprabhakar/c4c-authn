package com.c4c.auth.core.repositories;

import com.c4c.auth.core.models.entities.Role;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface RoleRepository.
 */
@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {
  /**
   * Find by name optional.
   *
   * @param name the name
   * @return the optional
   */
  Optional<Role> findByName(String name);
}
