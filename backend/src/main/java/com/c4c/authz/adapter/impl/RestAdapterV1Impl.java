package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.common.exception.CustomException;
import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.core.entity.AttributeEntity;
import com.c4c.authz.core.entity.RoleAttributeEntity;
import com.c4c.authz.core.entity.RoleAttributeId;
import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.entity.UserRoleEntity;
import com.c4c.authz.core.entity.UserRoleId;
import com.c4c.authz.core.entity.lookup.CityEntity;
import com.c4c.authz.core.entity.lookup.CountryEntity;
import com.c4c.authz.core.entity.lookup.StateEntity;
import com.c4c.authz.core.service.api.AttributeService;
import com.c4c.authz.core.service.api.AuthenticationService;
import com.c4c.authz.core.service.api.ClientService;
import com.c4c.authz.core.service.api.LookupService;
import com.c4c.authz.core.service.api.PolicyService;
import com.c4c.authz.core.service.api.RoleAttributeService;
import com.c4c.authz.core.service.api.RoleService;
import com.c4c.authz.core.service.api.TenantService;
import com.c4c.authz.core.service.api.UserRoleService;
import com.c4c.authz.core.service.api.UserService;
import com.c4c.authz.rest.resource.AttributeResource;
import com.c4c.authz.rest.resource.ClientResource;
import com.c4c.authz.rest.resource.PolicyResource;
import com.c4c.authz.rest.resource.RoleAttributeResource;
import com.c4c.authz.rest.resource.RoleResource;
import com.c4c.authz.rest.resource.TenantResource;
import com.c4c.authz.rest.resource.auth.JwtRequest;
import com.c4c.authz.rest.resource.auth.JwtResponse;
import com.c4c.authz.rest.resource.lookup.CityResource;
import com.c4c.authz.rest.resource.lookup.CountryResource;
import com.c4c.authz.rest.resource.lookup.StateResource;
import com.c4c.authz.rest.resource.user.UserDetailsResource;
import com.c4c.authz.rest.resource.user.UserResource;
import com.c4c.authz.rest.resource.user.UserRoleResource;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * The type Rest adapter v 1.
 */
@Component
public class RestAdapterV1Impl implements RestAdapterV1 {
  /**
   * The Role service.
   */
  private final RoleService roleService;
  /**
   * The User service.
   */
  private final UserService userService;
  /**
   * The Authentication service.
   */
  private final AuthenticationService authenticationService;

  /**
   * The Attribute converter.
   */
  private final AttributeConverter attributeConverter;

  /**
   * The User converter.
   */
  private final UserConverter userConverter;
  /**
   * The Exact name model mapper.
   */
  private final ModelMapper exactNameModelMapper;

  /**
   * The Lookup service.
   */
  private final LookupService lookupService;

  /**
   * The Tenant service.
   */
  private final TenantService tenantService;

  /**
   * The Attribute service.
   */
  private final AttributeService attributeService;

  /**
   * The Role converter.
   */
  private final RoleConverter roleConverter;

  /**
   * The User role converter.
   */
  private final UserRoleConverter userRoleConverter;

  /**
   * The User role service.
   */
  private final UserRoleService userRoleService;

  /**
   * The Tenant converter.
   */
  private final TenantConverter tenantConverter;
  /**
   * The Role attribute service.
   */
  private final RoleAttributeService roleAttributeService;
  /**
   * The Role attribute converter.
   */
  private final RoleAttributeConverter roleAttributeConverter;

  /**
   * The Policy service.
   */
  private final PolicyService policyService;
  /**
   * The Client service.
   */
  private final ClientService clientService;
  /**
   * The Client converter.
   */
  private final ClientConverter clientConverter;

  /**
   * The Policy converter.
   */
  private final PolicyConverter policyConverter;

