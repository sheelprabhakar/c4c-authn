package com.c4c.auth.core.services;

import com.c4c.auth.core.models.entities.Permission;
import com.c4c.auth.core.repositories.PermissionRepository;
import com.c4c.auth.core.services.api.PermissionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * The type PermissionServiceImpl.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
  private final PermissionRepository permissionRepository;

  /**
   * Instantiates a new Permission service.
   *
   * @param permissionRepository the permission repository
   */
  public PermissionServiceImpl(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  @Override
  public List<Permission> findAll() {
    List<Permission> list = new ArrayList<>();
    permissionRepository.findAll().iterator().forEachRemaining(list::add);

    return list;
  }

  @Override
  public Optional<Permission> findByName(String name) {
    return permissionRepository.findByName(name);
  }

  @Override
  public Optional<Permission> findById(String id) {
    return permissionRepository.findById(new ObjectId(id));
  }
}
