package com.c4c.authz.rest.controller;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.rest.resource.lookup.CityResource;
import com.c4c.authz.rest.resource.lookup.CountryResource;
import com.c4c.authz.rest.resource.lookup.StateResource;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.LOOKUP_URL;

/**
 * The type Lookup controller.
 */
@Slf4j
@RestController()
@RequestMapping(LookupController.BASE_URL)
public class LookupController extends BaseController {
    /**
     * The constant BASE_URL.
     */
    static final String BASE_URL = API_V1 + LOOKUP_URL;

    /**
     * Instantiates a new Lookup controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
  public LookupController(final RestAdapterV1 restAdapterV1) {
    super(restAdapterV1);
  }

    /**
     * Countries response entity.
     *
     * @return the response entity
     */
    @GetMapping("/country")
  public ResponseEntity<List<CountryResource>> countries() {
    List<CountryResource> countryResources = this.getRestAdapterV1().countries();
    return ResponseEntity.ok(countryResources);
  }

    /**
     * States response entity.
     *
     * @param countryId the country id
     * @return the response entity
     */
    @GetMapping("/country/{countryId}/state")
  public ResponseEntity<List<StateResource>> states(
      final @PathVariable("countryId") int countryId) {
    List<StateResource> stateResources = this.getRestAdapterV1().states(countryId);
    return ResponseEntity.ok(stateResources);
  }

    /**
     * Cities response entity.
     *
     * @param stateId the state id
     * @return the response entity
     */
    @GetMapping("/state/{stateId}/city")
  public ResponseEntity<List<CityResource>> cities(
      final @PathVariable("stateId") int stateId) {
    List<CityResource> cityResources = this.getRestAdapterV1().cities(stateId);
    return ResponseEntity.ok(cityResources);
  }
}