  /**
   * Instantiates a new Rest adapter v 1.
   *
   * @param roleService           the role service
   * @param userService           the user service
   * @param authenticationService the authentication service
   * @param lookupService         the lookup service
   * @param tenantService         the tenant service
   * @param attributeService      the attribute service
   * @param userRoleService       the user role service
   * @param exactNameModelMapper  the exact name model mapper
   * @param roleAttributeService  the role attribute service
   * @param policyService         the policy service
   * @param clientService         the client service
   */
  @Autowired
  public RestAdapterV1Impl(final RoleService roleService, final UserService userService,
                           final AuthenticationService authenticationService,
                           final LookupService lookupService,
                           final TenantService tenantService,
                           final AttributeService attributeService,
                           final UserRoleService userRoleService,
                           final ModelMapper exactNameModelMapper,
                           final RoleAttributeService roleAttributeService,
                           final PolicyService policyService,
                           final ClientService clientService) {
    this.roleService = roleService;
    this.userService = userService;
    this.authenticationService = authenticationService;
    this.exactNameModelMapper = exactNameModelMapper;
    this.lookupService = lookupService;
    this.tenantService = tenantService;
    this.attributeService = attributeService;
    this.userRoleService = userRoleService;
    this.roleAttributeService = roleAttributeService;
    this.policyService = policyService;
    this.clientService = clientService;

    this.roleConverter = RoleConverter.getInstance();
    this.userConverter = UserConverter.getInstance();
    this.userRoleConverter = UserRoleConverter.getInstance();
    this.tenantConverter = TenantConverter.getInstance();
    this.attributeConverter = AttributeConverter.getInstance();
    this.roleAttributeConverter = RoleAttributeConverter.getInstance();
    this.clientConverter = ClientConverter.getInstance();
    this.policyConverter = PolicyConverter.getInstance();
  }

  /**
   * Create user user resource.
   *
   * @param userResource the user resource
   * @return the user resource
   */
  @Override
  public UserResource createUser(final UserResource userResource) {
    return this.userConverter.covertFromEntity(
        this.userService.save(this.userConverter.convertFromResource(userResource)));
  }

  /**
   * Update user user resource.
   *
   * @param userResource the user resource
   * @return the user resource
   */
  @Override
  public UserResource updateUser(final UserResource userResource) {
    return this.userConverter.covertFromEntity(
        this.userService.update(this.userConverter.convertFromResource(userResource)));
  }

  /**
   * Authenticate jwt response.
   *
   * @param request the request
   * @return the jwt response
   */
  @Override
  public JwtResponse authenticate(final JwtRequest request) {
    return TokenConverter.authSuccessInfoToJwtResponse(
        this.authenticationService.authenticate(request.getUsername(), request.getPassword(), request.isOtp()));
  }

  /**
   * Logout.
   */
  @Override
  public void logout() {
    this.authenticationService.logout();
  }

  /**
   * Refresh token jwt response.
   *
   * @param refreshToken the refresh token
   * @return the jwt response
   */
  @Override
  public JwtResponse refreshToken(final String refreshToken) {
    return TokenConverter.authSuccessInfoToJwtResponse(this.authenticationService.refreshToken(refreshToken));
  }

  /**
   * Countries list.
   *
   * @return the list
   */
  @Override
  public List<CountryResource> countries() {
    List<CountryEntity> countryEntities = this.lookupService.countries();
    return countryEntities.stream()
        .map(countryEntity -> this.exactNameModelMapper.map(countryEntity, CountryResource.class)).toList();
  }

  /**
   * States list.
   *
   * @param countryId the country id
   * @return the list
   */
  @Override
  public List<StateResource> states(final int countryId) {
    List<StateEntity> stateEntities = this.lookupService.states(countryId);
    return stateEntities.stream()
        .map(stateEntity -> this.exactNameModelMapper.map(stateEntity, StateResource.class)).toList();
  }

  /**
   * Cities list.
   *
   * @param stateId the state id
   * @return the list
   */
  @Override
  public List<CityResource> cities(final int stateId) {
    List<CityEntity> cityEntities = this.lookupService.cities(stateId);
    return cityEntities.stream().map(cityEntity -> this.exactNameModelMapper.map(cityEntity, CityResource.class))
        .toList();
  }

  /**
   * Create tenant tenant resource.
   *
   * @param tenantResource the tenant resource
   * @return the tenant resource
   */
  @Override
  public TenantResource createTenant(final TenantResource tenantResource) {
    return this.tenantConverter.covertFromEntity(this.tenantService.create(this.getTenantEntity(tenantResource)));
  }

  /**
   * Update tenant tenant resource.
   *
   * @param tenantResource the tenant resource
   * @return the tenant resource
   */
  @Override
  public TenantResource updateTenant(final TenantResource tenantResource) {
    TenantEntity tenantEntity = this.getTenantEntity(tenantResource);
    tenantEntity = this.tenantService.update(tenantEntity);
    return this.tenantConverter.covertFromEntity(this.tenantService.create(tenantEntity));
  }

  /**
   * Find by id tenant tenant resource.
   *
   * @param tenantId the tenant id
   * @return the tenant resource
   */
  @Override
  public TenantResource findByIdTenant(final UUID tenantId) {
    TenantEntity tenantEntity = this.getTenantById(tenantId);
    return this.tenantConverter.covertFromEntity(tenantEntity);
  }

