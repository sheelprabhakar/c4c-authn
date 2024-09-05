package com.c4c.authz.adapter.impl;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.common.CurrentUserContext;
import com.c4c.authz.common.exception.CustomException;
import com.c4c.authz.core.domain.PolicyRecord;
import com.c4c.authz.core.entity.RestAclEntity;
import com.c4c.authz.core.entity.RoleRestAclEntity;
import com.c4c.authz.core.entity.RoleRestAclId;
import com.c4c.authz.core.entity.TenantEntity;
import com.c4c.authz.core.entity.UserEntity;
import com.c4c.authz.core.entity.UserRoleEntity;
import com.c4c.authz.core.entity.UserRoleId;
import com.c4c.authz.core.entity.lookup.CityEntity;
import com.c4c.authz.core.entity.lookup.CountryEntity;
import com.c4c.authz.core.entity.lookup.StateEntity;
import com.c4c.authz.core.service.api.RestAclService;
import com.c4c.authz.core.service.api.AuthenticationService;
import com.c4c.authz.core.service.api.ClientService;
import com.c4c.authz.core.service.api.LookupService;
import com.c4c.authz.core.service.api.PolicyService;
import com.c4c.authz.core.service.api.RoleRestAclService;
import com.c4c.authz.core.service.api.RoleService;
import com.c4c.authz.core.service.api.TenantService;
import com.c4c.authz.core.service.api.UserRoleService;
import com.c4c.authz.core.service.api.UserService;
import com.c4c.authz.rest.resource.RestAclResource;
import com.c4c.authz.rest.resource.ClientResource;
import com.c4c.authz.rest.resource.PolicyResource;
import com.c4c.authz.rest.resource.RoleRestAclResource;
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
import org.springframework.data.domain.Pageable;
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
   * The Rest acl converter.
   */
  private final RestAclConverter restAclConverter;

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
   * The Rest acl service.
   */
  private final RestAclService restAclService;

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
   * The Role rest acl service.
   */
  private final RoleRestAclService roleRestAclService;
  /**
   * The Role rest acl converter.
   */
  private final RoleRestAclConverter roleRestAclConverter;

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
   * @param restAclService        the rest acl service
   * @param userRoleService       the user role service
   * @param exactNameModelMapper  the exact name model mapper
   * @param roleRestAclService    the role rest acl service
   * @param policyService         the policy service
   * @param clientService         the client service
   */
  @Autowired
  public RestAdapterV1Impl(final RoleService roleService, final UserService userService,
                           final AuthenticationService authenticationService,
                           final LookupService lookupService,
                           final TenantService tenantService,
                           final RestAclService restAclService,
                           final UserRoleService userRoleService,
                           final ModelMapper exactNameModelMapper,
                           final RoleRestAclService roleRestAclService,
                           final PolicyService policyService,
                           final ClientService clientService) {
    this.roleService = roleService;
    this.userService = userService;
    this.authenticationService = authenticationService;
    this.exactNameModelMapper = exactNameModelMapper;
    this.lookupService = lookupService;
    this.tenantService = tenantService;
    this.restAclService = restAclService;
    this.userRoleService = userRoleService;
    this.roleRestAclService = roleRestAclService;
    this.policyService = policyService;
    this.clientService = clientService;

    this.roleConverter = RoleConverter.getInstance();
    this.userConverter = UserConverter.getInstance();
    this.userRoleConverter = UserRoleConverter.getInstance();
    this.tenantConverter = TenantConverter.getInstance();
    this.restAclConverter = RestAclConverter.getInstance();
    this.roleRestAclConverter = RoleRestAclConverter.getInstance();
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
   * @param pageIndex the page index
   * @param pageSize  the page size
   * @return the page
   */
  @Override
  public Page<TenantResource> findByPaginationTenant(final int pageIndex, final int pageSize) {
    return this.tenantConverter.createFromEntities(
        this.tenantService.findByPagination(pageIndex, pageSize));
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
   * Create rest acl rest acl resource.
   *
   * @param restAclResource the rest acl resource
   * @return the rest acl resource
   */
  @Override
  public RestAclResource createRestAcl(final RestAclResource restAclResource) {
    RestAclEntity restAclEntity = this.restAclConverter.convertFromResource(restAclResource);
    restAclEntity = this.restAclService.create(restAclEntity);
    return this.restAclConverter.covertFromEntity(restAclEntity);
  }

  /**
   * Find by id rest acl rest acl resource.
   *
   * @param restAclId the rest acl id
   * @return the rest acl resource
   */
  @Override
  public RestAclResource findByIdRestAcl(final UUID restAclId) {
    return this.restAclConverter.covertFromEntity(this.restAclService.findById(restAclId));
  }

  /**
   * Find all rest acl list.
   *
   * @return the list
   */
  @Override
  public List<RestAclResource> findAllRestAcl() {
    return this.restAclConverter.createFromEntities(this.restAclService.findAll());
  }

  /**
   * Find by pagination rest acl page.
   *
   * @param pageRequest the page request
   * @return the page
   */
  @Override
  public Page<RestAclResource> findByPaginationRestAcl(final Pageable pageRequest) {

    return this.restAclConverter.createFromEntities(
        this.restAclService.findByPagination(pageRequest));
  }

  /**
   * Update rest acl rest acl resource.
   *
   * @param restAclResource the rest acl resource
   * @return the rest acl resource
   */
  @Override
  public RestAclResource updateRestAcl(final RestAclResource restAclResource) {
    RestAclEntity restAclEntity = this.restAclConverter.convertFromResource(restAclResource);
    restAclEntity = this.restAclService.update(restAclEntity);
    return this.restAclConverter.covertFromEntity(restAclEntity);
  }

  /**
   * Delete by id rest acl.
   *
   * @param restAclId the rest acl id
   */
  @Override
  public void deleteByIdRestAcl(final UUID restAclId) {
    this.restAclService.deleteById(restAclId);
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
   * @param pageIndex the page index
   * @param pageSize  the page size
   * @return the page
   */
  @Override
  public Page<RoleResource> findByPaginationRole(final int pageIndex, final int pageSize) {
    return this.roleConverter.createFromEntities(this.roleService.findByPagination(pageIndex, pageSize));
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
   * @param pageIndex the page index
   * @param pageSize  the page size
   * @return the page
   */
  @Override
  public Page<UserRoleResource> findByPaginationUserRole(final int pageIndex, final int pageSize) {
    return this.userRoleConverter.createFromEntities(this.userRoleService.findByPagination(pageIndex, pageSize));
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
   * Find by id role rest acl role rest acl resource.
   *
   * @param roleId    the role id
   * @param restAclId the rest acl id
   * @return the role rest acl resource
   */
  @Override
  public RoleRestAclResource findByIdRoleRestAcl(final UUID roleId, final UUID restAclId) {
    return this.roleRestAclConverter.covertFromEntity(
        this.roleRestAclService.findById(new RoleRestAclId(roleId, restAclId)));
  }

  /**
   * Find by pagination role rest acl page.
   *
   * @param pageIndex the page index
   * @param pageSize  the page size
   * @return the page
   */
  @Override
  public Page<RoleRestAclResource> findByPaginationRoleRestAcl(final int pageIndex, final int pageSize) {
    return this.roleRestAclConverter.createFromEntities(
        this.roleRestAclService.findByPagination(pageIndex, pageSize));
  }

  /**
   * Find all role rest acl list.
   *
   * @return the list
   */
  @Override
  public List<RoleRestAclResource> findAllRoleRestAcl() {
    return this.roleRestAclConverter.createFromEntities(this.roleRestAclService.findAll());
  }

  /**
   * Create role rest acl role rest acl resource.
   *
   * @param roleRestAclResource the role rest acl resource
   * @return the role rest acl resource
   */
  @Override
  public RoleRestAclResource createRoleRestAcl(final RoleRestAclResource roleRestAclResource) {
    RoleRestAclEntity roleRestAclEntity =
        this.roleRestAclConverter.convertFromResource(roleRestAclResource);
    roleRestAclEntity.setRoleEntity(this.roleService.findById(roleRestAclEntity.getRoleId()));
    roleRestAclEntity.setRestAclEntity(this.restAclService.findById(roleRestAclEntity.getRestAclId()));
    return this.roleRestAclConverter.covertFromEntity(this.roleRestAclService.create(roleRestAclEntity));
  }

  /**
   * Update role rest acl role rest acl resource.
   *
   * @param roleRestAclResource the role rest acl resource
   * @return the role rest acl resource
   */
  @Override
  public RoleRestAclResource updateRoleRestAcl(final RoleRestAclResource roleRestAclResource) {
    return this.roleRestAclConverter.covertFromEntity(
        this.roleRestAclService.update(
            this.roleRestAclConverter.convertFromResource(roleRestAclResource)));
  }

  /**
   * Delete by id role rest acl.
   *
   * @param roleId    the role id
   * @param restAclId the rest acl id
   */
  @Override
  public void deleteByIdRoleRestAcl(final UUID roleId, final UUID restAclId) {
    this.roleRestAclService.deleteById(new RoleRestAclId(roleId, restAclId));
  }

  /**
   * Find by tenant id and user name user details resource.
   *
   * @param tenantId the tenant id
   * @param userName the user name
   * @return the user details resource
   */
  @Override
  public UserDetailsResource findByTenantIdAndUserName(final UUID tenantId, final String userName) {
    UserEntity userEntity = this.userService.findByTenantIdAndUserName(tenantId, userName);
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
   * @param pageRequest the page request
   * @return the page
   */
  @Override
  public Page<ClientResource> findByPaginationClient(final Pageable pageRequest) {
    return this.clientConverter.createFromEntities(this.clientService.findByPagination(pageRequest));
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
   * Gets policies for current client.
   *
   * @return the policies for current client
   */
  @Override
  public List<PolicyResource> getPoliciesForCurrentClient() {
    List<PolicyRecord> policyRecords =
        this.policyService.getPoliciesForCurrentClient(CurrentUserContext.getCurrentTenantId(),
            CurrentUserContext.getCurrentUser());
    return this.policyConverter.createFromEntities(policyRecords);
  }


  /**
   * Gets policies for current user.
   *
   * @return the policies for current user
   */
  @Override
  public List<PolicyResource> getPoliciesForCurrentUser() {
    List<PolicyRecord> policyRecords =
        this.policyService.getPoliciesForCurrentUser(CurrentUserContext.getCurrentTenantId(),
            CurrentUserContext.getCurrentUser());
    return this.policyConverter.createFromEntities(policyRecords);
  }

  /**
   * Authenticate client jwt response.
   *
   * @param tenantId     the tenant id
   * @param clientId     the client id
   * @param clientSecret the client secret
   * @param grantType    the grant type
   * @return the jwt response
   */
  @Override
  public JwtResponse authenticateClient(final UUID tenantId, final String clientId, final String clientSecret,
                                        final String grantType) {
    return TokenConverter.authSuccessInfoToJwtResponse(
        this.authenticationService.authenticate(tenantId, clientId, clientSecret, grantType));
  }

}
