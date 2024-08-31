package com.c4c.authz.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

/**
 * The type Client role entity.
 */
@Table(name = "client_roles")
@Entity(name = "ClientRoleEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@IdClass(ClientRoleId.class)
public class ClientRoleEntity extends CommonEntityAttributes implements Serializable {

    /**
     * The constant PRIME_31.
     */
    public static final int PRIME_31 = 31;
    /**
     * The Role id.
     */
    @Id
    @Column(name = "role_id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID roleId;

    /**
     * The Client id.
     */
    @Id
    @Column(name = "client_id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID clientId;

    /**
     * The Client entity.
     */
    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;

    /**
     * The Role entity.
     */
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

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
        if (!super.equals(o)) {
            return false;
        }

        ClientRoleEntity that = (ClientRoleEntity) o;
        return roleId.equals(that.roleId) && clientId.equals(that.clientId);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = PRIME_31 * result + roleId.hashCode();
        result = PRIME_31 * result + clientId.hashCode();
        return result;
    }
}
