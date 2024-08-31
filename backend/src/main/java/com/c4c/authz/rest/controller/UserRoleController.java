package com.c4c.authz.rest.controller;

import static com.c4c.authz.common.Constants.API_V1;
import static com.c4c.authz.common.Constants.USER_ROLE_URL;
import static com.c4c.authz.rest.controller.UserRoleController.BASE_URL;

import com.c4c.authz.adapter.api.RestAdapterV1;
import com.c4c.authz.rest.resource.PagedModelResponse;
import com.c4c.authz.rest.resource.user.UserRoleResource;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type User role controller.
 */
@Slf4j
@RestController()
@RequestMapping(value = BASE_URL)
public class UserRoleController extends BaseController {
  /**
   * The constant BASE_URL.
   */
  static final String BASE_URL = API_V1 + USER_ROLE_URL;

  /**
   * Instantiates a new User role controller.
   *
   * @param restAdapterV1 the rest adapter v 1
   */
  @Autowired
    protected UserRoleController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

  /**
   * Find by id response entity.
   *
   * @param userId the user id
   * @param roleId the role id
   * @return the response entity
   */
  @GetMapping(value = "/{userId}/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRoleResource> findById(@PathVariable(value = "userId") final UUID userId,
                                                     @PathVariable(value = "roleId") final UUID roleId) {
        UserRoleResource resource = this.getRestAdapterV1().findByIdUserRole(userId, roleId);
        if (!Objects.isNull(resource)) {
            return ResponseEntity.ok().body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

  /**
   * Find by pagination response entity.
   *
   * @param pageNo   the page no
   * @param pageSize the page size
   * @return the response entity
   */
  @GetMapping
    public ResponseEntity<PagedModelResponse<UserRoleResource>> findByPagination(
            @RequestParam(value = "pageNo", required = false, defaultValue = "-1") final int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "-1") final int pageSize) {
        if (pageSize > 0) {
            Page<UserRoleResource> resources = this.getRestAdapterV1().findByPaginationUserRole(pageNo, pageSize);
            return ResponseEntity.ok().body(new PagedModelResponse<>(resources));
        } else {
            List<UserRoleResource> resources = this.getRestAdapterV1().findAllUserRole();
            return ResponseEntity.ok().body(new PagedModelResponse<>(new PageImpl<>(resources)));
        }
    }

  /**
   * Create response entity.
   *
   * @param userRoleResource the user role resource
   * @return the response entity
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRoleResource> create(final @RequestBody @Validated UserRoleResource userRoleResource) {
        UserRoleResource resource = this.getRestAdapterV1().createUserRole(userRoleResource);
        return ResponseEntity.created(
                        URI.create(BASE_URL + "?userId=" + resource.getUserId() + "&roleId=" + resource.getRoleId()))
                .body(resource);
    }

  /**
   * Update response entity.
   *
   * @param userRoleResource the user role resource
   * @return the response entity
   */
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRoleResource> update(final @RequestBody @Validated UserRoleResource userRoleResource) {
        UserRoleResource resource = this.getRestAdapterV1().updateUserRole(userRoleResource);
        return ResponseEntity.ok().body(resource);
    }

  /**
   * Delete by id response entity.
   *
   * @param userId the user id
   * @param roleId the role id
   * @return the response entity
   */
  @DeleteMapping(value = "/{userId}/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteById(@PathVariable(value = "userId") final UUID userId,
                                           @PathVariable(value = "roleId") final UUID roleId) {
        this.getRestAdapterV1().deleteByIdUserRole(userId, roleId);
        return ResponseEntity.noContent().build();
    }
}
