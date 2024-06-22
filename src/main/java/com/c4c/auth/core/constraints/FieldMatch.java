package com.c4c.auth.core.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.c4c.auth.core.constraints.validators.FieldMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The interface FieldMatch.
 */
@Constraint(validatedBy = FieldMatchValidator.class)
@Target({
    TYPE, FIELD,
    ANNOTATION_TYPE
})
@Retention(RUNTIME)
@Documented
public @interface FieldMatch {
  /**
   * Message string.
   *
   * @return the string
   */
  String message() default "{constraints.field-match}";

  /**
   * Groups class [ ].
   *
   * @return the class [ ]
   */
  Class<?>[] groups() default {};

  /**
   * Payload class [ ].
   *
   * @return the class [ ]
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * First string.
   *
   * @return the string
   */
  String first();

  /**
   * Second string.
   *
   * @return the string
   */
  String second();

  /**
   * The interface List.
   */
  @Target({
      TYPE, FIELD,
      ANNOTATION_TYPE
  })
  @Retention(RUNTIME)
  @Documented
  @interface List {
    /**
     * Value field match [ ].
     *
     * @return the field match [ ]
     */
    FieldMatch[] value();
  }
}
