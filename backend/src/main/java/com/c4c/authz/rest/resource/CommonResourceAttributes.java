package com.c4c.authz.rest.resource;

import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The type Common resource attributes.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
public class CommonResourceAttributes {
  /**
   * The Is deleted.
   */
  private boolean isDeleted;
  /**
   * The Created at.
   */
  private Calendar createdAt;
  /**
   * The Updated at.
   */
  private Calendar updatedAt;
  /**
   * The Created by.
   */
  private String createdBy;
  /**
   * The Updated by.
   */
  private String updatedBy;
}
