package com.krystalink.helper;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.krystalink.constants.ServiceEnum;

public class ServiceNameValidator implements ConstraintValidator<ValidServiceName, String> {

    @Override
    public boolean isValid(String serviceName, ConstraintValidatorContext context) {
        if (serviceName == null) return true;

        for (ServiceEnum service : ServiceEnum.values()) {
            if (service.name().equalsIgnoreCase(serviceName)) {
                return true;
            }
        }
        return false;
    }
}