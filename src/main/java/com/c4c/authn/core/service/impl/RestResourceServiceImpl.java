package com.c4c.authn.core.service.impl;

import com.c4c.authn.core.entity.RestResourceEntity;
import com.c4c.authn.core.repository.RestResourceRepository;
import com.c4c.authn.core.service.api.RestResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Rest resource service.
 */
@Service
@Slf4j
@Transactional
public class RestResourceServiceImpl implements RestResourceService {

    /**
     * The Rest resource repository.
     */
    private final RestResourceRepository restResourceRepository;

    /**
     * Instantiates a new Rest resource service.
     *
     * @param restResourceRepository the rest resource repository
     */
    public RestResourceServiceImpl(final RestResourceRepository restResourceRepository) {
        this.restResourceRepository = restResourceRepository;
    }

    /**
     * Create rest resource entity.
     *
     * @param restResourceEntity the rest resource entity
     * @return the rest resource entity
     */
    @Override
    public RestResourceEntity create(final RestResourceEntity restResourceEntity) {
        return this.restResourceRepository.save(restResourceEntity);
    }

    /**
     * Update rest resource entity.
     *
     * @param restResourceEntity the rest resource entity
     * @return the rest resource entity
     */
    @Override
    public RestResourceEntity update(final RestResourceEntity restResourceEntity) {
        return this.restResourceRepository.save(restResourceEntity);
    }

    /**
     * Find by id rest resource entity.
     *
     * @param resourceId the resource id
     * @return the rest resource entity
     */
    @Override
    public RestResourceEntity findById(final UUID resourceId) {
        return this.restResourceRepository.findById(resourceId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<RestResourceEntity> findAll() {
        return (List<RestResourceEntity>) this.restResourceRepository.findAll();
    }

    /**
     * Delete by id.
     *
     * @param resourceId the resource id
     */
    @Override
    public void deleteById(UUID resourceId) {
        this.restResourceRepository.deleteById(resourceId);
    }

    /**
     * Delete all by id.
     *
     * @param resourceIds the resource ids
     */
    @Override
    public void deleteAllById(List<UUID> resourceIds) {
        this.restResourceRepository.deleteAllById(resourceIds);
    }
}
