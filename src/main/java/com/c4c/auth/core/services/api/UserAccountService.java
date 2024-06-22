package com.c4c.auth.core.services.api;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.entities.User;
import com.c4c.auth.core.models.entities.UserAccount;
import java.util.List;

public interface UserAccountService {
  UserAccount save(User user, String token);

  List<UserAccount> findAll();

  void delete(String id);

  UserAccount findByToken(String token) throws ResourceNotFoundException;

  UserAccount findById(String id) throws ResourceNotFoundException;
}
