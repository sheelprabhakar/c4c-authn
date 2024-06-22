package com.c4c.auth.rest.response;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type BadRequestResponse.
 */
@AllArgsConstructor
@Setter
@Getter
public class BadRequestResponse {
  private Map<String, String> data;
}
