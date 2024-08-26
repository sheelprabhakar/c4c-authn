// src/main/java/com/c4c/authz/rest/resource/ClientResource.java
package com.c4c.authz.rest.resource;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Client resource.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class ClientResource extends CommonResourceAttributes implements Serializable {
  /**
   * The Id.
   */
  private UUID id;
  /**
   * The Tenant id.
   */
  private UUID tenantId;
  /**
   * The Client id.
   */
  @NotEmpty(message = "Client id can not be empty.")
  @Size(max = 50, message = "Role name should be less than 50 characters.")
  private String clientId;
  /**
   * The Client secret.
   */
  @NotEmpty(message = "Client secret can not be empty.")
  @Size(max = 255, message = "Role name should be less than 50 characters.")
  private String clientSecret;

  /**
   * The Name.
   */
  @NotEmpty(message = "Role name can not be empty.")
  @Size(max = 50, message = "Role name should be less than 50 characters.")
  private String name;
}