package com.c4c.auth.rest.response;

import com.c4c.auth.core.models.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type PermissionResponse.
 */
@AllArgsConstructor
@Setter
@Getter
public class PermissionResponse {
  private Permission data;
}
