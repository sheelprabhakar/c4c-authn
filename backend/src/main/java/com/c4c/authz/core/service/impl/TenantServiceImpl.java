package com.c4c.authz.core.service.impl;


import static com.c4c.authz.common.Constants.ANT_POLICY_URL;
import static com.c4c.authz.common.Constants.CLIENT_CRED_ROLE_NAME;
import static com.c4c.authz.common.Constants.POLICY;
import static com.c4c.authz.common.Constants.SYSTEM_TENANT;

import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.common.SpringUtil;
import com.c4c.authz.core.entity.AttributeEntity;
import com.c4c.authz.core.entity.RoleAttributeEntity;
import com.c4c.authz.core.entity.RoleEntity;
import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.entity.UserRoleEntity;
import com.c4c.authz.core.repository.TenantRepository;
import com.c4c.authz.core.service.api.AttributeService;
import com.c4c.authz.core.service.api.RoleAttributeService;
import com.c4c.authz.core.service.api.RoleService;
import com.c4c.authz.core.service.api.SystemTenantService;
import com.c4c.authz.core.service.api.TenantService;
import com.c4c.authz.core.service.api.UserRoleService;
import com.c4c.authz.core.service.api.UserService;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Tenant service.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class TenantServiceImpl implements TenantService {
  /**
   * The Tenant repository.
   */
  private final TenantRepository tenantRepository;

  /**
   * The User service.
   */
  private final UserService userService;

  /**
   * The Role service.
   */
  private final RoleService roleService;

  /**
   * The Attribute service.
   */
  private final AttributeService attributeService;

  /**
   * The Role attribute service.
   */
  private final RoleAttributeService roleAttributeService;
  /**
   * The System tenant service.
   */
  private final SystemTenantService systemTenantService;

  /**
   * The User role service.
   */
  private final UserRoleService userRoleService;

  /**
   * Instantiates a new Tenant service.
   *
   * @param tenantRepository     the tenant repository
   * @param userService          the user service
   * @param roleService          the role service
   * @param attributeService     the attribute service
   * @param roleAttributeService the role attribute service
   * @param systemTenantService  the system tenant service
   * @param userRoleService      the user role service
   */
  public TenantServiceImpl(final TenantRepository tenantRepository, final UserService userService,
                           final RoleService roleService, final AttributeService attributeService,
                           final RoleAttributeService roleAttributeService,
                           final SystemTenantService systemTenantService, final UserRoleService userRoleService) {
    this.tenantRepository = tenantRepository;
    this.userService = userService;
    this.roleService = roleService;
    this.attributeService = attributeService;
    this.roleAttributeService = roleAttributeService;
    this.systemTenantService = systemTenantService;
    this.userRoleService = userRoleService;
  }


  /**
   * Gets new user entity.
   *
   * @param tenantEntity the tenant entity
   * @return the new user entity
   */
  private static UserEntity getNewUserEntity(final TenantEntity tenantEntity) {

    return UserEntity.builder().userName(tenantEntity.getShortName()).email(tenantEntity.getEmail())
        .mobile(tenantEntity.getMobile()).firstName(tenantEntity.getShortName())
        .lastName(tenantEntity.getShortName()).passwordHash("admin123").build();
  }

  /**
   * Create tenant entity.
   *
   * @param tenantEntity the tenant entity
   * @return the tenant entity
   */
  @Override
  @Transactional(readOnly = false)
  public TenantEntity create(final TenantEntity tenantEntity) {
    tenantEntity.setActive(true);
    tenantEntity.created(CurrentUserContext.getCurrentUser());
    return this.saveTenantEntity(tenantEntity);
  }

  /**
   * Update tenant entity.
   *
   * @param tenantEntity the tenant entity
   * @return the tenant entity
   */
  @Override
  @Transactional(readOnly = false)
  public TenantEntity update(final TenantEntity tenantEntity) {
    tenantEntity.updated(CurrentUserContext.getCurrentUser());
    return this.saveTenantEntity(tenantEntity);
  }

  /**
   * Find by id tenant entity.
   *
   * @param tenantId the tenant id
   * @return the tenant entity
   */
  @Override
  public TenantEntity findById(final UUID tenantId) {
    return this.tenantRepository.findById(tenantId).orElse(null);
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  @Override
  public List<TenantEntity> findAll() {
    if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenantId())) {
      return this.tenantRepository.findAll();
    } else {
      return SpringUtil.fromSingleItem(
          this.tenantRepository.findById(CurrentUserContext.getCurrentTenantId()).orElse(null));
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
  public Page<TenantEntity> findByPagination(final int pageNo, final int pageSize) {
    if (this.systemTenantService.isSystemTenant(CurrentUserContext.getCurrentTenantId())) {
      return this.tenantRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by("name").ascending()));
    } else {
      return SpringUtil.pagedFromSingleItem(
          this.tenantRepository.findById(CurrentUserContext.getCurrentTenantId()).orElse(null));
    }
  }

  /**
   * Delete by id.
   *
   * @param tenantId the tenant id
   */
  @Override
  @Transactional(readOnly = false)
  public void deleteById(final UUID tenantId) {
    this.tenantRepository.deleteById(tenantId);
  }

  /**
   * Delete all by id.
   *
   * @param tenantIds the tenant ids
   */
  @Override
  @Transactional(readOnly = false)
  public void deleteAllById(final List<UUID> tenantIds) {
    this.tenantRepository.deleteAllById(tenantIds);
  }


  /**
   * Save tenant entity tenant entity.
   *
   * @param tenantEntity the tenant entity
   * @return the tenant entity
   */
  private TenantEntity saveTenantEntity(final TenantEntity tenantEntity) {
    TenantEntity entity = this.tenantRepository.save(tenantEntity);
    // If User not register then automatically register admin user
    UserEntity userEntity = this.userService.findByEmail(entity.getEmail());
    if (Objects.isNull(userEntity)) {
      userEntity = getNewUserEntity(entity);
      userEntity.setTenantId(entity.getId());
      userEntity = this.userService.save(userEntity);


      RoleEntity roleEntity = createDefaultClientRole(entity);

      //Assign Default Tenant Role to User
      //ToDo implement default role provisioning
      UserRoleEntity userRoleEntity = UserRoleEntity.builder().userId(userEntity.getId()).roleId(roleEntity.getId())
          .userEntity(userEntity).roleEntity(roleEntity).build();
      userRoleEntity.created(SYSTEM_TENANT);
      userRoleEntity.updated(SYSTEM_TENANT);
      userRoleEntity = this.userRoleService.create(userRoleEntity);
    }
    return entity;
  }

  /**
   * Create default client role role entity.
   *
   * @param entity the entity
   * @return the role entity
   */
  private RoleEntity createDefaultClientRole(final TenantEntity entity) {
    //Create Default Role For clientid
    RoleEntity roleEntity = RoleEntity.builder().name(CLIENT_CRED_ROLE_NAME).tenantId(entity.getId()).build();
    roleEntity.created(SYSTEM_TENANT);
    roleEntity.updated(SYSTEM_TENANT);
    roleEntity = this.roleService.create(roleEntity);

    //Create Attribute/Rest Path
    AttributeEntity attribute =
        AttributeEntity.builder().tenantId(entity.getId()).name(POLICY).path(ANT_POLICY_URL).build();
    attribute.created(SYSTEM_TENANT);
    attribute.updated(SYSTEM_TENANT);
    attribute = this.attributeService.create(attribute);

    //Create Role Attribute
    RoleAttributeEntity roleAttributeEntity = RoleAttributeEntity.builder().roleId(roleEntity.getId())
        .attributeId(attribute.getId()).canRead(true).attributeEntity(attribute).roleEntity(roleEntity).build();
    roleAttributeEntity.created(SYSTEM_TENANT);
    roleAttributeEntity.updated(SYSTEM_TENANT);
    roleAttributeEntity = this.roleAttributeService.create(roleAttributeEntity);
    return roleEntity;
  }
}
