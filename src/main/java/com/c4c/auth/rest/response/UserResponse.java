package com.c4c.auth.rest.response;

import com.c4c.auth.core.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type UserResponse.
 */
@AllArgsConstructor
@Setter
@Getter
public class UserResponse {
  private User data;
}
