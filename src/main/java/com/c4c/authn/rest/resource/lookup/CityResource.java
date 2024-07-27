package com.c4c.authn.rest.resource.lookup;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * The type City resource.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityResource implements Serializable {
    /**
     * The Id.
     */
    private int id;
    /**
     * The Name.
     */
    @NotNull
  @Size(max = 255)
  private String name;
    /**
     * The State code.
     */
    @NotNull
  @Size(max = 255)
  private String stateCode;
    /**
     * The Country code.
     */
    @NotNull
  @Size(max = 2)
  private String countryCode;
    /**
     * The Latitude.
     */
    @NotNull
  private BigDecimal latitude;
    /**
     * The Longitude.
     */
    @NotNull
  private BigDecimal longitude;
    /**
     * The Created at.
     */
    @NotNull
  private Instant createdAt;
    /**
     * The Updated at.
     */
    @NotNull
  private Instant updatedAt;
    /**
     * The Flag.
     */
    @NotNull
  private Boolean flag;
    /**
     * The Wiki data id.
     */
    @Size(max = 255)
  private String wikiDataId;
}
