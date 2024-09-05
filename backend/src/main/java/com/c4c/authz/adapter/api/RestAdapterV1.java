package com.c4c.authz.adapter.api;

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
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface Rest adapter v 1.
 */
public interface RestAdapterV1 {
  /**
   * Create user user resource.
   *
   * @param userResource the user resource
   * @return the user resource
   */
  UserResource createUser(UserResource userResource);

  /**
   * Update user user resource.
   *
   * @param userResource the user resource
   * @return the user resource
   */
  UserResource updateUser(UserResource userResource);

  /**
   * Authenticate jwt response.
   *
   * @param request the request
   * @return the jwt response
   */
  JwtResponse authenticate(JwtRequest request);

  /**
   * Logout.
   */
  void logout();

  /**
   * Refresh token jwt response.
   *
   * @param refreshToken the refresh token
   * @return the jwt response
   */
  JwtResponse refreshToken(String refreshToken);

  /**
   * Countries list.
   *
   * @return the list
   */
  List<CountryResource> countries();

  /**
   * States list.
   *
   * @param countryId the country id
   * @return the list
   */
  List<StateResource> states(int countryId);

  /**
   * Cities list.
   *
   * @param stateId the state id
   * @return the list
   */
  List<CityResource> cities(int stateId);

  /**
   * Create tenant tenant resource.
   *
   * @param tenantResource the tenant resource
   * @return the tenant resource
   */
  TenantResource createTenant(TenantResource tenantResource);

  /**
   * Update tenant tenant resource.
   *
   * @param tenantResource the tenant resource
   * @return the tenant resource
   */
  TenantResource updateTenant(TenantResource tenantResource);

  /**
   * Find by id tenant tenant resource.
   *
   * @param tenantId the tenant id
   * @return the tenant resource
   */
  TenantResource findByIdTenant(UUID tenantId);


  /**
   * Find by pagination tenant page.
   *
   * @param pageIndex the page index
   * @param pageSize  the page size
   * @return the page
   */
  Page<TenantResource> findByPaginationTenant(int pageIndex, int pageSize);

  /**
   * Find all tenant list.
   *
   * @return the list
   */
  List<TenantResource> findAllTenant();

  /**
   * Delete by id tenant.
   *
   * @param tenantId the tenant id
   */
  void deleteByIdTenant(UUID tenantId);

  /**
   * Create rest acl rest acl resource.
   *
   * @param restAclResource the rest acl resource
   * @return the rest acl resource
   */
  RestAclResource createRestAcl(RestAclResource restAclResource);

  /**
   * Find by id rest acl rest acl resource.
   *
   * @param restAclId the rest acl id
   * @return the rest acl resource
   */
  RestAclResource findByIdRestAcl(UUID restAclId);

  /**
   * Find all rest acl list.
   *
   * @return the list
   */
  List<RestAclResource> findAllRestAcl();

  /**
   * Find by pagination rest acl page.
   *
   * @param pageRequest the page request
   * @return the page
   */
  Page<RestAclResource> findByPaginationRestAcl(Pageable pageRequest);

  /**
   * Update rest acl rest acl resource.
   *
   * @param restAclResource the rest acl resource
   * @return the rest acl resource
   */
  RestAclResource updateRestAcl(RestAclResource restAclResource);

  /**
   * Delete by id rest acl.
   *
   * @param restAclId the rest acl id
   */
  void deleteByIdRestAcl(UUID restAclId);

  /**
   * Find by id role role resource.
   *
   * @param roleId the role id
   * @return the role resource
   */
  RoleResource findByIdRole(UUID roleId);

  /**
   * Find by pagination role page.
   *
   * @param pageIndex the page index
   * @param pageSize  the page size
   * @return the page
   */
  Page<RoleResource> findByPaginationRole(int pageIndex, int pageSize);

  /**
   * Find all role list.
   *
   * @return the list
   */
  List<RoleResource> findAllRole();

  /**
   * Create role role resource.
   *
   * @param role the role
   * @return the role resource
   */
  RoleResource createRole(RoleResource role);

  /**
   * Update role role resource.
   *
   * @param role the role
   * @return the role resource
   */
  RoleResource updateRole(RoleResource role);

  /**
   * Delete by id role.
   *
   * @param roleId the role id
   */
  void deleteByIdRole(UUID roleId);

