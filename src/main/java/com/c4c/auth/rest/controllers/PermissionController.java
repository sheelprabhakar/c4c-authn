package com.c4c.auth.rest.controllers;

import static com.c4c.auth.common.Constants.FORBIDDEN_MESSAGE;
import static com.c4c.auth.common.Constants.PERMISSION_NOT_FOUND_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_PERMISSION_ITEM_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_PERMISSION_ITEM_OPERATION;
import static com.c4c.auth.common.Constants.SWG_PERMISSION_LIST_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_PERMISSION_LIST_OPERATION;
import static com.c4c.auth.common.Constants.UNAUTHORIZED_MESSAGE;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.entities.Permission;
import com.c4c.auth.core.services.api.PermissionService;
import com.c4c.auth.rest.response.BadRequestResponse;
import com.c4c.auth.rest.response.PermissionListResponse;
import com.c4c.auth.rest.response.PermissionResponse;
import com.c4c.auth.rest.response.RoleListResponse;
import com.c4c.auth.rest.response.RoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/permissions")
public class PermissionController {

  private final PermissionService permissionService;

  public PermissionController(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @Operation(summary = SWG_PERMISSION_LIST_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_PERMISSION_LIST_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RoleListResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),})
  @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
  @GetMapping
  public ResponseEntity<PermissionListResponse> all() {
    return ResponseEntity.ok(new PermissionListResponse(permissionService.findAll()));
  }

  @Operation(summary = SWG_PERMISSION_ITEM_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_PERMISSION_ITEM_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "404", description = PERMISSION_NOT_FOUND_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),})
  @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<PermissionResponse> one(@PathVariable String id)
      throws ResourceNotFoundException {
    Optional<Permission> permission = permissionService.findById(id);

    if (permission.isEmpty()) {
      throw new ResourceNotFoundException(PERMISSION_NOT_FOUND_MESSAGE);
    }

    return ResponseEntity.ok(new PermissionResponse(permission.get()));
  }
}
