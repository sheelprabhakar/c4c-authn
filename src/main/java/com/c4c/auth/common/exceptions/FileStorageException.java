package com.c4c.auth.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type FileStorageException.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileStorageException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new File storage exception.
   *
   * @param message the message
   */
  public FileStorageException(String message) {
    super(message);
  }

  /**
   * Instantiates a new File storage exception.
   *
   * @param message the message
   * @param cause   the cause
   */
  public FileStorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
