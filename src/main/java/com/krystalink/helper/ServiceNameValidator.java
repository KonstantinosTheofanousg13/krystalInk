package com.krystalink.helper;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.krystalink.constants.ServiceEnum;

public class ServiceNameValidator implements ConstraintValidator<ValidServiceName, String> {

    @Override
    public boolean isValid(String serviceName, ConstraintValidatorContext context) {
        try {
            ServiceEnum.valueOf(serviceName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}