package com.c4c.authn.core.entity;

import com.c4c.authn.core.entity.lookup.CityEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * The type Tenant entity.
 */
@Entity(name = "tenant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends CommonEntityAttributes implements Serializable {
    /**
     * The constant L255.
     */
    private static final int L255 = 255;
    /**
     * The constant L512.
     */
    private static final int L512 = 512;
    /**
     * The constant L15.
     */
    private static final int L15 = 45;
    /**
     * The constant L8.
     */
    private static final int L8 = 8;
    /**
     * The Id.
     */
    @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID id;

    /**
     * The Name.
     */
    @Column(name = "name", length = L255, nullable = false)
  @NotNull
  private String name;

    /**
     * The Email.
     */
    @Column(name = "email", length = L255, nullable = false)
  @NotNull
  private String email;

    /**
     * The Address.
     */
    @Column(name = "address", length = L512, nullable = true)
  private String address;

    /**
     * The Pin.
     */
    @Column(name = "pin", length = L8, nullable = true)
  private String pin;

    /**
     * The Phone.
     */
    @Column(name = "phone", length = L15, nullable = true)
  private String phone;

    /**
     * The Short name.
     */
    @Size(max = 45)
  @NotNull
  @Column(name = "short_name", nullable = false, length = 45)
  private String shortName;

    /**
     * The Area.
     */
    @Size(max = 255)
  @Column(name = "area")
  private String area;

    /**
     * The Landmark.
     */
    @Size(max = 45)
  @Column(name = "landmark", length = 45)
  private String landmark;

    /**
     * The City.
     */
    @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "city_id", nullable = false)
  private CityEntity city;

    /**
     * The Picture url.
     */
    @Size(max = 2048)
  @Column(name = "picture_url", length = 2048)
  private String pictureUrl;

    /**
     * The Latitude.
     */
    @Column(name = "latitude", precision = 10, scale = 8)
  private BigDecimal latitude;

    /**
     * The Longitude.
     */
    @Column(name = "longitude", precision = 10, scale = 8)
  private BigDecimal longitude;

    /**
     * The Active.
     */
    @NotNull
  @Column(name = "active", nullable = false)
  private boolean active;

    /**
     * The Mobile.
     */
    @Size(max = 15)
  @NotNull
  @Column(name = "mobile", nullable = false, length = 15)
  private String mobile;

}
