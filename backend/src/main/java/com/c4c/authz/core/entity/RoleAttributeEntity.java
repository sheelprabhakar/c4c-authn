package com.c4c.authz.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
 * The type Role attribute entity.
 */
@Entity(name = "role_attribute")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@IdClass(RoleAttributeId.class)
public class RoleAttributeEntity extends CommonEntityAttributes implements Serializable {


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
     * The Attribute id.
     */
    @Id
    @Column(name = "attribute_id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID attributeId;


    /**
     * The Attribute entity.
     */
    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    private AttributeEntity attributeEntity;

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

        RoleAttributeEntity that = (RoleAttributeEntity) o;
        return roleId.equals(that.roleId) && attributeId.equals(that.attributeId);
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
        result = PRIME_31 * result + attributeId.hashCode();
        return result;
    }
}
