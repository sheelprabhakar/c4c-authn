package com.c4c.authn.core.service.impl;

import com.c4c.authn.core.entity.RoleEntity;
import com.c4c.authn.core.entity.UserEntity;
import com.c4c.authn.core.entity.UserRoleEntity;
import com.c4c.authn.core.repository.RoleRepository;
import com.c4c.authn.core.repository.UserRepository;
import com.c4c.authn.core.service.api.UserExDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The type User details service.
 */
@Service
@Slf4j
public final class UserDetailsServiceImpl implements UserExDetailsService {
    /**
     * The User repository.
     */
    private final UserRepository userRepository;
    /**
     * The Role repository.
     */
    private final RoleRepository roleRepository;

    /**
     * Instantiates a new User details service.
     *
     * @param userRepository the user repository
     * @param roleRepository the role repository
     */
    @Autowired
    public UserDetailsServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        final UserEntity user = this.userRepository.findByEmail(username);

        if (user == null) {
            log.info("User Not found");
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return this.loadUserByUsername(user);
    }

    /**
     * Load user by username user details.
     *
     * @param userEntity the user entity
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    public UserDetails loadUserByUsername(final UserEntity userEntity) throws UsernameNotFoundException {
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (UserRoleEntity e : userEntity.getRoles()) {
            RoleEntity entity = this.roleRepository.findById(e.getRoleId()).orElse(null);
            if (!Objects.isNull(entity)) {
                roleEntities.add(entity);
            }
        }
        return org.springframework.security.core.userdetails.User//
                .withUsername(userEntity.getEmail())//
                .password("no need")
                .authorities(roleEntities)//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }
}
