package com.c4c.auth.core.models.entities;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The type Role.
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Document(collection = "roles")
public class Role extends BaseModel {
  @Field(name = "name")
  private String name;

  private String description;

  private boolean isDefault;

  @DBRef
  private Set<Permission> permissions;

  /**
   * Instantiates a new Role.
   */
  public Role() {
    permissions = new HashSet<>();
  }

  /**
   * Add permission role.
   *
   * @param permission the permission
   * @return the role
   */
  public Role addPermission(Permission permission) {
    this.permissions.add(permission);

    return this;
  }

  /**
   * Has permission boolean.
   *
   * @param permissionName the permission name
   * @return the boolean
   */
  public boolean hasPermission(String permissionName) {
    Optional<Permission> permissionItem =
        this.permissions.stream().filter(permission -> permission.getName().equals(permissionName))
            .findFirst();

    return permissionItem.isPresent();
  }

  /**
   * Remove permission role.
   *
   * @param permission the permission
   * @return the role
   */
  public Role removePermission(Permission permission) {
    Stream<Permission> newPermissions = this.permissions.stream()
        .filter(permission1 -> !permission1.getName().equals(permission.getName()));

    this.permissions = newPermissions.collect(Collectors.toSet());

    return this;
  }
}
