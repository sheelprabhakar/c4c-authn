package com.c4c.auth.core.services.api;

import com.c4c.auth.core.models.entities.Permission;
import java.util.List;
import java.util.Optional;

/**
 * The interface PermissionService.
 */
public interface PermissionService {
  /**
   * Find all list.
   *
   * @return the list
   */
  List<Permission> findAll();

  /**
   * Find by id optional.
   *
   * @param id the id
   * @return the optional
   */
  Optional<Permission> findById(String id);

  /**
   * Find by name optional.
   *
   * @param id the id
   * @return the optional
   */
  Optional<Permission> findByName(String id);
}
