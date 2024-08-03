package com.c4c.authn.core.service.impl;

import com.c4c.authn.common.CurrentUserContext;
import com.c4c.authn.core.entity.UserRoleEntity;
import com.c4c.authn.core.entity.UserRoleId;
import com.c4c.authn.core.repository.UserRoleRepository;
import com.c4c.authn.core.service.api.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type User role service.
 */
@Service
@Slf4j
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    /**
     * The User role repository.
     */
    private final UserRoleRepository userRoleRepository;

    /**
     * Instantiates a new User role service.
     *
     * @param userRoleRepository the user role repository
     */
    public UserRoleServiceImpl(final UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    /**
     * Create user role entity.
     *
     * @param userRoleEntity the user role entity
     * @return the user role entity
     */
    @Override
    public UserRoleEntity create(final UserRoleEntity userRoleEntity) {
        userRoleEntity.created(CurrentUserContext.getCurrentUser());
        return this.saveUserRoleEntity(userRoleEntity);
    }

    /**
     * Update user role entity.
     *
     * @param userRoleEntity the user role entity
     * @return the user role entity
     */
    @Override
    public UserRoleEntity update(final UserRoleEntity userRoleEntity) {
        userRoleEntity.updated(CurrentUserContext.getCurrentUser());
        return this.saveUserRoleEntity(userRoleEntity);
    }

    /**
     * Find by id user role entity.
     *
     * @param userRoleId the user role id
     * @return the user role entity
     */
    @Override
    public UserRoleEntity findById(final UserRoleId userRoleId) {
        return this.userRoleRepository.findById(userRoleId).orElse(null);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<UserRoleEntity> findAll() {
        return (List<UserRoleEntity>) this.userRoleRepository.findAll();
    }

    /**
     * Find by pagination page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    @Override
    public Page<UserRoleEntity> findByPagination(final int pageNo, final int pageSize) {
        return this.userRoleRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.unsorted()));
    }

    /**
     * Delete by id.
     *
     * @param userRoleId the user role id
     */
    @Override
    public void deleteById(final UserRoleId userRoleId) {
        this.userRoleRepository.deleteById(userRoleId);
    }

    /**
     * Delete all by id.
     *
     * @param userRoleIds the user role ids
     */
    @Override
    public void deleteAllById(final List<UserRoleId> userRoleIds) {
        this.userRoleRepository.deleteAllById(userRoleIds);
    }

    /**
     * Save user role entity user role entity.
     *
     * @param userRoleEntity the user role entity
     * @return the user role entity
     */
    private UserRoleEntity saveUserRoleEntity(final UserRoleEntity userRoleEntity) {
        return this.userRoleRepository.save(userRoleEntity);
    }
}
