package com.c4c.authn.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * The type Tenant user entity.
 */
@Getter
@Setter
@Entity(name = "tenant_user")
@NoArgsConstructor
@IdClass(TenantUserEntityId.class)
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
