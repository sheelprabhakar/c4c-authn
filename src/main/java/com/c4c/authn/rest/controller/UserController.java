package com.c4c.authn.rest.controller;

import com.c4c.authn.adapter.RestAdapterV1;
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
  static final String BASE_URL = "/api/v1/user";

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
   * Add response entity.
   *
   * @param userResource the user resource
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<UserResource> add(final @RequestBody @Validated UserResource userResource) {
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
    UserResource resource = this.getRestAdapterV1().update(userResource);
    return ResponseEntity.ok(resource);
  }

}
