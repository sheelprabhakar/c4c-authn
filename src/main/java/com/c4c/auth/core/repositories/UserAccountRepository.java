package com.c4c.auth.core.repositories;

import com.c4c.auth.core.models.entities.UserAccount;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface UserAccountRepository.
 */
@Repository
public interface UserAccountRepository extends MongoRepository<UserAccount, ObjectId> {
  /**
   * Find by token optional.
   *
   * @param token the token
   * @return the optional
   */
  Optional<UserAccount> findByToken(String token);
}
