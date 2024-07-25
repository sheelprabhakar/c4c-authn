package com.c4c.authn.rest.controller;

import com.c4c.authn.adapter.api.RestAdapterV1;
import com.c4c.authn.config.tenant.CurrentUserContext;
import com.c4c.authn.rest.resource.UserResource;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.c4c.authn.common.Constants.API_V1;
import static com.c4c.authn.common.Constants.USER_URL;

/**
 * The type User controller.
 */
@Slf4j
@RestController()
@RequestMapping(UserController.BASE_URL)
public class UserController extends BaseController {
  /**
   * The Base url.
   */
  static final String BASE_URL = API_V1 + USER_URL;

  /**
   * Instantiates a new User controller.
   *
   * @param restAdapterV1 the rest adapter v 1
   */
  @Autowired
  public UserController(final RestAdapterV1 restAdapterV1) {
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
    userResource.setTenantId(CurrentUserContext.getCurrentTenant());
    UserResource resource = this.getRestAdapterV1().save(userResource);
    return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId())).body(resource);
  }

  /**
   * Update response entity.
   *
   * @param userResource the user resource
   * @return the response entity
   */
  @PutMapping
  public ResponseEntity<UserResource> update(final @RequestBody UserResource userResource) {
    userResource.setTenantId(CurrentUserContext.getCurrentTenant());
    UserResource resource = this.getRestAdapterV1().update(userResource);
    return ResponseEntity.ok(resource);
  }

}
