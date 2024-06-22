package com.c4c.auth.core.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.c4c.auth.core.constraints.validators.IsUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The interface IsUnique.
 */
@Constraint(validatedBy = IsUniqueValidator.class)
@Target({
    TYPE, FIELD,
    ANNOTATION_TYPE
})
@Retention(RUNTIME)
@Documented
public @interface IsUnique {
  /**
   * Message string.
   *
   * @return the string
   */
  String message() default "{constraints.is-unique}";

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
   * Action is unique validator . update action.
   *
   * @return the is unique validator . update action
   */
  IsUniqueValidator.UpdateAction action() default IsUniqueValidator.UpdateAction.INSERT;

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
     * Value is unique [ ].
     *
     * @return the is unique [ ]
     */
    IsUnique[] value();
  }
}
