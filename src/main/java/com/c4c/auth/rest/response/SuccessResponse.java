package com.c4c.auth.rest.response;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type SuccessResponse.
 */
@AllArgsConstructor
@Setter
@Getter
public class SuccessResponse {
  private Map<String, String> data;
}
