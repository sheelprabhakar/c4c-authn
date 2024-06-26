package com.c4c.authn.rest.resource;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * Resource for {@link com.c4c.authn.core.entity.TenantEntity}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantResource extends CommonResourceAttributes implements Serializable {
  /**
   * The Id.
   */
  private UUID id;
  /**
   * The Name.
   */
  @NotNull
  @Size(max = 45)
  private String name;
  /**
   * The Email.
   */
  @NotNull
  @Size(max = 255)
  private String email;
  /**
   * The Address.
   */
  @NotNull
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
  @NotNull
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
  @NotNull
  private String mobile;

  /**
   * The City id.
   */
  @NotNull
  @Range(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE)
  private int cityId;
}
