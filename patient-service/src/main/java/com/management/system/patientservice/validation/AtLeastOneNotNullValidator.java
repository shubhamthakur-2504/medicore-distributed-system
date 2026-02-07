package com.management.system.patientservice.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {
    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context){
        for(Field field : dto.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if (field.get(dto) != null) {
                    return true; // Found a field that isn't null!
                }
            } catch (IllegalAccessException e) {
                // skip
            }
        }
        return false; // All fields were null
    }
}
