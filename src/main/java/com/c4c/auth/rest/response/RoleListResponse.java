package com.c4c.auth.rest.response;

import com.c4c.auth.core.models.entities.Role;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type RoleListResponse.
 */
@AllArgsConstructor
@Setter
@Getter
public class RoleListResponse {
  private List<Role> data;
}
