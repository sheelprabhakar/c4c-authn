package com.c4c.auth.core.services.api;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.dtos.CreateRoleDto;
import com.c4c.auth.core.models.entities.Role;
import java.util.List;

/**
 * The interface RoleService.
 */
public interface RoleService {
  /**
   * Save role.
   *
   * @param role the role
   * @return the role
   */
  Role save(CreateRoleDto role);

  /**
   * Find all list.
   *
   * @return the list
   */
  List<Role> findAll();

  /**
   * Delete.
   *
   * @param id the id
   */
  void delete(String id);

  /**
   * Find by name role.
   *
   * @param name the name
   * @return the role
   * @throws ResourceNotFoundException the resource not found exception
   */
  Role findByName(String name) throws ResourceNotFoundException;

  /**
   * Find by id role.
   *
   * @param id the id
   * @return the role
   * @throws ResourceNotFoundException the resource not found exception
   */
  Role findById(String id) throws ResourceNotFoundException;

  /**
   * Update role.
   *
   * @param id            the id
   * @param createRoleDto the create role dto
   * @return the role
   * @throws ResourceNotFoundException the resource not found exception
   */
  Role update(String id, CreateRoleDto createRoleDto) throws ResourceNotFoundException;

  /**
   * Update role.
   *
   * @param role the role
   * @return the role
   */
  Role update(Role role);
}
