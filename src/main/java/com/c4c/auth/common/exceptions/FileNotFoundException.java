package com.c4c.auth.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type FileNotFoundException.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new File not found exception.
   *
   * @param message the message
   */
  public FileNotFoundException(String message) {
    super(message);
  }

  /**
   * Instantiates a new File not found exception.
   *
   * @param message the message
   * @param cause   the cause
   */
  public FileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}