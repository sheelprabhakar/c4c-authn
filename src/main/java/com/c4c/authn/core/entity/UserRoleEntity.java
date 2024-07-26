package com.c4c.authn.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
//@EqualsAndHashCode(callSuper = true)
public class UserRoleEntity extends CommonEntityAttributes implements Serializable {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    /**
     * The Role id.
     */
    @Column(name = "role_id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID roleId;

    /**
     * The User id.
     */
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
    @JoinColumn(name = "role_id")
    RoleEntity roleEntity;
}
