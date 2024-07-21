package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The interface User ex details service.
 */
public interface UserExDetailsService extends UserDetailsService {
  /**
   * Load user by username user details.
   *
   * @param userEntity the user entity
   * @return the user details
   * @throws UsernameNotFoundException the username not found exception
   */
  UserDetails loadUserByUsername(UserEntity userEntity) throws UsernameNotFoundException;
}
