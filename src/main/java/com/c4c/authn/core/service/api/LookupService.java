package com.c4c.authn.core.service.api;

import com.c4c.authn.core.entity.lookup.CityEntity;
import com.c4c.authn.core.entity.lookup.CountryEntity;
import com.c4c.authn.core.entity.lookup.StateEntity;
import java.util.List;

/**
 * The interface Lookup service.
 */
public interface LookupService {
  /**
   * Countries list.
   *
   * @return the list
   */
  List<CountryEntity> countries();

  /**
   * States list.
   *
   * @param countryId the country id
   * @return the list
   */
  List<StateEntity> states(int countryId);

  /**
   * Cities list.
   *
   * @param stateId the state id
   * @return the list
   */
  List<CityEntity> cities(int stateId);

  /**
   * Gets city by id.
   *
   * @param cityId the city id
   * @return the city by id
   */
  CityEntity getCityById(int cityId);
}

