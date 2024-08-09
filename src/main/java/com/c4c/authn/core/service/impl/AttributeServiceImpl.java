package com.c4c.authn.core.service.impl;

import com.c4c.authn.common.CurrentUserContext;
import com.c4c.authn.core.entity.AttributeEntity;
import com.c4c.authn.core.repository.AttributeRepository;
import com.c4c.authn.core.service.api.AttributeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Attribute service.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class AttributeServiceImpl implements AttributeService {

    /**
     * The Attribute repository.
     */
    private final AttributeRepository attributeRepository;

    /**
     * Instantiates a new Attribute service.
     *
     * @param attributeRepository the attribute repository
     */
    public AttributeServiceImpl(final AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    /**
     * Create attribute entity.
     *
     * @param attributeEntity the attribute entity
     * @return the attribute entity
     */
    @Override
    @Transactional(readOnly = false)
    public AttributeEntity create(final AttributeEntity attributeEntity) {
        attributeEntity.created(CurrentUserContext.getCurrentUser());
        return this.attributeRepository.save(attributeEntity);
    }

    /**
     * Update attribute entity.
     *
     * @param attributeEntity the attribute entity
     * @return the attribute entity
     */
    @Override
    @Transactional(readOnly = false)
    public AttributeEntity update(final AttributeEntity attributeEntity) {
        attributeEntity.updated(CurrentUserContext.getCurrentUser());
        return this.attributeRepository.save(attributeEntity);
    }

    /**
     * Find by id attribute entity.
     *
     * @param resourceId the resource id
     * @return the attribute entity
     */
    @Override
    public AttributeEntity findById(final UUID resourceId) {
        return this.attributeRepository.findById(resourceId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<AttributeEntity> findAll() {
        return (List<AttributeEntity>) this.attributeRepository.findAll();
    }

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    @Override
    public Page<AttributeEntity> findByPagination(final int pageNo, final int pageSize) {
        return this.attributeRepository.findAll(PageRequest.of(pageNo, pageSize,
                Sort.by("attributeName").ascending()));
    }

    /**
     * Delete by id.
     *
     * @param resourceId the resource id
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteById(final UUID resourceId) {
        this.attributeRepository.deleteById(resourceId);
    }

    /**
     * Delete all by id.
     *
     * @param resourceIds the resource ids
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteAllById(final List<UUID> resourceIds) {
        this.attributeRepository.deleteAllById(resourceIds);
    }
}
