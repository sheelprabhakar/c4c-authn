package com.c4c.auth.rest.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * The type InvalidDataResponse.
 */
@Setter
@Getter
public class InvalidDataResponse {
  private Map<String, Map<String, List<String>>> data;

  /**
   * Instantiates a new Invalid data response.
   *
   * @param data the data
   */
  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public InvalidDataResponse(@JsonProperty("data") Map<String, Map<String, List<String>>> data) {
    this.data = data;
  }
}