  /**
   * Find by id user role user role resource.
   *
   * @param userId the user id
   * @param roleId the role id
   * @return the user role resource
   */
  UserRoleResource findByIdUserRole(UUID userId, UUID roleId);

  /**
   * Find by pagination user role page.
   *
   * @param pageIndex the page index
   * @param pageSize  the page size
   * @return the page
   */
  Page<UserRoleResource> findByPaginationUserRole(int pageIndex, int pageSize);

  /**
   * Find all user role list.
   *
   * @return the list
   */
  List<UserRoleResource> findAllUserRole();

  /**
   * Create user role user role resource.
   *
   * @param userRoleResource the user role resource
   * @return the user role resource
   */
  UserRoleResource createUserRole(UserRoleResource userRoleResource);

  /**
   * Update user role user role resource.
   *
   * @param userRoleResource the user role resource
   * @return the user role resource
   */
  UserRoleResource updateUserRole(UserRoleResource userRoleResource);

  /**
   * Delete by id user role.
   *
   * @param userId the user id
   * @param roleId the role id
   */
  void deleteByIdUserRole(UUID userId, UUID roleId);

  /**
   * Find by id role rest acl role rest acl resource.
   *
   * @param roleId    the role id
   * @param restAclId the rest acl id
   * @return the role rest acl resource
   */
  RoleRestAclResource findByIdRoleRestAcl(UUID roleId, UUID restAclId);

  /**
   * Find by pagination role rest acl page.
   *
   * @param pageIndex the page index
   * @param pageSize  the page size
   * @return the page
   */
  Page<RoleRestAclResource> findByPaginationRoleRestAcl(int pageIndex, int pageSize);

  /**
   * Find all role rest acl list.
   *
   * @return the list
   */
  List<RoleRestAclResource> findAllRoleRestAcl();

  /**
   * Create role rest acl role rest acl resource.
   *
   * @param roleRestAclResource the role rest acl resource
   * @return the role rest acl resource
   */
  RoleRestAclResource createRoleRestAcl(RoleRestAclResource roleRestAclResource);

  /**
   * Update role rest acl role rest acl resource.
   *
   * @param roleRestAclResource the role rest acl resource
   * @return the role rest acl resource
   */
  RoleRestAclResource updateRoleRestAcl(RoleRestAclResource roleRestAclResource);

  /**
   * Delete by id role rest acl.
   *
   * @param roleId    the role id
   * @param restAclId the rest acl id
   */
  void deleteByIdRoleRestAcl(UUID roleId, UUID restAclId);

  /**
   * Find by tenant id and user name user details resource.
   *
   * @param tenantId the tenant id
   * @param userName the user name
   * @return the user details resource
   */
  UserDetailsResource findByTenantIdAndUserName(UUID tenantId, String userName);

  /**
   * Create client client resource.
   *
   * @param clientResource the client resource
   * @return the client resource
   */
  ClientResource createClient(ClientResource clientResource);

  /**
   * Update client client resource.
   *
   * @param clientResource the client resource
   * @return the client resource
   */
  ClientResource updateClient(ClientResource clientResource);

  /**
   * Find by id client client resource.
   *
   * @param clientId the client id
   * @return the client resource
   */
  ClientResource findByIdClient(UUID clientId);

  /**
   * Find all client list.
   *
   * @return the list
   */
  List<ClientResource> findAllClient();

  /**
   * Find by pagination client page.
   *
   * @param pageRequest the page request
   * @return the page
   */
  Page<ClientResource> findByPaginationClient(Pageable pageRequest);

  /**
   * Delete by id client.
   *
   * @param clientId the client id
   */
  void deleteByIdClient(UUID clientId);

  /**
   * Gets policies for current client.
   *
   * @return the policies for current client
   */
  List<PolicyResource> getPoliciesForCurrentClient();

  /**
   * Authenticate client jwt response.
   *
   * @param tenantId     the tenant id
   * @param clientId     the client id
   * @param clientSecret the client secret
   * @param grantType    the grant type
   * @return the jwt response
   */
  JwtResponse authenticateClient(UUID tenantId, String clientId, String clientSecret, String grantType);

  /**
   * Gets policies for current user.
   *
   * @return the policies for current user
   */
  List<PolicyResource> getPoliciesForCurrentUser();
}
