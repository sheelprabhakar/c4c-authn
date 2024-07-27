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
 * The type Country resource.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryResource implements Serializable {
    /**
     * The Id.
     */
    private Integer id;
    /**
     * The Name.
     */
    @NotNull
  @Size(max = 100)
  private String name;
    /**
     * The Iso 3.
     */
    @Size(max = 3)
  private String iso3;
    /**
     * The Iso 2.
     */
    @Size(max = 2)
  private String iso2;
    /**
     * The Phonecode.
     */
    @Size(max = 255)
  private String phonecode;
    /**
     * The Capital.
     */
    @Size(max = 255)
  private String capital;
    /**
     * The Currency.
     */
    @Size(max = 255)
  private String currency;
    /**
     * The Currency symbol.
     */
    @Size(max = 255)
  private String currencySymbol;
    /**
     * The Native field.
     */
    @Size(max = 255)
  private String nativeField;
    /**
     * The Region.
     */
    @Size(max = 255)
  private String region;
    /**
     * The Subregion.
     */
    @Size(max = 255)
  private String subregion;
    /**
     * The Timezones.
     */
    private String timezones;
    /**
     * The Translations.
     */
    private String translations;
    /**
     * The Latitude.
     */
    private BigDecimal latitude;
    /**
     * The Longitude.
     */
    private BigDecimal longitude;
    /**
     * The Emoji.
     */
    @Size(max = 191)
  private String emoji;
    /**
     * The Emoji u.
     */
    @Size(max = 191)
  private String emojiU;
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
  private Boolean flag;
    /**
     * The Wiki data id.
     */
    @Size(max = 255)
  private String wikiDataId;
}
