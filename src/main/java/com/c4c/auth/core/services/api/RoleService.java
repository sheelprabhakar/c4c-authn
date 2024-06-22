package com.c4c.auth.core.services.api;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.dtos.CreateRoleDto;
import com.c4c.auth.core.models.entities.Role;
import java.util.List;

public interface RoleService {
  Role save(CreateRoleDto role);

  List<Role> findAll();

  void delete(String id);

  Role findByName(String name) throws ResourceNotFoundException;

  Role findById(String id) throws ResourceNotFoundException;

  Role update(String id, CreateRoleDto createRoleDto) throws ResourceNotFoundException;

  Role update(Role role);
}
