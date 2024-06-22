package com.c4c.auth.core.constraints.validators;

import com.c4c.auth.core.constraints.Exists;
import com.c4c.auth.common.utils.Helpers;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ExistsValidator implements ConstraintValidator<Exists, Object> {

  private final ApplicationContext applicationContext;
  private String propertyName;
  private String repositoryName;

  public ExistsValidator(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override
  public void initialize(final Exists constraintAnnotation) {
    propertyName = constraintAnnotation.property();
    repositoryName = constraintAnnotation.repository();
  }

  @Override
  public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    Object result;
    String finalRepositoryName = "com.c4c.auth.repositories." + repositoryName;

    try {
      Class<?> type = Class.forName(finalRepositoryName);
      Object instance = this.applicationContext.getBean(finalRepositoryName);

      final Object propertyObj = BeanUtils.getProperty(value, propertyName);

      String finalPropertyName = Helpers.capitalize(propertyName);

      result = type.getMethod("findBy" + finalPropertyName, String.class)
          .invoke(instance, propertyObj.toString());
    } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException |
             NoSuchMethodException e) {
      e.printStackTrace();

      return false;
    }

    return result != null;
  }
}
