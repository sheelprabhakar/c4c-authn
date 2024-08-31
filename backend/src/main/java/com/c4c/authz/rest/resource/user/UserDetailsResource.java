package com.c4c.authz.rest.resource.user;

import com.c4c.authz.core.domain.PolicyRecord;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type User details resource.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDetailsResource {
  /**
   * The User info.
   */
  private UserResource userInfo;
  /**
   * The Policies.
   */
  private Set<PolicyRecord> policies;
}
