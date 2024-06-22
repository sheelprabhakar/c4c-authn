package com.c4c.auth.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type ResourceNotFoundException.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param message the message
     */
    public ResourceNotFoundException(String message) {
    super(message);
  }
}