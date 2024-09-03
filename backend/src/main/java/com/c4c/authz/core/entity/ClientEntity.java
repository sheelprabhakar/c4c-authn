package com.c4c.authz.core.entity;

import com.c4c.authz.core.service.impl.EntityAttributeEncryptor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * The type Client entity.
 */
@Table(name = "clients")
@Entity(name = "ClientEntity")
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
    @Column(name = "client_id")
    private String clientId;

    /**
     * The Client secret.
     */
    @Column(name = "client_secret", nullable = false, length = 1024)
    @Convert(converter = EntityAttributeEncryptor.class)
    private String clientSecret;

    /**
     * The Client role entities.
     */
    @OneToMany(mappedBy = "clientEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientRoleEntity> clientRoleEntities;
}
