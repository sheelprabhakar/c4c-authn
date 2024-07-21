package com.c4c.authn.core.entity;

import com.c4c.authn.core.service.impl.EntityAttributeEncryptor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Calendar;
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
 * The type User token entity.
 */
@Entity(name = "user_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
public class UserTokenEntity implements Serializable {

  /**
   * The constant TOKEN_MAX_LENGTH.
   */
  private static final int TOKEN_MAX_LENGTH = 4096;
  /**
   * The Id.
   */
  @Id
  @Column(name = "user_id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID userId;

  /**
   * The accesstoken.
   */
  @Column(name = "access_token", nullable = false, length = TOKEN_MAX_LENGTH)
  @Convert(converter = EntityAttributeEncryptor.class)
  private String accessToken;

  /**
   * The Refresh token.
   */
  @Column(name = "refresh_token", nullable = false, length = TOKEN_MAX_LENGTH)
  @Convert(converter = EntityAttributeEncryptor.class)
  private String refreshToken;

  /**
   * The Updated at.
   */
  @Column(name = "updated_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar updatedAt;
}
