package com.c4c.auth.rest.response;

import com.c4c.auth.core.models.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type RoleResponse.
 */
@AllArgsConstructor
@Setter
@Getter
public class RoleResponse {
  private Role data;
}
