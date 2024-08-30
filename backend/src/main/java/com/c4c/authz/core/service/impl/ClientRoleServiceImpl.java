package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.ClientRoleEntity;
import com.c4c.authz.core.entity.ClientRoleId;
import com.c4c.authz.core.repository.ClientRoleRepository;
import com.c4c.authz.core.service.api.ClientRoleService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * The type Client role service.
 */
@Service
@Slf4j
@Transactional
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
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    @Override
    public Page<ClientRoleEntity> findByPagination(final int pageNo, final int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("clientId").ascending());
        return this.clientRoleRepository.findAll(pageRequest);
    }

    /**
     * Delete by id.
     *
     * @param clientRoleId the client role id
     */
    @Override
    public void deleteById(final ClientRoleId clientRoleId) {
        this.clientRoleRepository.deleteById(clientRoleId);
    }

    /**
     * Delete all by id.
     *
     * @param clientRoleIds the client role ids
     */
    @Override
    public void deleteAllById(final List<ClientRoleId> clientRoleIds) {
        this.clientRoleRepository.deleteAllById(clientRoleIds);
    }
}
