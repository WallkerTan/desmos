package com.example.backend.validator;

import java.util.regex.Pattern;
import com.example.backend.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private ValidPassword annotation;

    @Override
    public void initialize(ValidPassword constrainAnnotation) {
        this.annotation = constrainAnnotation;
    }

    @Override
    public boolean isValid(String pasword, ConstraintValidatorContext context) {
        if (pasword == null)
            return annotation.allowNull();
        if (pasword.trim().isEmpty())
            return annotation.allowEmpty();
        Pattern pattern = Pattern.compile(annotation.regex());

        return pattern.matcher(pasword).matches();
    }

}
