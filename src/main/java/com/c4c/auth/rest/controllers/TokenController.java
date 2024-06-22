package com.c4c.auth.rest.controllers;

import static com.c4c.auth.common.Constants.INVALID_DATA_MESSAGE;
import static com.c4c.auth.common.Constants.INVALID_TOKEN_MESSAGE;
import static com.c4c.auth.common.Constants.JWT_EXPIRED_MESSAGE;
import static com.c4c.auth.common.Constants.JWT_ILLEGAL_ARGUMENT_MESSAGE;
import static com.c4c.auth.common.Constants.JWT_SIGNATURE_MESSAGE;
import static com.c4c.auth.common.Constants.MESSAGE_KEY;
import static com.c4c.auth.common.Constants.SWG_TOKEN_REFRESH_ERROR;
import static com.c4c.auth.common.Constants.SWG_TOKEN_REFRESH_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_TOKEN_REFRESH_OPERATION;
import static com.c4c.auth.common.Constants.SWG_TOKEN_VALIDATE_ERROR;
import static com.c4c.auth.common.Constants.SWG_TOKEN_VALIDATE_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_TOKEN_VALIDATE_OPERATION;
import static com.c4c.auth.common.Constants.TOKEN_NOT_FOUND_MESSAGE;
import static com.c4c.auth.common.Constants.VALIDATE_TOKEN_SUCCESS_MESSAGE;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.common.utils.JwtTokenUtil;
import com.c4c.auth.core.models.dtos.RefreshTokenDto;
import com.c4c.auth.core.models.dtos.ValidateTokenDto;
import com.c4c.auth.core.models.entities.RefreshToken;
import com.c4c.auth.core.models.entities.User;
import com.c4c.auth.core.repositories.RefreshTokenRepository;
import com.c4c.auth.core.services.api.UserService;
import com.c4c.auth.rest.response.AuthTokenResponse;
import com.c4c.auth.rest.response.BadRequestResponse;
import com.c4c.auth.rest.response.InvalidDataResponse;
import com.c4c.auth.rest.response.SuccessResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type TokenController.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class TokenController {

  private final Log logger = LogFactory.getLog(this.getClass());

  private final JwtTokenUtil jwtTokenUtil;

  private final RefreshTokenRepository refreshTokenRepository;

  private final UserService userService;

  /**
   * Instantiates a new Token controller.
   *
   * @param jwtTokenUtil           the jwt token util
   * @param refreshTokenRepository the refresh token repository
   * @param userService            the user service
   */
  public TokenController(
      JwtTokenUtil jwtTokenUtil,
      RefreshTokenRepository refreshTokenRepository,
      UserService userService
  ) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.refreshTokenRepository = refreshTokenRepository;
    this.userService = userService;
  }

  /**
   * Validate response entity.
   *
   * @param validateTokenDto the validate token dto
   * @return the response entity
   */
  @Operation(summary = SWG_TOKEN_VALIDATE_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_TOKEN_VALIDATE_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_TOKEN_VALIDATE_ERROR, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = InvalidDataResponse.class))}),
  })
  @PostMapping(value = "/validate")
  public ResponseEntity<Map<String, String>> validate(
      @Valid @RequestBody ValidateTokenDto validateTokenDto) {
    String username = null;
    Map<String, String> result = new HashMap<>();

    try {
      username = jwtTokenUtil.getUsernameFromToken(validateTokenDto.getToken());
    } catch (IllegalArgumentException e) {
      logger.error(JWT_ILLEGAL_ARGUMENT_MESSAGE, e);
      result.put(MESSAGE_KEY, JWT_ILLEGAL_ARGUMENT_MESSAGE);
    } catch (ExpiredJwtException e) {
      logger.warn(JWT_EXPIRED_MESSAGE, e);
      result.put(MESSAGE_KEY, JWT_EXPIRED_MESSAGE);
    } catch (SignatureException e) {
      logger.error(JWT_SIGNATURE_MESSAGE);
      result.put(MESSAGE_KEY, JWT_SIGNATURE_MESSAGE);
    }

    if (username != null) {
      result.put(MESSAGE_KEY, VALIDATE_TOKEN_SUCCESS_MESSAGE);
      return ResponseEntity.ok(result);
    }

    return ResponseEntity.badRequest().body(result);
  }

  /**
   * Refresh response entity.
   *
   * @param refreshTokenDto the refresh token dto
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @Operation(summary = SWG_TOKEN_REFRESH_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_TOKEN_REFRESH_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = AuthTokenResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_TOKEN_REFRESH_ERROR, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = InvalidDataResponse.class))}),
  })
  @PostMapping(value = "/refresh")
  public ResponseEntity<Object> refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto)
      throws ResourceNotFoundException {
    RefreshToken refreshToken = refreshTokenRepository.findByValue(refreshTokenDto.getToken());
    Map<String, String> result = new HashMap<>();

    if (refreshToken == null) {
      result.put(MESSAGE_KEY, INVALID_TOKEN_MESSAGE);
      return ResponseEntity.badRequest().body(result);
    }

    User user = userService.findById(refreshToken.getId());
    if (user == null) {
      result.put(MESSAGE_KEY, TOKEN_NOT_FOUND_MESSAGE);
      return ResponseEntity.badRequest().body(result);
    }

    String token = jwtTokenUtil.createTokenFromUser(user);
    Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);

    return ResponseEntity.ok(
        new AuthTokenResponse(token, refreshToken.getValue(), expirationDate.getTime()));
  }
}
