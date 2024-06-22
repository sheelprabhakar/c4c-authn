package com.c4c.auth.rest.controllers;

import static com.c4c.auth.common.Constants.FORBIDDEN_MESSAGE;
import static com.c4c.auth.common.Constants.INVALID_DATA_MESSAGE;
import static com.c4c.auth.common.Constants.PASSWORD_NOT_MATCH_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_DELETE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_DELETE_OPERATION;
import static com.c4c.auth.common.Constants.SWG_USER_ITEM_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_ITEM_OPERATION;
import static com.c4c.auth.common.Constants.SWG_USER_LIST_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_LIST_OPERATION;
import static com.c4c.auth.common.Constants.SWG_USER_LOGGED_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_LOGGED_OPERATION;
import static com.c4c.auth.common.Constants.SWG_USER_PERMISSION_ASSIGN_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_PERMISSION_ASSIGN_OPERATION;
import static com.c4c.auth.common.Constants.SWG_USER_PERMISSION_REVOKE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_PERMISSION_REVOKE_OPERATION;
import static com.c4c.auth.common.Constants.SWG_USER_PICTURE_ERROR;
import static com.c4c.auth.common.Constants.SWG_USER_PICTURE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_PICTURE_OPERATION;
import static com.c4c.auth.common.Constants.SWG_USER_UPDATE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_UPDATE_OPERATION;
import static com.c4c.auth.common.Constants.SWG_USER_UPDATE_PWD_ERROR;
import static com.c4c.auth.common.Constants.SWG_USER_UPDATE_PWD_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_USER_UPDATE_PWD_OPERATION;
import static com.c4c.auth.common.Constants.UNAUTHORIZED_MESSAGE;
import static com.c4c.auth.common.Constants.USER_PICTURE_NO_ACTION_MESSAGE;

import com.c4c.auth.common.exceptions.PasswordNotMatchException;
import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.models.dtos.UpdatePasswordDto;
import com.c4c.auth.core.models.dtos.UpdateUserDto;
import com.c4c.auth.core.models.dtos.UpdateUserPermissionDto;
import com.c4c.auth.core.models.entities.Permission;
import com.c4c.auth.core.models.entities.User;
import com.c4c.auth.core.services.FileStorageServiceImpl;
import com.c4c.auth.core.services.api.PermissionService;
import com.c4c.auth.core.services.api.UserService;
import com.c4c.auth.rest.response.BadRequestResponse;
import com.c4c.auth.rest.response.InvalidDataResponse;
import com.c4c.auth.rest.response.SuccessResponse;
import com.c4c.auth.rest.response.UserListResponse;
import com.c4c.auth.rest.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/users")
@Validated
public class UserController {
  private final Log logger = LogFactory.getLog(this.getClass());

  private final UserService userService;

  private final PermissionService permissionService;

  private final FileStorageServiceImpl fileStorageServiceImpl;

  public UserController(UserService userService, PermissionService permissionService,
                        FileStorageServiceImpl fileStorageServiceImpl) {
    this.userService = userService;
    this.permissionService = permissionService;
    this.fileStorageServiceImpl = fileStorageServiceImpl;
  }

