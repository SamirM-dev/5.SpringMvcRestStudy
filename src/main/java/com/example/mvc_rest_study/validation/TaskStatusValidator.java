package com.example.mvc_rest_study.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class TaskStatusValidator implements ConstraintValidator<ValidTaskStatus, String> {
    final private List<String> ALLOWED = new ArrayList<>(List.of("NEW","IN_PROGRESS","DONE"));
    @Override
    public void initialize(ValidTaskStatus constraintAnnotation) {
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value==null) {
            return true;
        }
        return ALLOWED.contains(value.toUpperCase());
    }
}
