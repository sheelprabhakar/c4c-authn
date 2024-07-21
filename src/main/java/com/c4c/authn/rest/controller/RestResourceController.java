package com.c4c.authn.rest.controller;

import static com.c4c.authn.rest.controller.RestResourceController.BASE_URL;

import com.c4c.authn.adapter.api.RestAdapterV1;
import com.c4c.authn.rest.resource.UserResource;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Rest resource controller.
 */
@Slf4j
@RestController()
@RequestMapping(BASE_URL)
public class RestResourceController extends BaseController {
  /**
   * The Base url.
   */
  static final String BASE_URL = "/api/v1/restResource";

  /**
   * Instantiates a new Rest resource controller.
   *
   * @param restAdapterV1 the rest adapter v 1
   */
  @Autowired
  protected RestResourceController(final RestAdapterV1 restAdapterV1) {
    super(restAdapterV1);
  }

  /**
   * Create response entity.
   *
   * @param userResource the user resource
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<UserResource> create(final @RequestBody @Validated UserResource userResource) {
    UserResource resource = this.getRestAdapterV1().save(userResource);
    return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId())).body(resource);
  }
}
