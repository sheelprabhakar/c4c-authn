package com.c4c.authn.core.entity;

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
 * The type User role entity.
 */
@Entity(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
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


    /**
     * The User entity.
     */
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    UserEntity userEntity;

    /**
     * The Role entity.
     */
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id" )
    RoleEntity roleEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        UserRoleEntity that = (UserRoleEntity) o;
        return roleId.equals(that.roleId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + roleId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}
