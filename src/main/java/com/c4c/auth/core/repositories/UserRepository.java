package com.c4c.auth.core.repositories;

import com.c4c.auth.core.models.entities.User;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface UserRepository.
 */
@Repository(value = "com.c4c.auth.core.repositories.UserRepository")
public interface UserRepository extends MongoRepository<User, ObjectId> {
  /**
   * Find by email optional.
   *
   * @param email the email
   * @return the optional
   */
  Optional<User> findByEmail(String email);
}
