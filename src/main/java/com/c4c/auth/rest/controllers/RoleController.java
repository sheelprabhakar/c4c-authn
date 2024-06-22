package com.c4c.auth.rest.controllers;

import static com.c4c.auth.common.Constants.FORBIDDEN_MESSAGE;
import static com.c4c.auth.common.Constants.INVALID_DATA_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ROLE_ASSIGN_PERMISSION_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ROLE_ASSIGN_PERMISSION_OPERATION;
import static com.c4c.auth.common.Constants.SWG_ROLE_CREATE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ROLE_CREATE_OPERATION;
import static com.c4c.auth.common.Constants.SWG_ROLE_DELETE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ROLE_DELETE_OPERATION;
import static com.c4c.auth.common.Constants.SWG_ROLE_ITEM_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ROLE_ITEM_OPERATION;
import static com.c4c.auth.common.Constants.SWG_ROLE_LIST_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ROLE_LIST_OPERATION;
import static com.c4c.auth.common.Constants.SWG_ROLE_REMOVE_PERMISSION_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ROLE_REMOVE_PERMISSION_OPERATION;
import static com.c4c.auth.common.Constants.SWG_ROLE_UPDATE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ROLE_UPDATE_OPERATION;
import static com.c4c.auth.common.Constants.UNAUTHORIZED_MESSAGE;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.dtos.CreateRoleDto;
import com.c4c.auth.core.models.dtos.UpdateRolePermissionDto;
import com.c4c.auth.core.models.entities.Permission;
import com.c4c.auth.core.models.entities.Role;
import com.c4c.auth.core.services.api.PermissionService;
import com.c4c.auth.core.services.api.RoleService;
import com.c4c.auth.rest.response.BadRequestResponse;
import com.c4c.auth.rest.response.InvalidDataResponse;
import com.c4c.auth.rest.response.RoleListResponse;
import com.c4c.auth.rest.response.RoleResponse;
import com.c4c.auth.rest.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type RoleController.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/roles")
public class RoleController {
  private final RoleService roleService;

  private final PermissionService permissionService;

  /**
   * Instantiates a new Role controller.
   *
   * @param permissionService the permission service
   * @param roleService       the role service
   */
  public RoleController(PermissionService permissionService, RoleService roleService) {
    this.roleService = roleService;
    this.permissionService = permissionService;
  }

  /**
   * Create response entity.
   *
   * @param createRoleDto the create role dto
   * @return the response entity
   */
  @Operation(summary = SWG_ROLE_CREATE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_ROLE_CREATE_MESSAGE,
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Role.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = InvalidDataResponse.class))}),
  })
  @PreAuthorize("hasAuthority('create:role')")
  @PostMapping
  public ResponseEntity<Role> create(@Valid @RequestBody CreateRoleDto createRoleDto) {
    Role role = roleService.save(createRoleDto);

    return ResponseEntity.ok(role);
  }

  /**
   * All response entity.
   *
   * @return the response entity
   */
  @Operation(summary = SWG_ROLE_LIST_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_ROLE_LIST_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = RoleListResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
  })
  @PreAuthorize("hasAuthority('read:roles')")
  @GetMapping
  public ResponseEntity<RoleListResponse> all() {
    return ResponseEntity.ok(new RoleListResponse(roleService.findAll()));
  }

  /**
   * One response entity.
   *
   * @param id the id
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @Operation(summary = SWG_ROLE_ITEM_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_ROLE_ITEM_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = RoleListResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
  })
  @PreAuthorize("hasAuthority('read:role')")
  @GetMapping("/{id}")
  public ResponseEntity<RoleResponse> one(@PathVariable String id)
      throws ResourceNotFoundException {
    Role role = roleService.findById(id);

    return ResponseEntity.ok(new RoleResponse(role));
  }

  /**
   * Update response entity.
   *
   * @param id            the id
   * @param createRoleDto the create role dto
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @Operation(summary = SWG_ROLE_UPDATE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_ROLE_UPDATE_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = RoleResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = InvalidDataResponse.class))}),
  })
  @PreAuthorize("hasAuthority('update:role')")
  @PutMapping("/{id}")
  public ResponseEntity<RoleResponse> update(@PathVariable String id,
                                             @Valid @RequestBody CreateRoleDto createRoleDto)
      throws ResourceNotFoundException {
    return ResponseEntity.ok(new RoleResponse(roleService.update(id, createRoleDto)));
  }

  /**
   * Delete response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @Operation(summary = SWG_ROLE_DELETE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = SWG_ROLE_DELETE_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
  })
  @PreAuthorize("hasAuthority('delete:role')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    roleService.delete(id);

    return ResponseEntity.noContent().build();
  }

  /**
   * Add permissions response entity.
   *
   * @param id                      the id
   * @param updateRolePermissionDto the update role permission dto
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @Operation(summary = SWG_ROLE_ASSIGN_PERMISSION_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_ROLE_ASSIGN_PERMISSION_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = InvalidDataResponse.class))}),
  })
  @PreAuthorize("hasAuthority('add:permission')")
  @PutMapping("/{id}/permissions")
  public ResponseEntity<RoleResponse> addPermissions(@PathVariable String id, @Valid @RequestBody
  UpdateRolePermissionDto updateRolePermissionDto)
      throws ResourceNotFoundException {
    Role role = roleService.findById(id);

    Arrays.stream(updateRolePermissionDto.getPermissions()).forEach(permissionName -> {
      Optional<Permission> permission = permissionService.findByName(permissionName);

      if (permission.isPresent() && !role.hasPermission(permissionName)) {
        role.addPermission(permission.get());
      }
    });

    Role roleUpdated = roleService.update(role);

    return ResponseEntity.ok().body(new RoleResponse(roleUpdated));
  }

  /**
   * Remove permissions response entity.
   *
   * @param id                      the id
   * @param updateRolePermissionDto the update role permission dto
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @Operation(summary = SWG_ROLE_REMOVE_PERMISSION_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_ROLE_REMOVE_PERMISSION_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = InvalidDataResponse.class))}),
  })
  @PreAuthorize("hasAuthority('remove:permission')")
  @DeleteMapping("/{id}/permissions")
  public ResponseEntity<RoleResponse> removePermissions(@PathVariable String id, @Valid @RequestBody
  UpdateRolePermissionDto updateRolePermissionDto)
      throws ResourceNotFoundException {
    Role role = roleService.findById(id);

    Arrays.stream(updateRolePermissionDto.getPermissions()).forEach(permissionName -> {
      Optional<Permission> permission = permissionService.findByName(permissionName);

      if (permission.isPresent() && role.hasPermission(permissionName)) {
        role.removePermission(permission.get());
      }
    });

    Role roleUpdated = roleService.update(role);

    return ResponseEntity.ok().body(new RoleResponse(roleUpdated));
  }
}