  @Operation(summary = SWG_USER_LIST_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_USER_LIST_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserListResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
  })
  @PreAuthorize("hasAuthority('read:users')")
  @GetMapping
  public ResponseEntity<UserListResponse> all() {
    return ResponseEntity.ok(new UserListResponse(userService.findAll()));
  }

  @Operation(summary = SWG_USER_LOGGED_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_USER_LOGGED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
  })
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/me")
  public ResponseEntity<UserResponse> currentUser() throws ResourceNotFoundException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return ResponseEntity.ok(new UserResponse(userService.findByEmail(authentication.getName())));
  }

  @Operation(summary = SWG_USER_ITEM_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_USER_ITEM_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
  })
  @PreAuthorize("hasAuthority('read:user')")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> one(@PathVariable String id)
      throws ResourceNotFoundException {
    return ResponseEntity.ok(new UserResponse(userService.findById(id)));
  }

  @Operation(summary = SWG_USER_UPDATE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_USER_UPDATE_MESSAGE, content = {
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
  @PreAuthorize("hasAuthority('update:user')")
  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable String id,
                                             @RequestBody UpdateUserDto updateUserDto)
      throws ResourceNotFoundException {
    return ResponseEntity.ok(new UserResponse(userService.update(id, updateUserDto)));
  }

  @Operation(summary = SWG_USER_UPDATE_PWD_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_USER_UPDATE_PWD_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_USER_UPDATE_PWD_ERROR, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
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
  @PreAuthorize("hasAuthority('change:password')")
  @PutMapping("/{id}/password")
  public ResponseEntity<UserResponse> updatePassword(
      @PathVariable String id, @Valid @RequestBody UpdatePasswordDto updatePasswordDto
  ) throws PasswordNotMatchException, ResourceNotFoundException {
    User user = userService.updatePassword(id, updatePasswordDto);

    if (user == null) {
      throw new PasswordNotMatchException(PASSWORD_NOT_MATCH_MESSAGE);
    }

    return ResponseEntity.ok(new UserResponse(user));
  }

  @Operation(summary = SWG_USER_DELETE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = SWG_USER_DELETE_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
  })
  @PreAuthorize("hasAuthority('delete:user')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    userService.delete(id);

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = SWG_USER_PICTURE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_USER_PICTURE_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_USER_PICTURE_ERROR, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "401", description = UNAUTHORIZED_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "403", description = FORBIDDEN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = InvalidDataResponse.class))}),
  })
  @PreAuthorize("hasAuthority('change:picture')")
  @PostMapping("/{id}/picture")
  public ResponseEntity<UserResponse> uploadPicture(
      @PathVariable String id,
      @RequestParam(name = "file", required = false) MultipartFile file,
      @RequestParam("action")
      @Pattern(regexp = "[ud]", message = "The valid value can be \"u\" or \"d\"")
      @Size(max = 1, message = "This field length can't be greater than 1")
      @NotBlank(message = "This field is required")
      String action
  ) throws IOException, ResourceNotFoundException {
    User user = null;
    UpdateUserDto updateUserDto = new UpdateUserDto();

    if (action.equals("u")) {
      String fileName = fileStorageServiceImpl.storeFile(file);

      updateUserDto.setAvatar(fileName);

      user = userService.update(id, updateUserDto);
    } else if (action.equals("d")) {
      user = userService.findById(id);

      if (user.getAvatar() != null) {
        boolean deleted = fileStorageServiceImpl.deleteFile(user.getAvatar());

        if (deleted) {
          user.setAvatar(null);
          userService.update(user);
        }
      }
    } else {
      logger.info(USER_PICTURE_NO_ACTION_MESSAGE);
    }

    return ResponseEntity.ok().body(new UserResponse(user));
  }


  @Operation(summary = SWG_USER_PERMISSION_ASSIGN_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_USER_PERMISSION_ASSIGN_MESSAGE, content = {
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
  @PreAuthorize("hasAuthority('assign:permission')")
  @PutMapping("/{id}/permissions")
  public ResponseEntity<UserResponse> assignPermissions(@PathVariable String id, @Valid @RequestBody
  UpdateUserPermissionDto updateUserPermissionDto)
      throws ResourceNotFoundException {
    User user = userService.findById(id);

    Arrays.stream(updateUserPermissionDto.getPermissions()).forEach(permissionName -> {
      Optional<Permission> permission = permissionService.findByName(permissionName);

      if (permission.isPresent() && !user.hasPermission(permissionName)) {
        user.addPermission(permission.get());
      }
    });

    userService.update(user);

    return ResponseEntity.ok().body(new UserResponse(user));
  }

  @Operation(summary = SWG_USER_PERMISSION_REVOKE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_USER_PERMISSION_REVOKE_MESSAGE, content = {
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
              schema = @Schema(implementation = BadRequestResponse.class))}),
  })
  @PreAuthorize("hasAuthority('revoke:permission')")
  @DeleteMapping("/{id}/permissions")
  public ResponseEntity<User> revokePermissions(@PathVariable String id, @Valid @RequestBody
  UpdateUserPermissionDto updateUserPermissionDto)
      throws ResourceNotFoundException {
    User user = userService.findById(id);

    Arrays.stream(updateUserPermissionDto.getPermissions()).forEach(permissionName -> {
      Optional<Permission> permission = permissionService.findByName(permissionName);

      if (permission.isPresent() && user.hasPermission(permissionName)) {
        user.removePermission(permission.get());
      }
    });

    userService.update(user);

    return ResponseEntity.ok().body(user);
  }
}
