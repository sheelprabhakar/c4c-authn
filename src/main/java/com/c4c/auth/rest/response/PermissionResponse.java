package com.c4c.auth.rest.response;

import com.c4c.auth.core.models.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PermissionResponse {
  private Permission data;
}
