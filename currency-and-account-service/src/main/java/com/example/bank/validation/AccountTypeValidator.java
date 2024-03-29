package com.example.bank.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class AccountTypeValidator implements ConstraintValidator<AccountTypeValidation, Enum<?>> {

    private Pattern pattern;

    @Override
    public void initialize(AccountTypeValidation constraintAnnotation) {
        try {
            pattern = Pattern.compile(constraintAnnotation.regexp());
        }catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }

    public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext constraintValidatorContext) {
        if (anEnum == null) {
            return true;
        }

        Matcher m = pattern.matcher(anEnum.name());
        return m.matches();
    }
}
