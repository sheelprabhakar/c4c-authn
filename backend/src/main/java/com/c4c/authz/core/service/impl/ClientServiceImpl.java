package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.ClientEntity;
import com.c4c.authz.core.repository.ClientRepository;
import com.c4c.authz.core.service.api.ClientService;
import com.c4c.authz.core.service.api.SystemTenantService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Instantiates a new Client service.
     *
     * @param systemTenantService the system tenant service
     * @param clientRepository    the client repository
     */
    public ClientServiceImpl(final SystemTenantService systemTenantService, final ClientRepository clientRepository) {
        this.systemTenantService = systemTenantService;
        this.clientRepository = clientRepository;
    }

    /**
     * Create client entity.
     *
     * @param clientEntity the client entity
     * @return the client entity
     */
    @Override
    public ClientEntity create(final ClientEntity clientEntity) {
        clientEntity.created(CurrentUserContext.getCurrentUser());
        return this.saveRoleEntity(clientEntity);
    }

    /**
     * Update client entity.
     *
     * @param clientEntity the client entity
     * @return the client entity
     */
    @Override
    public ClientEntity update(final ClientEntity clientEntity) {
        clientEntity.updated(CurrentUserContext.getCurrentUser());
        return this.saveRoleEntity(clientEntity);
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
     * @param roleId the role id
     * @return the client entity
     */
    @Override
    public ClientEntity findById(final UUID roleId) {
        return this.clientRepository.findById(roleId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<ClientEntity> findAll() {
        if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenant())) {
            return (List<ClientEntity>) this.clientRepository.findAll();
        } else {
            return this.clientRepository.findAllByTenantId(CurrentUserContext.getCurrentTenant());
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
        if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenant())) {
            return this.clientRepository.findAll(pageRequest);
        } else {
            return this.clientRepository.findAllByTenantId(pageRequest, CurrentUserContext.getCurrentTenant());
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
     * Save role entity client entity.
     *
     * @param clientEntity the client entity
     * @return the client entity
     */
    private ClientEntity saveRoleEntity(final ClientEntity clientEntity) {
        return this.clientRepository.save(clientEntity);
    }
}
