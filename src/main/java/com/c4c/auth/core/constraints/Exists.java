package com.c4c.auth.core.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.c4c.auth.core.constraints.validators.ExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The interface Exists.
 */
@Constraint(validatedBy = ExistsValidator.class)
@Target({
    TYPE, FIELD,
    ANNOTATION_TYPE
})
@Retention(RUNTIME)
@Documented
public @interface Exists {
  /**
   * Message string.
   *
   * @return the string
   */
  String message() default "{constraints.exists}";

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
   * Property string.
   *
   * @return the string
   */
  String property();

  /**
   * Repository string.
   *
   * @return the string
   */
  String repository();

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
     * Value exists [ ].
     *
     * @return the exists [ ]
     */
    Exists[] value();
  }
}
