package com.c4c.authz.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;

/**
 * The type Role entity.
 */
@Entity(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends CommonEntityAttributes implements GrantedAuthority, Serializable {

  /**
   * The constant L50.
   */
  private static final int L50 = 50;
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
  @Column(name = "name", length = L50)
  private String name;

  /**
   * Gets authority.
   *
   * @return the authority
   */
  @Override
  public String getAuthority() {
    return name;
  }

  /**
   * The User role entities.
   */
  @OneToMany(mappedBy = "roleEntity", fetch = FetchType.EAGER)
  private Set<UserRoleEntity> userRoleEntities;

}
