package com.c4c.auth.rest.controllers;

import static com.c4c.auth.common.Constants.FORBIDDEN_MESSAGE;
import static com.c4c.auth.common.Constants.INVALID_DATA_MESSAGE;
import static com.c4c.auth.common.Constants.ROLE_ADMIN;
import static com.c4c.auth.common.Constants.SWG_ADMIN_CREATE_ERROR;
import static com.c4c.auth.common.Constants.SWG_ADMIN_CREATE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ADMIN_CREATE_OPERATION;
import static com.c4c.auth.common.Constants.SWG_ADMIN_DELETE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_ADMIN_DELETE_OPERATION;
import static com.c4c.auth.common.Constants.UNAUTHORIZED_MESSAGE;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.dtos.CreateUserDto;
import com.c4c.auth.core.models.entities.Role;
import com.c4c.auth.core.models.entities.User;
import com.c4c.auth.core.services.api.RoleService;
import com.c4c.auth.core.services.api.UserService;
import com.c4c.auth.rest.response.BadRequestResponse;
import com.c4c.auth.rest.response.InvalidDataResponse;
import com.c4c.auth.rest.response.SuccessResponse;
import com.c4c.auth.rest.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admins")
public class AdminController {
  private final RoleService roleService;

  private final UserService userService;

  public AdminController(RoleService roleService, UserService userService) {
    this.roleService = roleService;
    this.userService = userService;
  }

  @Operation(summary = SWG_ADMIN_CREATE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_ADMIN_CREATE_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_ADMIN_CREATE_ERROR, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidDataResponse.class))}),})
  @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
  @PostMapping(value = "")
  public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserDto createUserDto)
      throws ResourceNotFoundException {
    Role roleUser = roleService.findByName(ROLE_ADMIN);

    createUserDto.setRole(roleUser).setConfirmed(true).setEnabled(true);

    User user = userService.save(createUserDto);

    return ResponseEntity.ok(new UserResponse(user));
  }

  @Operation(summary = SWG_ADMIN_DELETE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = SWG_ADMIN_DELETE_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),})
  @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    userService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
