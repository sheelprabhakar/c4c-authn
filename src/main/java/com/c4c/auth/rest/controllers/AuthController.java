package com.c4c.auth.rest.controllers;

import static com.c4c.auth.common.Constants.ACCOUNT_CONFIRMED_MESSAGE;
import static com.c4c.auth.common.Constants.ACCOUNT_DEACTIVATED_MESSAGE;
import static com.c4c.auth.common.Constants.ACCOUNT_NOT_CONFIRMED_MESSAGE;
import static com.c4c.auth.common.Constants.DATA_KEY;
import static com.c4c.auth.common.Constants.INVALID_DATA_MESSAGE;
import static com.c4c.auth.common.Constants.MESSAGE_KEY;
import static com.c4c.auth.common.Constants.ROLE_NOT_FOUND_MESSAGE;
import static com.c4c.auth.common.Constants.ROLE_USER;
import static com.c4c.auth.common.Constants.SWG_AUTH_CONFIRM_ACCOUNT_ERROR;
import static com.c4c.auth.common.Constants.SWG_AUTH_CONFIRM_ACCOUNT_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_AUTH_CONFIRM_ACCOUNT_OPERATION;
import static com.c4c.auth.common.Constants.SWG_AUTH_LOGIN_ERROR;
import static com.c4c.auth.common.Constants.SWG_AUTH_LOGIN_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_AUTH_LOGIN_OPERATION;
import static com.c4c.auth.common.Constants.SWG_AUTH_REGISTER_ERROR;
import static com.c4c.auth.common.Constants.SWG_AUTH_REGISTER_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_AUTH_REGISTER_OPERATION;
import static com.c4c.auth.common.Constants.TOKEN_EXPIRED_MESSAGE;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.common.utils.Helpers;
import com.c4c.auth.common.utils.JwtTokenUtil;
import com.c4c.auth.core.events.OnRegistrationCompleteEvent;
import com.c4c.auth.core.models.dtos.CreateUserDto;
import com.c4c.auth.core.models.dtos.LoginUserDto;
import com.c4c.auth.core.models.dtos.ValidateTokenDto;
import com.c4c.auth.core.models.entities.RefreshToken;
import com.c4c.auth.core.models.entities.Role;
import com.c4c.auth.core.models.entities.User;
import com.c4c.auth.core.models.entities.UserAccount;
import com.c4c.auth.core.repositories.RefreshTokenRepository;
import com.c4c.auth.core.services.api.RoleService;
import com.c4c.auth.core.services.api.UserAccountService;
import com.c4c.auth.core.services.api.UserService;
import com.c4c.auth.rest.response.AuthTokenResponse;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type AuthController.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final UserService userService;

  private final RoleService roleService;

  private final AuthenticationManager authenticationManager;

  private final JwtTokenUtil jwtTokenUtil;

  private final RefreshTokenRepository refreshTokenRepository;

  private final ApplicationEventPublisher eventPublisher;

  private final UserAccountService userAccountService;

  /**
   * Instantiates a new Auth controller.
   *
   * @param authenticationManager  the authentication manager
   * @param jwtTokenUtil           the jwt token util
   * @param userService            the user service
   * @param roleService            the role service
   * @param refreshTokenRepository the refresh token repository
   * @param eventPublisher         the event publisher
   * @param userAccountService     the user account service
   */
  public AuthController(
      AuthenticationManager authenticationManager,
      JwtTokenUtil jwtTokenUtil,
      UserService userService,
      RoleService roleService,
      RefreshTokenRepository refreshTokenRepository,
      ApplicationEventPublisher eventPublisher,
      UserAccountService userAccountService
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userService = userService;
    this.roleService = roleService;
    this.refreshTokenRepository = refreshTokenRepository;
    this.eventPublisher = eventPublisher;
    this.userAccountService = userAccountService;
  }

  /**
   * Register response entity.
   *
   * @param createUserDto the create user dto
   * @return the response entity
   */
  @Operation(summary = SWG_AUTH_REGISTER_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_AUTH_REGISTER_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_AUTH_REGISTER_ERROR, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = InvalidDataResponse.class))}),
  })
  @PostMapping(value = "/register")
  public ResponseEntity<Object> register(@Valid @RequestBody CreateUserDto createUserDto) {
    try {
      Role roleUser = roleService.findByName(ROLE_USER);

      createUserDto.setRole(roleUser);

      User user = userService.save(createUserDto);

      eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));

      return ResponseEntity.ok(user);
    } catch (ResourceNotFoundException e) {
      Map<String, String> result = new HashMap<>();
      result.put("message", SWG_AUTH_REGISTER_ERROR);

      logger.error("Register User: " + ROLE_NOT_FOUND_MESSAGE);

      return ResponseEntity.badRequest().body(result);
    }
  }

  /**
   * Login response entity.
   *
   * @param loginUserDto the login user dto
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @Operation(summary = SWG_AUTH_LOGIN_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_AUTH_LOGIN_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = AuthTokenResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_AUTH_LOGIN_ERROR, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = InvalidDataResponse.class))}),
  })
  @PostMapping(value = "/login")
  public ResponseEntity<Object> login(@Valid @RequestBody LoginUserDto loginUserDto)
      throws ResourceNotFoundException {
    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUserDto.getEmail(),
            loginUserDto.getPassword()
        )
    );

    User user = userService.findByEmail(loginUserDto.getEmail());
    Map<String, String> result = new HashMap<>();

    if (!user.isEnabled()) {
      result.put(DATA_KEY, ACCOUNT_DEACTIVATED_MESSAGE);

      return ResponseEntity.badRequest().body(result);
    }

    if (!user.isConfirmed()) {
      result.put(DATA_KEY, ACCOUNT_NOT_CONFIRMED_MESSAGE);

      return ResponseEntity.badRequest().body(result);
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);

    final String token = jwtTokenUtil.createTokenFromAuth(authentication);
    Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);
    String refreshToken = Helpers.generateRandomString(25);

    refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));

    return ResponseEntity.ok(new AuthTokenResponse(token, refreshToken, expirationDate.getTime()));
  }

  /**
   * Confirm account response entity.
   *
   * @param validateTokenDto the validate token dto
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @Operation(summary = SWG_AUTH_CONFIRM_ACCOUNT_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_AUTH_CONFIRM_ACCOUNT_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_AUTH_CONFIRM_ACCOUNT_ERROR, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
  })
  @PostMapping(value = "/confirm-account")
  public ResponseEntity<Object> confirmAccount(
      @Valid @RequestBody ValidateTokenDto validateTokenDto)
      throws ResourceNotFoundException {
    UserAccount userAccount = userAccountService.findByToken(validateTokenDto.getToken());
    Map<String, String> result = new HashMap<>();

    if (userAccount.isExpired()) {
      result.put(MESSAGE_KEY, TOKEN_EXPIRED_MESSAGE);

      userAccountService.delete(userAccount.getId());

      return ResponseEntity.badRequest().body(result);
    }

    userService.confirm(userAccount.getUser().getId());

    result.put(MESSAGE_KEY, ACCOUNT_CONFIRMED_MESSAGE);

    return ResponseEntity.badRequest().body(result);
  }
}
