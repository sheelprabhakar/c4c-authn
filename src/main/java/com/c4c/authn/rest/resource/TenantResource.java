package com.c4c.authn.rest.resource;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Resource for {@link com.c4c.authn.core.entity.TenantEntity}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class TenantResource extends CommonResourceAttributes implements Serializable {
  /**
   * The Id.
   */
  private UUID id;
  /**
   * The Name.
   */
  @NotEmpty
  @Size(max = 45)
  private String name;
  /**
   * The Email.
   */
  @NotEmpty
  @Size(max = 255)
  @Email
  private String email;
  /**
   * The Address.
   */
  @NotEmpty
  @Size(max = 255)
  private String address;
  /**
   * The Pin.
   */
  private String pin;
  /**
   * The Phone.
   */
  private String phone;

  /**
   * The Short name.
   */
  @NotEmpty
  @Size(max = 45)
  private String shortName;
  /**
   * The Area.
   */
  @Size(max = 255)
  private String area;
  /**
   * The Landmark.
   */
  @Size(max = 45)
  private String landmark;
  /**
   * The Picture url.
   */
  @Size(max = 2048)
  private String pictureUrl;
  /**
   * The Latitude.
   */
  private BigDecimal latitude;
  /**
   * The Longitude.
   */
  private BigDecimal longitude;
  /**
   * The Active.
   */
  private boolean active;

  /**
   * The Mobile.
   */
  @NotEmpty
  private String mobile;

  /**
   * The City id.
   */
  private int cityId;
}
