package com.example.bank.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class AccountNumberValidator implements ConstraintValidator<AccountNumberValidation, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if(s == null || s.isEmpty()) return true;
            Long.parseLong(s);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }
}
