package com.c4c.auth.core.services.api;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.entities.User;
import com.c4c.auth.core.models.entities.UserAccount;
import java.util.List;

/**
 * The interface UserAccountService.
 */
public interface UserAccountService {
  /**
   * Save user account.
   *
   * @param user  the user
   * @param token the token
   * @return the user account
   */
  UserAccount save(User user, String token);

  /**
   * Find all list.
   *
   * @return the list
   */
  List<UserAccount> findAll();

  /**
   * Delete.
   *
   * @param id the id
   */
  void delete(String id);

  /**
   * Find by token user account.
   *
   * @param token the token
   * @return the user account
   * @throws ResourceNotFoundException the resource not found exception
   */
  UserAccount findByToken(String token) throws ResourceNotFoundException;

  /**
   * Find by id user account.
   *
   * @param id the id
   * @return the user account
   * @throws ResourceNotFoundException the resource not found exception
   */
  UserAccount findById(String id) throws ResourceNotFoundException;
}
