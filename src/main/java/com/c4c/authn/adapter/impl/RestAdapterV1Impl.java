package com.c4c.authn.adapter.impl;

import com.c4c.authn.adapter.RestAdapterV1;
import com.c4c.authn.core.entity.TenantEntity;
import com.c4c.authn.core.entity.UserEntity;
import com.c4c.authn.core.entity.lookup.CityEntity;
import com.c4c.authn.core.entity.lookup.CountryEntity;
import com.c4c.authn.core.entity.lookup.StateEntity;
import com.c4c.authn.core.service.AuthenticationService;
import com.c4c.authn.core.service.LookupService;
import com.c4c.authn.core.service.TenantService;
import com.c4c.authn.core.service.UserService;
import com.c4c.authn.rest.resource.TenantResource;
import com.c4c.authn.rest.resource.UserResource;
import com.c4c.authn.rest.resource.auth.JwtRequest;
import com.c4c.authn.rest.resource.auth.JwtResponse;
import com.c4c.authn.rest.resource.lookup.CityResource;
import com.c4c.authn.rest.resource.lookup.CountryResource;
import com.c4c.authn.rest.resource.lookup.StateResource;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Rest adapter v 1.
 */
@Component
public class RestAdapterV1Impl implements RestAdapterV1 {
  /**
   * The User service.
   */
  private final UserService userService;
  /**
   * The Authentication service.
   */
  private final AuthenticationService authenticationService;

  /**
   * Instantiates a new Rest adapter v 1.
   *
   * @param userService the user service
   * @param authenticationService the authentication service
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
   * Instantiates a new Rest adapter v 1.
   *
   * @param userService           the user service
   * @param authenticationService the authentication service
   * @param exactNameModelMapper  the exact name model mapper
   * @param lookupService         the lookup service
   * @param tenantService         the tenant service
   */
  @Autowired
  public RestAdapterV1Impl(final UserService userService,
                           final AuthenticationService authenticationService,
                           final ModelMapper exactNameModelMapper, final LookupService lookupService,
                           final TenantService tenantService) {
    this.userService = userService;
    this.authenticationService = authenticationService;
    this.exactNameModelMapper = exactNameModelMapper;
    this.lookupService = lookupService;
    this.tenantService = tenantService;
  }

  /**
   * Save user resource.
   *
   * @param userResource the user resource
   * @return the user resource
   */
  @Override
  public UserResource save(final UserResource userResource) {
    return this.exactNameModelMapper.map(this.userService.save(
            this.exactNameModelMapper.map(userResource, UserEntity.class)),
        UserResource.class);
  }

  /**
   * Update user resource.
   *
   * @param userResource the user resource
   * @return the user resource
   */
  @Override
  public UserResource update(final UserResource userResource) {
    return this.exactNameModelMapper.map(this.userService.update(
            this.exactNameModelMapper.map(userResource, UserEntity.class)),
        UserResource.class);
  }

  /**
   * Authenticate string.
   *
   * @param request the request
   * @return the string
   * @throws Exception the exception
   */
  @Override
  public JwtResponse authenticate(final JwtRequest request) throws Exception {
    return TokenConverter.authSuccessInfoToJwtResponse(
        this.authenticationService.authenticate(request.getUsername(),
            request.getPassword(), request.isOtp()));
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
   * @param refreshToken the token
   * @return the jwt response
   */
  @Override
  public JwtResponse refreshToken(final String refreshToken) {
    return TokenConverter.authSuccessInfoToJwtResponse(
        this.authenticationService.refreshToken(refreshToken));
  }

  /**
   * Countries list.
   *
   * @return the list
   */
  @Override
  public List<CountryResource> countries() {
    List<CountryEntity> countryEntities = this.lookupService.countries();
    return countryEntities.stream().map(countryEntity -> this.exactNameModelMapper
        .map(countryEntity, CountryResource.class)).collect(Collectors.toList());
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
    return stateEntities.stream().map(stateEntity -> this.exactNameModelMapper
        .map(stateEntity, StateResource.class)).collect(Collectors.toList());
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
    return cityEntities.stream().map(cityEntity -> this.exactNameModelMapper
        .map(cityEntity, CityResource.class)).collect(Collectors.toList());
  }

  /**
   * Create tenant tenant resource.
   *
   * @param tenantResource the tenant resource
   * @return the tenant resource
   */
  @Override
  public TenantResource createTenant(final TenantResource tenantResource) {
    TenantEntity tenantEntity = this.getTenantEntity(tenantResource);

    tenantEntity = this.tenantService.create(tenantEntity);

    TenantResource resource = this.mapModel(this.tenantService.create(tenantEntity),
        TenantResource.class);
    if (!Objects.isNull(resource)) {
      resource.setCityId(tenantEntity.getCity().getId());
    }
    return resource;
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
    TenantResource resource = this.mapModel(this.tenantService.create(tenantEntity),
        TenantResource.class);
    if (!Objects.isNull(resource)) {
      resource.setCityId(tenantEntity.getCity().getId());
    }
    return resource;
  }

  /**
   * Read tenant tenant resource.
   *
   * @param tenantId the tenant id
   * @return the tenant resource
   */
  @Override
  public TenantResource readTenant(final UUID tenantId) {
    TenantEntity tenantEntity = this.tenantService.read(tenantId);
    return this.getTenantResource(tenantEntity);
  }

  /**
   * Read tenants list.
   *
   * @return the list
   */
  @Override
  public List<TenantResource> readTenants() {
    List<TenantEntity> tenantEntities = this.tenantService.readAll();
    return tenantEntities.stream().map(tenantEntity -> this.mapModel(tenantEntity, TenantResource.class))
        .collect(Collectors.toList());
  }

  /**
   * Gets tenant entity.
   *
   * @param tenantResource the tenant resource
   * @return the tenant entity
   */
  private TenantEntity getTenantEntity(final TenantResource tenantResource) {
    TenantEntity tenantEntity = this.mapModel(tenantResource, TenantEntity.class);
    CityEntity cityEntity = this.lookupService.getCityById(tenantResource.getCityId());
    if (!Objects.isNull(tenantEntity)) {
      tenantEntity.setCity(cityEntity);
    }
    return tenantEntity;
  }

  private TenantResource getTenantResource(final TenantEntity tenantEntity) {
    TenantResource resource = this.mapModel(tenantEntity,
        TenantResource.class);
    resource.setCityId(tenantEntity.getCity().getId());
    return resource;
  }

  /**
   * Map model d.
   *
   * @param <D>             the type parameter
   * @param source          the source
   * @param destinationType the destination type
   * @return the d
   */
  private <D> D mapModel(final Object source, final Class<D> destinationType) {
    if (Objects.isNull(source)) {
      return null;
    } else {
      return this.exactNameModelMapper.map(source, destinationType);
    }
  }
}
