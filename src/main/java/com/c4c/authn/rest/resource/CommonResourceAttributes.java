package com.c4c.authn.rest.resource;

import java.util.Calendar;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Common resource attributes.
 */
@Getter
@Setter
@NoArgsConstructor
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
