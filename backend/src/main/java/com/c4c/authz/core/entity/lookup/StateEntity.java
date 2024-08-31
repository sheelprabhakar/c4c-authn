package com.c4c.authz.core.entity.lookup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
 * The type State entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "states")
public class StateEntity implements Serializable {
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
  @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

  /**
   * The Country.
   */
  @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity country;

  /**
   * The Country code.
   */
  @Size(max = 2)
    @NotNull
    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;

  /**
   * The Fips code.
   */
  @Size(max = 255)
    @Column(name = "fips_code")
    private String fipsCode;

  /**
   * The Iso 2.
   */
  @Size(max = 255)
    @Column(name = "iso2")
    private String iso2;

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
   * Equals boolean.
   *
   * @param o the o
   * @return the boolean
   */
  @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StateEntity that = (StateEntity) o;
        return id == that.id;
    }

  /**
   * Hash code int.
   *
   * @return the int
   */
  @Override
    public int hashCode() {
        return id;
    }
}
