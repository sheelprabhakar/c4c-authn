package com.c4c.authz.core.entity;

import com.c4c.authz.core.service.impl.EntityAttributeEncryptor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

/**
 * The type Oauth token entity.
 */
@Table(name = "oauth_tokens")
@Entity(name = "OauthTokenEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
public class OauthTokenEntity implements Serializable {

    /**
     * The constant TOKEN_MAX_LENGTH.
     */
    private static final int TOKEN_MAX_LENGTH = 4096;

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    /**
     * The User id.
     */
    @Column(name = "user_id", nullable = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID userId;

    /**
     * The Client id.
     */
    @Column(name = "client_id", nullable = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID clientId;
    /**
     * The Tenant id.
     */
    @Column(name = "tenant_id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID tenantId;
    /**
     * The Access token.
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
     * The Created at.
     */
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;

    /**
     * The Expiry time.
     */
    @Column(name = "expiry_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar expiryTime;
}
