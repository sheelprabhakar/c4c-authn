package com.c4c.authz.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Calendar;

/**
 * The type Common entity attributes.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class CommonEntityAttributes {

    /**
     * The constant L255.
     */
    private static final int L255 = 255;
    /**
     * The Is deleted.
     */
    @Column(name = "is_deleted")
    private boolean isDeleted;

    /**
     * The Created at.
     */
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;

    /**
     * The Updated at.
     */
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;

    /**
     * The Created by.
     */
    @Column(name = "created_by", length = L255, nullable = true)
    private String createdBy;

    /**
     * The Updated by.
     */
    @Column(name = "updated_by", length = L255, nullable = true)
    private String updatedBy;

    /**
     * Updated.
     *
     * @param user the user
     */
    public void updated(final String user) {
        this.setUpdatedAt(Calendar.getInstance());
        this.setUpdatedBy(user);
    }

    /**
     * Created.
     *
     * @param user the user
     */
    public void created(final String user) {
        this.setCreatedAt(Calendar.getInstance());
        this.setCreatedBy(user);
    }

}
