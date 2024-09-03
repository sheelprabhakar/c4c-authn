package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.ClientRoleEntity;
import com.c4c.authz.core.entity.ClientRoleId;
import com.c4c.authz.core.repository.ClientRoleRepository;
import com.c4c.authz.core.service.api.ClientRoleService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Client role service.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class ClientRoleServiceImpl implements ClientRoleService {
    /**
     * The Client role repository.
     */
    private final ClientRoleRepository clientRoleRepository;

    /**
     * Instantiates a new Client role service.
     *
     * @param clientRoleRepository the client role repository
     */
    @Autowired
    public ClientRoleServiceImpl(final ClientRoleRepository clientRoleRepository) {
        this.clientRoleRepository = clientRoleRepository;
    }

    /**
     * Create client role entity.
     *
     * @param clientRoleEntity the client role entity
     * @return the client role entity
     */
    @Override
    @Transactional(readOnly = false)
    public ClientRoleEntity create(final ClientRoleEntity clientRoleEntity) {
        clientRoleEntity.created(CurrentUserContext.getCurrentUser());
        return this.clientRoleRepository.save(clientRoleEntity);
    }

    /**
     * Update client role entity.
     *
     * @param clientRoleEntity the client role entity
     * @return the client role entity
     */
    @Override
    @Transactional(readOnly = false)
    public ClientRoleEntity update(final ClientRoleEntity clientRoleEntity) {
        clientRoleEntity.updated(CurrentUserContext.getCurrentUser());
        return this.clientRoleRepository.save(clientRoleEntity);
    }

    /**
     * Find by client id list.
     *
     * @param clientId the client id
     * @return the list
     */
    @Override
    public List<ClientRoleEntity> findByClientId(final UUID clientId) {
        return this.clientRoleRepository.findByClientId(clientId);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<ClientRoleEntity> findAll() {
        return this.clientRoleRepository.findAll();
    }

    /**
     * Find by pagination page.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @return the page
     */
    @Override
    public Page<ClientRoleEntity> findByPagination(final int pageIndex, final int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by("clientId").ascending());
        return this.clientRoleRepository.findAll(pageRequest);
    }

    /**
     * Delete by id.
     *
     * @param clientRoleId the client role id
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteById(final ClientRoleId clientRoleId) {
        this.clientRoleRepository.deleteById(clientRoleId);
    }

    /**
     * Delete all by id.
     *
     * @param clientRoleIds the client role ids
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteAllById(final List<ClientRoleId> clientRoleIds) {
        this.clientRoleRepository.deleteAllById(clientRoleIds);
    }

    /**
     * Delete by client id.
     *
     * @param clientId the client id
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteByClientId(final UUID clientId) {
        this.clientRoleRepository.deleteAllInBatch(this.findByClientId(clientId));
    }
}
