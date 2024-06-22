package com.c4c.auth.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type PasswordNotMatchException.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PasswordNotMatchException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Password not match exception.
   *
   * @param message the message
   */
  public PasswordNotMatchException(String message) {
    super(message);
  }
}