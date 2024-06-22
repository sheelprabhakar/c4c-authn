package com.c4c.auth.core.models.dtos;

import com.c4c.auth.core.models.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type PermissionLoadDto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionLoadDto {
  private String name;

  private String description;

  private String[] roleNames;

  /**
   * To permission permission.
   *
   * @return the permission
   */
  public Permission toPermission() {
    return new Permission(name, description);
  }
}
