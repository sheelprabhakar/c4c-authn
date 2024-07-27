package com.c4c.authn.core.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

/**
 * The type Attribute entity.
 */
@Entity(name = "attribute_resource")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class AttributeEntity extends CommonEntityAttributes {

    /**
     * The constant L50.
     */
    private static final int L50 = 50;
    /**
     * The constant L4096.
     */
    private static final int L4096 = 4096;

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
     * The Attribute name.
     */
    @Column(name = "attribute_name", length = L50, nullable = false)
    private String attributeName;

    /**
     * The Path.
     */
    @Column(name = "path", length = L4096, nullable = false)
    private String path;

}
