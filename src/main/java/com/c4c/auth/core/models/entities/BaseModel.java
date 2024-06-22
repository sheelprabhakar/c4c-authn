package com.c4c.auth.core.models.entities;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * The type BaseModel.
 */
@Accessors(chain = true)
@Getter
@Setter
public abstract class BaseModel {
  /**
   * The Id.
   */
  @MongoId(FieldType.OBJECT_ID)
  protected String id;

  /**
   * The Created at.
   */
  protected Date createdAt;

  /**
   * The Updated at.
   */
  protected Date updatedAt;
}
