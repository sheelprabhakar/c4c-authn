package com.c4c.authz.core.service.api;

import com.c4c.authz.core.entity.ClientEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The interface Client ex details service.
 */
public interface ClientExDetailsService extends UserDetailsService {
    /**
     * Load client by client user details.
     *
     * @param clientEntity the client entity
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    UserDetails loadClientByClient(ClientEntity clientEntity) throws UsernameNotFoundException;
}
