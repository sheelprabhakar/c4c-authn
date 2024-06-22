package com.c4c.auth.core.services.api;

import com.c4c.auth.core.models.entities.Permission;
import java.util.List;
import java.util.Optional;

public interface PermissionService {
  List<Permission> findAll();

  Optional<Permission> findById(String id);

  Optional<Permission> findByName(String id);
}
