package com.c4c.auth.core.services.api;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.dtos.CreateUserDto;
import com.c4c.auth.core.models.dtos.UpdatePasswordDto;
import com.c4c.auth.core.models.dtos.UpdateUserDto;
import com.c4c.auth.core.models.entities.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User save(CreateUserDto createUserDto);

  List<User> findAll();

  void delete(String id);

  User findByEmail(String email) throws ResourceNotFoundException;

  User findById(String id) throws ResourceNotFoundException;

  User update(String id, UpdateUserDto updateUserDto) throws ResourceNotFoundException;

  void update(User user);

  User updatePassword(String id, UpdatePasswordDto updatePasswordDto)
      throws ResourceNotFoundException;

  void updatePassword(String id, String newPassword) throws ResourceNotFoundException;

  void confirm(String id) throws ResourceNotFoundException;
}
