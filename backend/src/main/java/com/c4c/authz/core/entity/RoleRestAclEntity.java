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
 * The type Role rest acl entity.
 */
@Table(name = "role_rest_acls")
@Entity(name = "RoleRestAclEntity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@IdClass(RoleRestAclId.class)
public class RoleRestAclEntity extends CommonEntityAttributes implements Serializable {


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
     * The Rest acl id.
     */
    @Id
    @Column(name = "rest_acl_id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID restAclId;


    /**
     * The Rest acl entity.
     */
    @ManyToOne
    @MapsId("restAclId")
    @JoinColumn(name = "rest_acl_id")
    private RestAclEntity restAclEntity;

    /**
     * The Role entity.
     */
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

    /**
     * The Can create.
     */
    @Column(name = "can_create", nullable = false)
    @JdbcTypeCode(SqlTypes.TINYINT)
    private boolean canCreate;

    /**
     * The Can read.
     */
    @Column(name = "can_read", nullable = false)
    @JdbcTypeCode(SqlTypes.TINYINT)
    private boolean canRead;

    /**
     * The Can update.
     */
    @Column(name = "can_update", nullable = false)
    @JdbcTypeCode(SqlTypes.TINYINT)
    private boolean canUpdate;

    /**
     * The Can delete.
     */
    @Column(name = "can_delete", nullable = false)
    @JdbcTypeCode(SqlTypes.TINYINT)
    private boolean canDelete;

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

        RoleRestAclEntity that = (RoleRestAclEntity) o;
        return roleId.equals(that.roleId) && restAclId.equals(that.restAclId);
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
        result = PRIME_31 * result + restAclId.hashCode();
        return result;
    }
}
