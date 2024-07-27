package com.c4c.authn.rest.resource.lookup;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type State resource.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateResource implements Serializable {
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
     * The Country code.
     */
    @NotNull
  @Size(max = 2)
  private String countryCode;
    /**
     * The Fips code.
     */
    @Size(max = 255)
  private String fipsCode;
    /**
     * The Iso 2.
     */
    @Size(max = 255)
  private String iso2;
    /**
     * The Latitude.
     */
    private BigDecimal latitude;
    /**
     * The Longitude.
     */
    private BigDecimal longitude;
    /**
     * The Created at.
     */
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
  private boolean flag;
    /**
     * The Wiki data id.
     */
    @Size(max = 255)
  private String wikiDataId;
}
