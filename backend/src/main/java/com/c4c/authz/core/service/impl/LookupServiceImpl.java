package com.c4c.authz.core.service.impl;

import com.c4c.authz.core.entity.lookup.CityEntity;
import com.c4c.authz.core.entity.lookup.CountryEntity;
import com.c4c.authz.core.entity.lookup.StateEntity;
import com.c4c.authz.core.repository.lookup.CityRepository;
import com.c4c.authz.core.repository.lookup.CountryRepository;
import com.c4c.authz.core.repository.lookup.StateRepository;
import com.c4c.authz.core.service.api.LookupService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Lookup service.
 */
@Service
@Slf4j
@Transactional
public class LookupServiceImpl implements LookupService {
  /**
   * The Country repository.
   */
  private final CountryRepository countryRepository;
  /**
   * The State repository.
   */
  private final StateRepository stateRepository;

  /**
   * The City repository.
   */
  private final CityRepository cityRepository;

  /**
   * Instantiates a new Lookup service.
   *
   * @param countryRepository the country repository
   * @param stateRepository   the state repository
   * @param cityRepository    the city repository
   */
  public LookupServiceImpl(final CountryRepository countryRepository, final StateRepository stateRepository,
                           final CityRepository cityRepository) {
    this.countryRepository = countryRepository;
    this.stateRepository = stateRepository;
    this.cityRepository = cityRepository;
  }

  /**
   * Countries list.
   *
   * @return the list
   */
  @Override
  public List<CountryEntity> countries() {
    return this.countryRepository.findAll();
  }

  /**
   * States list.
   *
   * @param countryId the country id
   * @return the list
   */
  public List<StateEntity> states(final int countryId) {
    return this.stateRepository.findByCountryId(countryId);
  }

  /**
   * Cities list.
   *
   * @param stateId the state id
   * @return the list
   */
  public List<CityEntity> cities(final int stateId) {
    return this.cityRepository.findByStateId(stateId);
  }

  /**
   * Gets city by id.
   *
   * @param cityId the city id
   * @return the city by id
   */
  @Override
  public CityEntity getCityById(final int cityId) {
    return this.cityRepository.findById(cityId).orElse(null);
  }
}

