package com.c4c.auth.core.models.entities;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Accessors(chain = true)
@Getter
@Setter
public abstract class BaseModel {
  @MongoId(FieldType.OBJECT_ID)
  protected String id;

  protected Date createdAt;

  protected Date updatedAt;
}
