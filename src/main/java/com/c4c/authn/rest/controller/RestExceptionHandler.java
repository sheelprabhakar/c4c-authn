package com.c4c.authn.rest.controller;

import jakarta.validation.ConstraintViolationException;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Rest exception handler.
 */
@ControllerAdvice
@Slf4j
public final class RestExceptionHandler extends ResponseEntityExceptionHandler {
  /**
   * Handle bad credential exception response entity.
   *
   * @param ex      the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentialException(
      final BadCredentialsException ex, final WebRequest request) {

    return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
  }

  /**
   * Constraint violation exception response entity.
   *
   * @param ex      the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({SQLIntegrityConstraintViolationException.class,
      DataIntegrityViolationException.class,
      ConstraintViolationException.class})
  public ResponseEntity<Object> constraintViolationException(
      final RuntimeException ex, final WebRequest request) {
    log.debug("DATA_INTEGRITY_VIOLATION", ex);
    return new ResponseEntity<>(ExceptionMessage.builder().code("DATA_INTEGRITY_VIOLATION")
        .message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
  }

  /**
   * The type Exception message.
   */
  @Builder
  @AllArgsConstructor
  @Getter
  static class ExceptionMessage implements Serializable {
    /**
     * The Code.
     */
    private String code;
    /**
     * The Message.
     */
    private String message;
  }
}
