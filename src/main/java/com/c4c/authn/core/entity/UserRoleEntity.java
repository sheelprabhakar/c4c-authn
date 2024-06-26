package com.c4c.authn.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * The type User role entity.
 */
@Entity(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
@IdClass(UserRoleId.class)
public class UserRoleEntity extends CommonEntityAttributes implements Serializable {

  /**
   * The Role id.
   */
  @Id
  @Column(name = "role_id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID roleId;

  /**
   * The User id.
   */
  @Id
  @Column(name = "user_id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID userId;
}