  /**
   * Find all tenant list.
   *
   * @return the list
   */
  @Override
  public List<TenantResource> findAllTenant() {
    List<TenantEntity> tenantEntities = this.tenantService.findAll();
    return this.tenantConverter.createFromEntities(tenantEntities);
  }

  /**
   * Find by pagination tenant page.
   *
   * @param pageNo   the page no
   * @param pageSize the page size
   * @return the page
   */
  @Override
  public Page<TenantResource> findByPaginationTenant(final int pageNo, final int pageSize) {
    return this.tenantConverter.createFromEntities(
        this.tenantService.findByPagination(pageNo, pageSize));
  }

  /**
   * Delete by id tenant.
   *
   * @param tenantId the tenant id
   */
  @Override
  public void deleteByIdTenant(final UUID tenantId) {
    TenantEntity tenantEntity = this.getTenantById(tenantId);
    tenantEntity.setDeleted(true);
    tenantEntity = this.tenantService.update(tenantEntity);
    if (!tenantEntity.isDeleted()) {
      throw new CustomException("Tenant Not Deleted.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Create attribute attribute resource.
   *
   * @param attributeResource the attribute resource
   * @return the attribute resource
   */
  @Override
  public AttributeResource createAttribute(final AttributeResource attributeResource) {
    AttributeEntity attributeEntity = this.attributeConverter.convertFromResource(attributeResource);
    attributeEntity = this.attributeService.create(attributeEntity);
    return this.attributeConverter.covertFromEntity(attributeEntity);
  }

  /**
   * Find by id attribute attribute resource.
   *
   * @param attributeId the attribute id
   * @return the attribute resource
   */
  @Override
  public AttributeResource findByIdAttribute(final UUID attributeId) {
    return this.attributeConverter.covertFromEntity(this.attributeService.findById(attributeId));
  }

  /**
   * Find all attribute list.
   *
   * @return the list
   */
  @Override
  public List<AttributeResource> findAllAttribute() {
    return this.attributeConverter.createFromEntities(this.attributeService.findAll());
  }

  /**
   * Find by pagination attribute page.
   *
   * @param pageNo   the page no
   * @param pageSize the page size
   * @return the page
   */
  @Override
  public Page<AttributeResource> findByPaginationAttribute(final int pageNo, final int pageSize) {
    return this.attributeConverter.createFromEntities(
        this.attributeService.findByPagination(pageNo, pageSize));
  }

  /**
   * Update attribute attribute resource.
   *
   * @param attributeResource the attribute resource
   * @return the attribute resource
   */
  @Override
  public AttributeResource updateAttribute(final AttributeResource attributeResource) {
    AttributeEntity attributeEntity = this.attributeConverter.convertFromResource(attributeResource);
    attributeEntity = this.attributeService.update(attributeEntity);
    return this.attributeConverter.covertFromEntity(attributeEntity);
  }

  /**
   * Delete by id attribute.
   *
   * @param attributeId the attribute id
   */
  @Override
  public void deleteByIdAttribute(final UUID attributeId) {
    this.attributeService.deleteById(attributeId);
  }

  /**
   * Find by id role role resource.
   *
   * @param roleId the role id
   * @return the role resource
   */
  @Override
  public RoleResource findByIdRole(final UUID roleId) {
    return this.roleConverter.covertFromEntity(this.roleService.findById(roleId));
  }

  /**
   * Find by pagination role page.
   *
   * @param pageNo   the page no
   * @param pageSize the page size
   * @return the page
   */
  @Override
  public Page<RoleResource> findByPaginationRole(final int pageNo, final int pageSize) {
    return this.roleConverter.createFromEntities(this.roleService.findByPagination(pageNo, pageSize));
  }

  /**
   * Find all role list.
   *
   * @return the list
   */
  @Override
  public List<RoleResource> findAllRole() {
    return this.roleConverter.createFromEntities(this.roleService.findAll());
  }

  /**
   * Create role role resource.
   *
   * @param role the role
   * @return the role resource
   */
  @Override
  public RoleResource createRole(final RoleResource role) {
    return this.roleConverter.covertFromEntity(
        this.roleService.create(this.roleConverter.convertFromResource(role)));
  }

  /**
   * Update role role resource.
   *
   * @param role the role
   * @return the role resource
   */
  @Override
  public RoleResource updateRole(final RoleResource role) {
    return this.roleConverter.covertFromEntity(
        this.roleService.update(this.roleConverter.convertFromResource(role)));
  }

  /**
   * Delete by id role.
   *
   * @param roleId the role id
   */
  @Override
  public void deleteByIdRole(final UUID roleId) {
    this.roleService.deleteById(roleId);
  }

  /**
   * Find by id user role user role resource.
   *
   * @param userId the user id
   * @param roleId the role id
   * @return the user role resource
   */
  @Override
  public UserRoleResource findByIdUserRole(final UUID userId, final UUID roleId) {
    return this.userRoleConverter.covertFromEntity(this.userRoleService.findById(new UserRoleId(userId, roleId)));
  }

  /**
   * Find by pagination user role page.
   *
   * @param pageNo   the page no
   * @param pageSize the page size
   * @return the page
   */
  @Override
  public Page<UserRoleResource> findByPaginationUserRole(final int pageNo, final int pageSize) {
    return this.userRoleConverter.createFromEntities(this.userRoleService.findByPagination(pageNo, pageSize));
  }

  /**
   * Find all user role list.
   *
   * @return the list
   */
  @Override
  public List<UserRoleResource> findAllUserRole() {
    return this.userRoleConverter.createFromEntities(this.userRoleService.findAll());
  }

  /**
   * Create user role user role resource.
   *
   * @param userRoleResource the user role resource
   * @return the user role resource
   */
  @Override
  public UserRoleResource createUserRole(final UserRoleResource userRoleResource) {
    UserRoleEntity userRoleEntity = this.userRoleConverter.convertFromResource(userRoleResource);
    userRoleEntity.setRoleEntity(this.roleService.findById(userRoleEntity.getRoleId()));
    userRoleEntity.setUserEntity(this.userService.findById(userRoleEntity.getUserId()));
    return this.userRoleConverter.covertFromEntity(this.userRoleService.create(userRoleEntity));
  }

  /**
   * Update user role user role resource.
   *
   * @param userRoleResource the user role resource
   * @return the user role resource
   */
  @Override
  public UserRoleResource updateUserRole(final UserRoleResource userRoleResource) {
    return this.userRoleConverter.covertFromEntity(
        this.userRoleService.update(this.userRoleConverter.convertFromResource(userRoleResource)));
  }

  /**
   * Delete by id user role.
   *
   * @param userId the user id
   * @param roleId the role id
   */
  @Override
  public void deleteByIdUserRole(final UUID userId, final UUID roleId) {
    this.userRoleService.deleteById(new UserRoleId(userId, roleId));
  }

  /**
   * Find by id role attribute role attribute resource.
   *
   * @param roleId      the role id
   * @param attributeId the attribute id
   * @return the role attribute resource
   */
  @Override
  public RoleAttributeResource findByIdRoleAttribute(final UUID roleId, final UUID attributeId) {
    return this.roleAttributeConverter.covertFromEntity(
        this.roleAttributeService.findById(new RoleAttributeId(roleId, attributeId)));
  }

  /**
   * Find by pagination role attribute page.
   *
   * @param pageNo   the page no
   * @param pageSize the page size
   * @return the page
   */
  @Override
  public Page<RoleAttributeResource> findByPaginationRoleAttribute(final int pageNo, final int pageSize) {
    return this.roleAttributeConverter.createFromEntities(
        this.roleAttributeService.findByPagination(pageNo, pageSize));
  }

  /**
   * Find all role attribute list.
   *
   * @return the list
   */
  @Override
  public List<RoleAttributeResource> findAllRoleAttribute() {
    return this.roleAttributeConverter.createFromEntities(this.roleAttributeService.findAll());
  }

  /**
   * Create role attribute role attribute resource.
   *
   * @param roleAttributeResource the role attribute resource
   * @return the role attribute resource
   */
  @Override
  public RoleAttributeResource createRoleAttribute(final RoleAttributeResource roleAttributeResource) {
    RoleAttributeEntity roleAttributeEntity =
        this.roleAttributeConverter.convertFromResource(roleAttributeResource);
    roleAttributeEntity.setRoleEntity(this.roleService.findById(roleAttributeEntity.getRoleId()));
    roleAttributeEntity.setAttributeEntity(this.attributeService.findById(roleAttributeEntity.getAttributeId()));
    return this.roleAttributeConverter.covertFromEntity(this.roleAttributeService.create(roleAttributeEntity));
  }

  /**
   * Update role attribute role attribute resource.
   *
   * @param roleAttributeResource the role attribute resource
   * @return the role attribute resource
   */
  @Override
  public RoleAttributeResource updateRoleAttribute(final RoleAttributeResource roleAttributeResource) {
    return this.roleAttributeConverter.covertFromEntity(
        this.roleAttributeService.update(
            this.roleAttributeConverter.convertFromResource(roleAttributeResource)));
  }

  /**
   * Delete by id role attribute.
   *
   * @param roleId      the role id
   * @param attributeId the attribute id
   */
  @Override
  public void deleteByIdRoleAttribute(final UUID roleId, final UUID attributeId) {
    this.roleAttributeService.deleteById(new RoleAttributeId(roleId, attributeId));
  }

  /**
   * Find by tenant id and user name user details resource.
   *
   * @param tenantId the tenant id
   * @param email    the email
   * @return the user details resource
   */
  @Override
  public UserDetailsResource findByTenantIdAndUserName(final UUID tenantId, final String email) {
    UserEntity userEntity = this.userService.findByTenantIdAndEmail(tenantId, email);
    Set<PolicyRecord> policyRecords = new TreeSet<>();
    UserResource userResource = this.userConverter.covertFromEntity(userEntity);
    for (UserRoleEntity userRoleEntity : userEntity.getUserRoleEntities()) {
      policyRecords.addAll(this.policyService.getPoliciesByRoleId(userRoleEntity.getRoleId()));
    }
    return new UserDetailsResource(userResource, policyRecords);
  }

  /**
   * Gets tenant by id.
   *
   * @param tenantId the tenant id
   * @return the tenant by id
   */
  private TenantEntity getTenantById(final UUID tenantId) {
    TenantEntity tenantEntity = this.tenantService.findById(tenantId);
    if (tenantEntity == null) {
      throw new EntityNotFoundException(String.format("Tenant not found with id %s", tenantId));
    }
    return tenantEntity;
  }

  /**
   * Gets tenant entity.
   *
   * @param tenantResource the tenant resource
   * @return the tenant entity
   */
  private TenantEntity getTenantEntity(final TenantResource tenantResource) {
    if (tenantResource == null) {
      return null;
    }
    TenantEntity tenantEntity = this.tenantConverter.convertFromResource(tenantResource);
    CityEntity cityEntity = this.lookupService.getCityById(tenantResource.getCityId());
    if (!Objects.isNull(tenantEntity)) {
      tenantEntity.setCity(cityEntity);
    }
    return tenantEntity;
  }


  /**
   * Create client client resource.
   *
   * @param clientResource the client resource
   * @return the client resource
   */
  @Override
  public ClientResource createClient(final ClientResource clientResource) {
    return this.clientConverter.covertFromEntity(
        this.clientService.create(this.clientConverter.convertFromResource(clientResource)));
  }

  /**
   * Update client client resource.
   *
   * @param clientResource the client resource
   * @return the client resource
   */
  @Override
  public ClientResource updateClient(final ClientResource clientResource) {
    return this.clientConverter.covertFromEntity(
        this.clientService.update(this.clientConverter.convertFromResource(clientResource)));
  }

  /**
   * Find by id client client resource.
   *
   * @param clientId the client id
   * @return the client resource
   */
  @Override
  public ClientResource findByIdClient(final UUID clientId) {
    return this.clientConverter.covertFromEntity(this.clientService.findById(clientId));
  }

  /**
   * Find all client list.
   *
   * @return the list
   */
  @Override
  public List<ClientResource> findAllClient() {
    return this.clientConverter.createFromEntities(this.clientService.findAll());
  }

  /**
   * Find by pagination client page.
   *
   * @param pageNo   the page no
   * @param pageSize the page size
   * @return the page
   */
  @Override
  public Page<ClientResource> findByPaginationClient(final int pageNo, final int pageSize) {
    return this.clientConverter.createFromEntities(this.clientService.findByPagination(pageNo, pageSize));
  }

  /**
   * Delete by id client.
   *
   * @param clientId the client id
   */
  @Override
  public void deleteByIdClient(final UUID clientId) {
    this.clientService.deleteById(clientId);
  }


  /**
   * Gets policies by role id.
   *
   * @return the policies by role id
   */
  @Override
  public List<PolicyResource> getPoliciesForCurrentClient() {
    List<PolicyRecord> policyRecords =
        this.policyService.getPoliciesForCurrentClient(CurrentUserContext.getCurrentTenantId(),
            CurrentUserContext.getCurrentUser());
    return this.policyConverter.createFromEntities(policyRecords);
  }
}
