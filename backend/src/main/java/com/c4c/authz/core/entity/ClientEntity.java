package com.c4c.authz.core.entity;

import com.c4c.authz.core.service.impl.EntityAttributeEncryptor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
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
 * The type Client entity.
 */
@Entity(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class ClientEntity extends CommonEntityAttributes implements Serializable {

  /**
   * The constant NAME_MAX.
   */
  private static final int NAME_MAX = 50;
  /**
   * The constant HASH_MAX.
   */
  private static final int HASH_MAX = 255;
  /**
   * The constant MOBILE_MAX.
   */
  private static final int MOBILE_MAX = 15;
  /**
   * The Id.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID id;

  /**
   * The Tenant id.
   */
  @Column(name = "tenant_id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID tenantId;

  /**
   * The Name.
   */
  @Column(name = "name", length = NAME_MAX, nullable = false)
  private String name;

  /**
   * The Client id.
   */
  @Column(name = "client_id", length = NAME_MAX)
  private String clientId;

  /**
   * The Client secret.
   */
  @Column(name = "client_secret", nullable = false)
  @Convert(converter = EntityAttributeEncryptor.class)
  private String clientSecret;
}
