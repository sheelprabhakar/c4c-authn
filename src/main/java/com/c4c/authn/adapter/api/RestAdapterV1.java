package com.c4c.authn.adapter.api;

import com.c4c.authn.rest.resource.RestResource;
import com.c4c.authn.rest.resource.RoleResource;
import com.c4c.authn.rest.resource.TenantResource;
import com.c4c.authn.rest.resource.UserResource;
import com.c4c.authn.rest.resource.UserRoleResource;
import com.c4c.authn.rest.resource.auth.JwtRequest;
import com.c4c.authn.rest.resource.auth.JwtResponse;
import com.c4c.authn.rest.resource.lookup.CityResource;
import com.c4c.authn.rest.resource.lookup.CountryResource;
import com.c4c.authn.rest.resource.lookup.StateResource;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

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
    UserResource createUser(final UserResource userResource);

    /**
     * Update user user resource.
     *
     * @param userResource the user resource
     * @return the user resource
     */
    UserResource updateUser(final UserResource userResource);

    /**
     * Authenticate jwt response.
     *
     * @param request the request
     * @return the jwt response
     */
    JwtResponse authenticate(final JwtRequest request);

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
    JwtResponse refreshToken(final String refreshToken);

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
    List<StateResource> states(final int countryId);

    /**
     * Cities list.
     *
     * @param stateId the state id
     * @return the list
     */
    List<CityResource> cities(final int stateId);

    /**
     * Create tenant tenant resource.
     *
     * @param tenantResource the tenant resource
     * @return the tenant resource
     */
    TenantResource createTenant(final TenantResource tenantResource);

    /**
     * Update tenant tenant resource.
     *
     * @param tenantResource the tenant resource
     * @return the tenant resource
     */
    TenantResource updateTenant(final TenantResource tenantResource);

    /**
     * Find by id tenant tenant resource.
     *
     * @param tenantId the tenant id
     * @return the tenant resource
     */
    TenantResource findByIdTenant(final UUID tenantId);

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
    void deleteByIdTenant(final UUID tenantId);

    /**
     * Create rest resource rest resource.
     *
     * @param restResource the rest resource
     * @return the rest resource
     */
    RestResource createRestResource(final RestResource restResource);

    /**
     * Find by id rest resource rest resource.
     *
     * @param restResourceId the rest resource id
     * @return the rest resource
     */
    RestResource findByIdRestResource(final UUID restResourceId);

    /**
     * Find all rest resource list.
     *
     * @return the list
     */
    List<RestResource> findAllRestResource();

    /**
     * Find by pagination rest resource page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<RestResource> findByPaginationRestResource(final int pageNo, final int pageSize);

    /**
     * Update rest resource rest resource.
     *
     * @param restResource the rest resource
     * @return the rest resource
     */
    RestResource updateRestResource(final RestResource restResource);

    /**
     * Delete by id rest resource.
     *
     * @param restResourceId the rest resource id
     */
    void deleteByIdRestResource(final UUID restResourceId);

    /**
     * Find by id role role resource.
     *
     * @param roleId the role id
     * @return the role resource
     */
    RoleResource findByIdRole(final UUID roleId);

    /**
     * Find by pagination role page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<RoleResource> findByPaginationRole(final int pageNo, final int pageSize);

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
    RoleResource createRole(final RoleResource role);

    /**
     * Update role role resource.
     *
     * @param role the role
     * @return the role resource
     */
    RoleResource updateRole(final RoleResource role);

    /**
     * Delete by id role.
     *
     * @param roleId the role id
     */
    void deleteByIdRole(final UUID roleId);

    /**
     * Find by id user role user role resource.
     *
     * @param userId the user id
     * @param roleId the role id
     * @return the user role resource
     */
    UserRoleResource findByIdUserRole(final UUID userId, UUID roleId);

    /**
     * Find by pagination user role page.
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return the page
     */
    Page<UserRoleResource> findByPaginationUserRole(final int pageNo, final int pageSize);

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
    UserRoleResource createUserRole(final UserRoleResource userRoleResource);

    /**
     * Update user role user role resource.
     *
     * @param userRoleResource the user role resource
     * @return the user role resource
     */
    UserRoleResource updateUserRole(final UserRoleResource userRoleResource);

    /**
     * Delete by id user role.
     *
     * @param userId the user id
     * @param roleId the role id
     */
    void deleteByIdUserRole(final UUID userId, final UUID roleId);
}
