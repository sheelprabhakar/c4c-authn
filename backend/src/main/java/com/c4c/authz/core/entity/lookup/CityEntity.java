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
 * The type City entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cities")
public class CityEntity implements Serializable {
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
     * The State code.
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "state_code", nullable = false)
    private String stateCode;

    /**
     * The State.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
    private StateEntity state;

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
     * The Latitude.
     */
    @NotNull
    @Column(name = "latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    /**
     * The Longitude.
     */
    @NotNull
    @Column(name = "longitude", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    /**
     * The Created at.
     */
    @NotNull
    @Column(name = "created_at", nullable = false)
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

        CityEntity that = (CityEntity) o;
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
