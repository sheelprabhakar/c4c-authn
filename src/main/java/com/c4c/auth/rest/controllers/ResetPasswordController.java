package com.c4c.auth.rest.controllers;

import static com.c4c.auth.common.Constants.INVALID_DATA_MESSAGE;
import static com.c4c.auth.common.Constants.MESSAGE_KEY;
import static com.c4c.auth.common.Constants.NO_USER_FOUND_WITH_EMAIL_MESSAGE;
import static com.c4c.auth.common.Constants.PASSWORD_LINK_SENT_MESSAGE;
import static com.c4c.auth.common.Constants.RESET_PASSWORD_SUCCESS_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_RESPWD_FORGOT_ERROR;
import static com.c4c.auth.common.Constants.SWG_RESPWD_FORGOT_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_RESPWD_FORGOT_OPERATION;
import static com.c4c.auth.common.Constants.SWG_RESPWD_RESET_ERROR;
import static com.c4c.auth.common.Constants.SWG_RESPWD_RESET_MESSAGE;
import static com.c4c.auth.common.Constants.SWG_RESPWD_RESET_OPERATION;
import static com.c4c.auth.common.Constants.TOKEN_EXPIRED_MESSAGE;

import com.c4c.auth.common.exceptions.ResourceNotFoundException;
import com.c4c.auth.core.events.OnResetPasswordEvent;
import com.c4c.auth.core.models.dtos.ForgotPasswordDto;
import com.c4c.auth.core.models.dtos.ResetPasswordDto;
import com.c4c.auth.core.models.entities.User;
import com.c4c.auth.core.models.entities.UserAccount;
import com.c4c.auth.core.services.api.UserAccountService;
import com.c4c.auth.core.services.api.UserService;
import com.c4c.auth.rest.response.BadRequestResponse;
import com.c4c.auth.rest.response.InvalidDataResponse;
import com.c4c.auth.rest.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class ResetPasswordController {

  private final UserService userService;

  private final ApplicationEventPublisher eventPublisher;

  private final UserAccountService userAccountService;

  public ResetPasswordController(UserService userService, ApplicationEventPublisher eventPublisher,
                                 UserAccountService userAccountService) {
    this.userService = userService;
    this.eventPublisher = eventPublisher;
    this.userAccountService = userAccountService;
  }

  @Operation(summary = SWG_RESPWD_FORGOT_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_RESPWD_FORGOT_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_RESPWD_FORGOT_ERROR, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidDataResponse.class))}),})
  @PostMapping(value = "/forgot-password")
  public ResponseEntity<Map<String, String>> forgotPassword(
      @Valid @RequestBody ForgotPasswordDto forgotPasswordDto) throws ResourceNotFoundException {
    User user = userService.findByEmail(forgotPasswordDto.getEmail());
    Map<String, String> result = new HashMap<>();

    if (user == null) {
      result.put(MESSAGE_KEY, NO_USER_FOUND_WITH_EMAIL_MESSAGE);

      return ResponseEntity.badRequest().body(result);
    }

    eventPublisher.publishEvent(new OnResetPasswordEvent(user));

    result.put(MESSAGE_KEY, PASSWORD_LINK_SENT_MESSAGE);

    return ResponseEntity.ok(result);
  }

  @Operation(summary = SWG_RESPWD_RESET_OPERATION)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = SWG_RESPWD_RESET_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))}),
      @ApiResponse(responseCode = "400", description = SWG_RESPWD_RESET_ERROR, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),
      @ApiResponse(responseCode = "422", description = INVALID_DATA_MESSAGE, content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequestResponse.class))}),})
  @PostMapping(value = "/reset-password")
  public ResponseEntity<Map<String, String>> resetPassword(
      @Valid @RequestBody ResetPasswordDto passwordResetDto) throws ResourceNotFoundException {
    UserAccount userAccount = userAccountService.findByToken(passwordResetDto.getToken());
    Map<String, String> result = new HashMap<>();

    if (userAccount.isExpired()) {
      result.put(MESSAGE_KEY, TOKEN_EXPIRED_MESSAGE);

      userAccountService.delete(userAccount.getId());

      return ResponseEntity.badRequest().body(result);
    }

    userService.updatePassword(userAccount.getUser().getId(), passwordResetDto.getPassword());

    result.put(MESSAGE_KEY, RESET_PASSWORD_SUCCESS_MESSAGE);

    userAccountService.delete(userAccount.getId());

    return ResponseEntity.badRequest().body(result);
  }
}
