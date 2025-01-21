package com.user_services.user_services.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class MinAgeValidator implements ConstraintValidator<MinAge, Date> {
  private int minAge;

  @Override
  public void initialize(MinAge constraintAnnotation) {
    this.minAge = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
    if (date == null) return false;


    LocalDate birthDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate now = LocalDate.now();
    int age = Period.between(birthDate, now).getYears();

    return age >= minAge;
  }
}