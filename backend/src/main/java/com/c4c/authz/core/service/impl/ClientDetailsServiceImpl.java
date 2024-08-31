package com.c4c.authz.core.service.impl;

import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.entity.ClientRoleEntity;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.repository.ClientRepository;
import com.c4c.authz.core.repository.RoleRepository;
import com.c4c.authz.core.service.api.ClientExDetailsService;
import com.c4c.authz.core.service.api.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The type Client details service.
 */
@Service
@Transactional
@Slf4j
public class ClientDetailsServiceImpl implements ClientExDetailsService {
    /**
     * The Client service.
     */
    private final ClientService clientService;

    /**
     * Instantiates a new Client details service.
     *
     * @param clientService the client service
     */
    @Autowired
    public ClientDetailsServiceImpl(final ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Load user by username user details.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final ClientEntity client = this.clientService.findByClientId(username);
        if (client == null) {
            log.info("Client Not found");
            throw new UsernameNotFoundException("Client '" + username + "' not found");
        }
        return this.loadClientByClient(client);
    }

    /**
     * Load client by client user details.
     *
     * @param clientEntity the client entity
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    public UserDetails loadClientByClient(final ClientEntity clientEntity) throws UsernameNotFoundException {
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (ClientRoleEntity e : clientEntity.getClientRoleEntities()) {
            RoleEntity entity = e.getRoleEntity();
            if (!Objects.isNull(entity)) {
                roleEntities.add(entity);
            }
        }
        return org.springframework.security.core.userdetails.User//
                .withUsername(clientEntity.getClientId())//
                .password("no need")
                .authorities(roleEntities)//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }
}
