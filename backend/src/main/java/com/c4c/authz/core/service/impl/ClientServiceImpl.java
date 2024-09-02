package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.common.OAuth2ClientIdGenerator;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.entity.ClientRoleEntity;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.repository.ClientRepository;
import com.c4c.authz.core.service.api.ClientRoleService;
import com.c4c.authz.core.service.api.ClientService;
import com.c4c.authz.core.service.api.RoleService;
import com.c4c.authz.core.service.api.SystemTenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.c4c.authz.common.Constants.CLIENT_CRED_ROLE_NAME;
import static com.c4c.authz.common.Constants.SYSTEM_TENANT;

/**
 * The type Client service.
 */
@Service
@Slf4j
@Transactional
public class ClientServiceImpl implements ClientService {
    /**
     * The System tenant service.
     */
    private final SystemTenantService systemTenantService;
    /**
     * The Client repository.
     */
    private final ClientRepository clientRepository;

    /**
     * The Role service.
     */
    private final RoleService roleService;

    /**
     * The Client role service.
     */
    private final ClientRoleService clientRoleService;

    /**
     * Instantiates a new Client service.
     *
     * @param systemTenantService the system tenant service
     * @param clientRepository    the client repository
     * @param roleService         the role service
     * @param clientRoleService   the client role service
     */
    public ClientServiceImpl(final SystemTenantService systemTenantService, final ClientRepository clientRepository,
                             final RoleService roleService, final ClientRoleService clientRoleService) {
        this.systemTenantService = systemTenantService;
        this.clientRepository = clientRepository;
        this.roleService = roleService;
        this.clientRoleService = clientRoleService;
    }

    /**
     * Create client entity.
     *
     * @param clientEntity the client entity
     * @return the client entity
     */
    @Override
    public ClientEntity create(final ClientEntity clientEntity) {
        clientEntity.setClientId(OAuth2ClientIdGenerator.generateClientId());
        String clientSecret = OAuth2ClientIdGenerator.generateClientSecret();
        clientEntity.setClientSecret(clientSecret);
        this.saveClientEntity(clientEntity);
        this.createDefaultClientRole(clientEntity);
        return clientEntity;
    }

    /**
     * Update client entity.
     *
     * @param clientEntity the client entity
     * @return the client entity
     */
    @Override
    public ClientEntity update(final ClientEntity clientEntity) {
        ClientEntity existingClientEntity = this.findById(clientEntity.getId());
        existingClientEntity.setName(clientEntity.getName());
        existingClientEntity.updated(CurrentUserContext.getCurrentUser());
        return this.saveClientEntity(existingClientEntity);
    }

    /**
     * Find by tenant id and name client entity.
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return the client entity
     */
    @Override
    public ClientEntity findByTenantIdAndName(final UUID tenantId, final String name) {
        return this.clientRepository.findByTenantIdAndName(tenantId, name).orElse(null);
    }

    /**
     * Find by tenant id and client id client entity.
     *
     * @param tenantId the tenant id
     * @param clientId the client id
     * @return the client entity
     */
    @Override
    public ClientEntity findByTenantIdAndClientId(final UUID tenantId, final String clientId) {
        return this.clientRepository.findByTenantIdAndClientId(tenantId, clientId).orElse(null);
    }

    /**
     * Delete.
     *
     * @param clientEntity the client entity
     */
    @Override
    public void delete(final ClientEntity clientEntity) {
        this.clientRepository.delete(clientEntity);
    }

    /**
     * Find by id client entity.
     *
     * @param clientId the client id
     * @return the client entity
     */
    @Override
    public ClientEntity findById(final UUID clientId) {
        return this.clientRepository.findById(clientId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<ClientEntity> findAll() {
        if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenantId())) {
            return (List<ClientEntity>) this.clientRepository.findAll();
        } else {
            return this.clientRepository.findAllByTenantId(CurrentUserContext.getCurrentTenantId());
        }
    }

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    @Override
    public Page<ClientEntity> findByPagination(final int pageNo, final int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("name").ascending());
        if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenantId())) {
            return this.clientRepository.findAll(pageRequest);
        } else {
            return this.clientRepository.findAllByTenantId(pageRequest, CurrentUserContext.getCurrentTenantId());
        }
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Override
    public void deleteById(final UUID id) {
        this.clientRepository.deleteById(id);
    }

    /**
     * Delete all by id.
     *
     * @param ids the ids
     */
    @Override
    public void deleteAllById(final List<UUID> ids) {
        this.clientRepository.deleteAllById(ids);
    }

    /**
     * Find by client id client entity.
     *
     * @param clientId the client id
     * @return the client entity
     */
    @Override
    public ClientEntity findByClientId(String clientId) {
        return this.clientRepository.findByClientId(clientId).orElse(null);
    }

    /**
     * Save client entity client entity.
     *
     * @param clientEntity the client entity
     * @return the client entity
     */
    private ClientEntity saveClientEntity(final ClientEntity clientEntity) {
        clientEntity.created(CurrentUserContext.getCurrentUser());
        return this.clientRepository.save(clientEntity);
    }

    /**
     * Create default client role.
     *
     * @param clientEntity the client entity
     */
    private void createDefaultClientRole(final ClientEntity clientEntity) {
        RoleEntity roleEntity =
                this.roleService.findByTenantIdAndName(clientEntity.getTenantId(), CLIENT_CRED_ROLE_NAME);
        ClientRoleEntity clientRoleEntity = new ClientRoleEntity();
        clientRoleEntity.setClientEntity(clientEntity);
        clientRoleEntity.setRoleEntity(roleEntity);
        clientRoleEntity.setRoleId(roleEntity.getId());
        clientRoleEntity.setClientId(clientEntity.getId());
        clientRoleEntity.setDeleted(false);
        clientRoleEntity.created(SYSTEM_TENANT);

        this.clientRoleService.create(clientRoleEntity);
    }
}
