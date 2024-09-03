package com.c4c.authz.core.entity.lookup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Country entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "countries")
public class CountryEntity implements Serializable {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    /**
     * The Name.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * The Iso 3.
     */
    @Size(max = 3)
    @Column(name = "iso3", length = 3)
    private String iso3;

    /**
     * The Iso 2.
     */
    @Size(max = 2)
    @Column(name = "iso2", length = 2)
    private String iso2;

    /**
     * The Phonecode.
     */
    @Size(max = 255)
    @Column(name = "phonecode")
    private String phonecode;

    /**
     * The Capital.
     */
    @Size(max = 255)
    @Column(name = "capital")
    private String capital;

    /**
     * The Currency.
     */
    @Size(max = 255)
    @Column(name = "currency")
    private String currency;

    /**
     * The Currency symbol.
     */
    @Size(max = 255)
    @Column(name = "currency_symbol")
    private String currencySymbol;

    /**
     * The Native field.
     */
    @Size(max = 255)
    @Column(name = "native")
    private String nativeField;

    /**
     * The Region.
     */
    @Size(max = 255)
    @Column(name = "region")
    private String region;

    /**
     * The Subregion.
     */
    @Size(max = 255)
    @Column(name = "subregion")
    private String subregion;

    /**
     * The Timezones.
     */
    @Lob
    @Column(name = "timezones")
    private String timezones;

    /**
     * The Translations.
     */
    @Lob
    @Column(name = "translations")
    private String translations;

    /**
     * The Latitude.
     */
    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    /**
     * The Longitude.
     */
    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    /**
     * The Emoji.
     */
    @Size(max = 191)
    @Column(name = "emoji", length = 191)
    private String emoji;

    /**
     * The Emoji u.
     */
    @Size(max = 191)
    @Column(name = "emojiU", length = 191)
    private String emojiU;

    /**
     * The Created at.
     */
    @Column(name = "created_at")
    private Instant createdAt;

    /**
     * The Updated at.
     */
    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * The Flag.
     */
    @NotNull
    @Column(name = "flag", nullable = false)
    private boolean flag;

    /**
     * The Wiki data id.
     */
    @Size(max = 255)
    @Column(name = "wiki_data_id")
    private String wikiDataId;

    /**
     * The Cities.
     */
    @OneToMany(mappedBy = "country")
    private Set<CityEntity> cities;

    /**
     * The States.
     */
    @OneToMany(mappedBy = "country")
    private Set<StateEntity> states;

}
