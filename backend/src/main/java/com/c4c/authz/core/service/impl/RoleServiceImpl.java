package com.c4c.authz.core.service.impl;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.repository.RoleRepository;
import com.c4c.authz.core.service.api.RoleService;
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
 * The type Role service.
 */
@Service
@Slf4j
@Transactional
public class RoleServiceImpl implements RoleService {
  /**
   * The System tenant service.
   */
  private final SystemTenantService systemTenantService;
  /**
   * The Role repository.
   */
  private final RoleRepository roleRepository;

  /**
   * Instantiates a new Role service.
   *
   * @param systemTenantService the system tenant service
   * @param roleRepository      the role repository
   */
  public RoleServiceImpl(final SystemTenantService systemTenantService, final RoleRepository roleRepository) {
    this.systemTenantService = systemTenantService;
    this.roleRepository = roleRepository;
  }

  /**
   * Create role entity.
   *
   * @param roleEntity the role entity
   * @return the role entity
   */
  @Override
  public RoleEntity create(final RoleEntity roleEntity) {
    roleEntity.created(CurrentUserContext.getCurrentUser());
    return this.saveRoleEntity(roleEntity);
  }

  /**
   * Update role entity.
   *
   * @param roleEntity the role entity
   * @return the role entity
   */
  @Override
  public RoleEntity update(final RoleEntity roleEntity) {
    roleEntity.updated(CurrentUserContext.getCurrentUser());
    return this.saveRoleEntity(roleEntity);
  }

  /**
   * Find by id role entity.
   *
   * @param roleId the role id
   * @return the role entity
   */
  @Override
  public RoleEntity findById(final UUID roleId) {
    return this.roleRepository.findById(roleId).orElse(null);
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  @Override
  public List<RoleEntity> findAll() {
    if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenantId())) {
      return (List<RoleEntity>) this.roleRepository.findAll();
    } else {
      return this.roleRepository.findAllByTenantId(CurrentUserContext.getCurrentTenantId());
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
  public Page<RoleEntity> findByPagination(final int pageNo, final int pageSize) {
    PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("name").ascending());
    if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenantId())) {
      return this.roleRepository.findAll(pageRequest);
    } else {
      return this.roleRepository.findAllByTenantId(pageRequest, CurrentUserContext.getCurrentTenantId());
    }
  }

  /**
   * Delete by id.
   *
   * @param roleId the role id
   */
  @Override
  public void deleteById(final UUID roleId) {
    this.roleRepository.deleteById(roleId);
  }

  /**
   * Delete all by id.
   *
   * @param roleIds the role ids
   */
  @Override
  public void deleteAllById(final List<UUID> roleIds) {
    this.roleRepository.deleteAllById(roleIds);
  }

  @Override
  public RoleEntity findByTenantIdAndName(final UUID tenantId, final String clientCredRoleName) {
    return this.roleRepository.findByTenantIdAndName(tenantId, clientCredRoleName).orElse(null);
  }

  /**
   * Save role entity role entity.
   *
   * @param roleEntity the role entity
   * @return the role entity
   */
  private RoleEntity saveRoleEntity(final RoleEntity roleEntity) {
    return this.roleRepository.save(roleEntity);
  }
}
