package com.c4c.authn.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
 * The type Tenant user entity.
 */
@Entity(name = "tenant_user")
@IdClass(TenantUserEntityId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class TenantUserEntity extends CommonEntityAttributes {
  /**
   * The Role id.
   */
  @Id
  @Column(name = "tenant_id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID tenantId;

  /**
   * The User id.
   */
  @Id
  @Column(name = "user_id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID userId;

}
