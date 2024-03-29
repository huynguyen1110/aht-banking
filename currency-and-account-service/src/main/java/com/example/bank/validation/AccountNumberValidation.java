package com.example.bank.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AccountNumberValidator.class)
public @interface AccountNumberValidation {

    String message() default "Account number can not contain characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
